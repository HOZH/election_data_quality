package com.example.demo.service;


import com.example.demo.dao.PrecinctDao;
import com.example.demo.model.County;
import com.example.demo.model.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */

//todo write checker for non uuid format string keys and try catch block in precincts services.
@Service
public class PrecinctService {


    private final PrecinctDao precinctDao;
    private final CountyService countyService;

    @Autowired
    public PrecinctService(PrecinctDao precinctDao, CountyService countyService) {
        this.precinctDao = precinctDao;
        this.countyService = countyService;
    }


    public Precinct savePrecinct(Precinct precinct) {

        //todo warp this method with exception handler, return null if any exception raised ->resulting in a 400 status code in the controller layer


        System.out.println();

        System.err.println();


        // getCountyId is never going to be null by convention in our group
        County tempCounty = countyService.selectCountyById(precinct.getCountyId());


        // if the precinctId field is not passed then it will be the insertion of a new precinct
        // if the select precinct by id result in a null it will be the insertion of a new precinct with given id
        // first check the nullity of the precinct id field so the second predicate will be safely ignore with the short-circuit ||
        if (precinct.getId() == null || precinctDao.findById(precinct.getId()).orElse(null) == null
        ) {
            //query current precinct's belonging county


            var countyNotFound = tempCounty == null;

            // if the belonging county is not found in database then create the county with county id and ethnicity data wrapped in the current precinct
            if (countyNotFound) {

                tempCounty = new County();
                tempCounty.setId(precinct.getCountyId());
                tempCounty.setStateId(precinct.getStateId());

            }


            // if the belonging county is not found in database or the flag for updating demographic data in current precinct
            // is set to true then update ethnicity data wrapped in the current precinct to current county
            if (countyNotFound || precinct.isDemographicDataModified()) {
                updateEthnicityDataHelper(tempCounty, precinct);
                countyService.saveCounty(tempCounty);

            }


            // set the county field for target precinct
            precinct.setCounty(tempCounty);

            // if the precinct id is not given then generate a random string id for the precinct in uuid version 4 format
            if (precinct.getId() == null) {
                precinct.setId(UUID.randomUUID().toString());
            }

            // save the target precinct into the database
            var result = precinctDao.save(precinct);


            // inform the target precinct's adjacent precincts to add it to their adjacent precinct id list
            precinctDao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

                // if not already include then add to the target's list and save the changes
                if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                    e.getAdjacentPrecinctIds().add(result.getId());

                    precinctDao.save(e);

                }

            });


            return result;
        }
        // operation of modifying an existing precinct
        else {


            // nullity check has been done in first selection
            // pull up the precinct record of target precinct in database
            var oldPrecinct = precinctDao.findById(precinct.getId()).orElse(null);

            // comparing the adjacentPrecinctIds of the updated precinct and the record in the database
            if (oldPrecinct.getAdjacentPrecinctIds().containsAll(precinct.getAdjacentPrecinctIds()) && oldPrecinct.getAdjacentPrecinctIds().size() == precinct.getAdjacentPrecinctIds().size()) {


                // if the adjacentPrecinctIds of target precinct is not changed then check is demographic data modified for its county
                if (precinct.isDemographicDataModified()) {

                    updateEthnicityDataHelper(tempCounty, precinct);
                    countyService.saveCounty(tempCounty);

                }

                precinct.setCounty(tempCounty);

                // save the target precinct into the database
                return precinctDao.save(precinct);


            } else {

                //else go to helper method updateNeighbors
                return updateNeighbors(precinct);
            }
        }
    }


    public Precinct updateNeighbors(Precinct newPrecinct) {

        //todo warp this method with exception handler, return null if any exception raised ->resulting in a 400 status code in the controller layer


        // pull up record of target precinct in the database
        // nullity check has been done in the method calling this
        var oldPrecinct = precinctDao.findById(newPrecinct.getId()).orElse(null);


        // set diff of adjacentPrecinctIds from the record in database and current precinct
        ArrayList<String> deleted = new ArrayList(oldPrecinct.getAdjacentPrecinctIds());

        // set diff of adjacentPrecinctIds from current precinct and the record in database
        ArrayList<String> added = new ArrayList(newPrecinct.getAdjacentPrecinctIds());

        // adjacentPrecinctIds cannot be null by convention
        deleted.remove(new ArrayList(newPrecinct.getAdjacentPrecinctIds()));
        added.remove(new ArrayList(oldPrecinct.getAdjacentPrecinctIds()));


        // removing target precinct's id from its deleted precinct ids in their adjacent precinct ids list
        for (var i : precinctDao.findAllById(deleted)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().remove(newPrecinct.getId());
            precinctDao.save(i);
        }

        // adding target precinct's id to its newly added precinct ids in their adjacent precinct ids list
        for (var i : precinctDao.findAllById(added)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().add(newPrecinct.getId());
            precinctDao.save(i);

        }


        // if the adjacentPrecinctIds of target precinct is not changed then check is demographic data modified for its county
        if (newPrecinct.isDemographicDataModified()) {

            var tempCounty = countyService.selectCountyById(newPrecinct.getCountyId());
            updateEthnicityDataHelper(tempCounty, newPrecinct);
            newPrecinct.setCounty(tempCounty);
            countyService.saveCounty(tempCounty);

        }


        return precinctDao.save(newPrecinct);


    }

    public void deletePrecinctById(String id) {


        precinctDao.deleteById(id);


    }


    public Precinct selectPrecinctById(String id) {
        return precinctDao.findById(id).orElse(null);
    }

    public List<Precinct> selectAllPrecincts() {
        return precinctDao.findAll();


    }

    public Precinct mergePrecincts(List<Precinct> precincts) {

        //todo warp this method with exception handler, return null if any exception raised ->resulting in a 400 status code in the controller layer

        // primary precinct
        Precinct merged = precincts.get(0);
        // deleting precinct
        Precinct placeholder = precincts.get(1);


        //merge two's adjacent list and delete the deleting precinct's id from its adjacent precinct ids
        placeholder.getAdjacentPrecinctIds().forEach(e -> {


                    //precinct queried by the ids in deleting precincts' adjacent precinct ids list
                    var temp = precinctDao.findById(e).orElse(null);


                    //if the primary precinct not already contained the temp then
                    if (!merged.getAdjacentPrecinctIds().contains(e)) {

                        // if temp is not primary precinct then add each other to their id to their adjacent precinct ids

                        if (!e.equals(merged.getId())) {
                            merged.getAdjacentPrecinctIds().add(e);
                            temp.getAdjacentPrecinctIds().add(merged.getId());
                        }

                        // temp.getAdjacentPrecinctIds().add(merged.getId());


                    }

                    // deleting precinct's id from temp's list
                    temp.getAdjacentPrecinctIds().remove(placeholder.getId());

                    // save temp
                    precinctDao.save(temp);


                }

        );


        // deleting precinct's id from temp's list
        merged.getAdjacentPrecinctIds().remove(placeholder.getId());


        // remove deleting precinct from database
        precinctDao.deleteById(placeholder.getId());

        // save merged precinct
        return precinctDao.save(merged);

    }


    private void updateEthnicityDataHelper(County c, Precinct p) {

        c.setWhite(p.getWhite());
        c.setAfricanAmerican(p.getAfricanAmerican());
        c.setAsianPacific(p.getAsianPacific());
        c.setNativeAmerican(p.getNativeAmerican());
        c.setPacificIslanders(p.getPacificIslanders());
        c.setOthers(p.getOthers());

    }

}
//1