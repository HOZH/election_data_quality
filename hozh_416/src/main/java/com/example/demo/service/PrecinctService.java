package com.example.demo.service;

import com.example.demo.entity.County;
import com.example.demo.entity.Precinct;
import com.example.demo.entitymanager.PrecinctEntityManager;
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

@Service
public class PrecinctService {

    private final PrecinctEntityManager precinctEntityManager;
    private final CountyService countyService;

    @Autowired
    public PrecinctService(PrecinctEntityManager precinctEntityManager, CountyService countyService) {
        this.precinctEntityManager = precinctEntityManager;
        this.countyService = countyService;
    }

    /**
     * query a precinct by the given id
     *
     * @param id -> String type, using as a id to query the target precinct
     * @return query result by given id -> type Precinct, return null if illegal arg exception raised
     */
    public Precinct selectPrecinctById(String id) {
        try {
            return precinctEntityManager.findById(id).orElse(null);
        } catch (Exception ex) {
            //fixme for now we may encounter Illegal arg exception, change generic handler to more concrete one later
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * return a collection of all the precinct records in the database
     *
     * @return query result -> type List<Precinct>
     */
    public List<Precinct> selectAllPrecincts() {
        return precinctEntityManager.findAll();
    }

    /**
     * delete a precinct record by the given id
     *
     * @param id -> String type, using as a id to query the target precinct
     */
    public void deletePrecinctById(String id) {
        precinctEntityManager.deleteById(id);
    }

    /**
     * save a precinct object into database. Used for both insertion and modification of a precinct record
     *
     * @param precinct -> precinct type
     * @return the saved precinct entity -> type precinct, return null if null pointer/ illegal arg exception raised
     * @see this.updateNeighbors
     */
    public Precinct savePrecinct(Precinct precinct) {
        try {
            // getCountyId is never going to be null by convention in our group
            County targetCounty = countyService.selectCountyById(precinct.getCountyId());

            // if the precinctId field is not passed then it will be the insertion of a new precinct
            // if the select precinct by id result in a null it will be the insertion of a new precinct with given id
            // first check the nullity of the precinct id field so the second predicate will be safely ignore with the 
            // short-circuit ||
            if (precinct.getId() == null || precinctEntityManager.findById(precinct.getId()).orElse(null) == null) {

                var countyNotFound = targetCounty == null;

                // query current precinct's belonging county
                // if the belonging county is not found in database then create the county with county id and ethnicity 
                // data wrapped in the current precinct
                if (countyNotFound) {
                    targetCounty = new County();
                    targetCounty.setId(precinct.getCountyId());
                    targetCounty.setStateId(precinct.getStateId());
                }

                // if the belonging county is not found in database or the flag for updating demographic data in 
                // current precinct is set to true then update ethnicity data wrapped in the current precinct to 
                // current county
                if (countyNotFound || precinct.isDemoModified()) {
                    updateEthnicityDataHelper(targetCounty, precinct);
                    countyService.saveCounty(targetCounty);
                }

                // set the county field for target precinct
                precinct.setCounty(targetCounty);

                // if the precinct id is not given then generate a random string id for the precinct in uuid v4 format
                if (precinct.getId() == null) {
                    precinct.setId(UUID.randomUUID().toString());
                }

                // save the target precinct into the database
                var result = precinctEntityManager.save(precinct);

                // inform the target precinct's adjacent precincts to add it to their adjacent precinct id list
                precinctEntityManager.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

                    // if not already include then add to the target's list and save the changes
                    if (!e.getAdjacentPrecinctIds().contains(result.getId())) {

                        e.getAdjacentPrecinctIds().add(result.getId());
                        precinctEntityManager.save(e);
                    }
                });

                return result;
            }
            // operation of modifying an existing precinct
            else {
                // nullity check has been done in first selection
                // pull up the precinct record of target precinct in database
                var precinctRecord = precinctEntityManager.findById(precinct.getId()).orElse(null);

                // comparing the adjacentPrecinctIds of the updated precinct and the record in the database
                if (precinctRecord.getAdjacentPrecinctIds().containsAll(precinct.getAdjacentPrecinctIds()) && precinctRecord.getAdjacentPrecinctIds().size() == precinct.getAdjacentPrecinctIds().size()) {

                    // if the adjacentPrecinctIds of target precinct is not changed then check is demographic data modified for its county
                    if (precinct.isDemoModified()) {
                        updateEthnicityDataHelper(targetCounty, precinct);
                        countyService.saveCounty(targetCounty);
                    }
                    precinct.setCounty(targetCounty);

                    // save the target precinct into the database
                    return precinctEntityManager.save(precinct);

                } else {
                    //else go to helper method updateNeighbors
                    return updateNeighbors(precinct);
                }
            }
        } catch (Exception ex) {

            //fixme may encounter nested exception, need a more concert error handler for that
            System.err.println("precinct adjacentPrecinctIds is null");
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * helper method for updating a precinct, it will update the adjacentPrecinctIds list of target
     * precinct and its adjacent precincts bidirectionally
     *
     * @param precinct -> precinct type
     * @return the saved precinct entity -> type precinct, return null if null pointer/ illegal arg exception raised
     */
    public Precinct updateNeighbors(Precinct precinct) {

        try {
            // pull up record of target precinct in the database
            // nullity check has been done in the method calling this
            var precinctRecord = precinctEntityManager.findById(precinct.getId()).orElse(null);

            // set diff of adjacentPrecinctIds from the record in database and current precinct
            ArrayList<String> deleted = new ArrayList(precinctRecord.getAdjacentPrecinctIds());

            // set diff of adjacentPrecinctIds from current precinct and the record in database
            ArrayList<String> added = new ArrayList(precinct.getAdjacentPrecinctIds());

            // adjacentPrecinctIds cannot be null by convention
            deleted.remove(new ArrayList(precinct.getAdjacentPrecinctIds()));
            added.remove(new ArrayList(precinctRecord.getAdjacentPrecinctIds()));

            // removing target precinct's id from its deleted precinct ids in their adjacent precinct ids list
            for (var i : precinctEntityManager.findAllById(deleted)) {
                i.getAdjacentPrecinctIds().remove(precinct.getId());
                precinctEntityManager.save(i);
            }

            // adding target precinct's id to its newly added precinct ids in their adjacent precinct ids list
            for (var i : precinctEntityManager.findAllById(added)) {
                i.getAdjacentPrecinctIds().add(precinct.getId());
                precinctEntityManager.save(i);
            }

            // if the adjacentPrecinctIds of target precinct is not changed then check is demographic data modified for its county
            if (precinct.isDemoModified()) {
                var targetCounty = countyService.selectCountyById(precinct.getCountyId());

                updateEthnicityDataHelper(targetCounty, precinct);
                precinct.setCounty(targetCounty);
                countyService.saveCounty(targetCounty);
            }
            return precinctEntityManager.save(precinct);
        } catch (NullPointerException | IllegalArgumentException ex) {
            System.err.println("precinct adjacentPrecinctIds is null");
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * merging two precincts
     *
     * @param precincts -> type List<Precinct>, index 0 -> primary precinct, index 1 deleting precinct
     * @return Precinct object of survived precinct
     */
    public Precinct mergePrecincts(List<Precinct> precincts) {

        try {

            // primary precinct
            Precinct primaryPrecinct = precincts.get(0);
            // deleting precinct
            Precinct deletingPrecinct = precincts.get(1);

            // merge two's adjacent list and delete the deleting precinct's id from its adjacent precinct ids
            deletingPrecinct.getAdjacentPrecinctIds().forEach(e -> {

                // precinct queried by the ids in deleting precincts' adjacent precinct ids list
                var temp = precinctEntityManager.findById(e).orElse(null);

                // if the primary precinct not already contained the temp then
                if (!primaryPrecinct.getAdjacentPrecinctIds().contains(e)) {

                    // if temp is not primary precinct, add each other to their id to their adjacent precinct ids
                    if (!e.equals(primaryPrecinct.getId())) {
                        primaryPrecinct.getAdjacentPrecinctIds().add(e);
                        temp.getAdjacentPrecinctIds().add(primaryPrecinct.getId());
                    }
                }
                temp.getAdjacentPrecinctIds().remove(deletingPrecinct.getId());
                precinctEntityManager.save(temp);
            });

            // deleting precinct's id from temp's list
            primaryPrecinct.getAdjacentPrecinctIds().remove(deletingPrecinct.getId());

            // remove deleting precinct from database
            precinctEntityManager.deleteById(deletingPrecinct.getId());

            // save primaryPrecinct precinct
            return precinctEntityManager.save(primaryPrecinct);

        } catch (NullPointerException ex) {
            System.err.println("precinct adjacentPrecinctIds is null");
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * set ethnicity data of a County object to ethnicity data of a Precinct object
     *
     * @param c -> type County, p -> type Precinct
     */
    private void updateEthnicityDataHelper(County c, Precinct p) {
        c.setWhite(p.getWhite());
        c.setAfrAmer(p.getAfrAmer());
        c.setAsian(p.getAsian());
        c.setNatAmer(p.getNatAmer());
        c.setPacIslr(p.getPacIslr());
        c.setOthers(p.getOthers());
    }
}
