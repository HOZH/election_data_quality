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


//    public Precinct selectByDefaultId(String defaultId){
//
//
//       return  precinctDao.findByDefaultId(defaultId);
//
//
//    }

    public Precinct savePrecinct(Precinct precinct) {

        //todo warp this method with exception handler, return null if any exception raised ->resulting in a 400 status code in the controller layer

//        var ids = precinct.getCanonicalName().split("-");
//        precinct.setStateId(ids[0]);
//        precinct.setCountyId(ids[1]);


        System.out.println();


        if (precinct.getId() == null || precinctDao.findById(precinct.getId()).orElse(null) == null
        ) {

            var tempCounty = countyService.selectCountyById(precinct.getCountyId());
            if (tempCounty == null) {

                tempCounty = new County();
                tempCounty.setId(precinct.getCountyId());
                tempCounty.setStateId(precinct.getStateId());
                tempCounty.setEthnicityData(precinct.getEthnicityData());
                System.err.println(tempCounty.getStateId());
                System.out.println();
                countyService.saveCounty(tempCounty);
            } else if (precinct.getEthnicityData() != null) {
                //update ethnicity data if it's not null
                tempCounty.setEthnicityData(precinct.getEthnicityData());
                countyService.saveCounty(tempCounty);

            }
            precinct.setCounty(tempCounty);
            if(precinct.getId()==null)
            precinct.setId(UUID.randomUUID().toString());


            var result = precinctDao.save(precinct);


            precinctDao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

                if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                    e.getAdjacentPrecinctIds().add(result.getId());

                    precinctDao.save(e);


                }

            });


            return result;
        } else {

//            var oldPrecinct = precinctDao.findById(precinct.getId()).orElse(null);
//            if(oldPrecinct==null)
//            {
//                return precinctDao.save(precinct);
//                //fixme maybe combining with next if statement?
//            }
            var oldPrecinct = precinctDao.findById(precinct.getId()).orElse(null);
            if (oldPrecinct.getAdjacentPrecinctIds().containsAll(precinct.getAdjacentPrecinctIds()) && oldPrecinct.getAdjacentPrecinctIds().size() == precinct.getAdjacentPrecinctIds().size()) {

                if (precinct.getEthnicityData() != null) {

                    var tempCounty = countyService.selectCountyById(precinct.getCountyId());

                    //update ethnicity data if it's not null
                    tempCounty.setEthnicityData(precinct.getEthnicityData());
                    precinct.setCounty(tempCounty);
                    countyService.saveCounty(tempCounty);

                }


                 precinctDao.save(precinct);
                 precinctDao.flush();
                 return precinct;
            } else {
                return updateNeighbors(precinct);
            }
        }
    }


    public Precinct updateNeighbors(Precinct newPrecinct) {

        //todo warp this method with exception handler, return null if any exception raised ->resulting in a 400 status code in the controller layer


        var oldPrecinct = precinctDao.findById(newPrecinct.getId()).orElse(null);


        ArrayList<String> deleted = new ArrayList(oldPrecinct.getAdjacentPrecinctIds());
        ArrayList<String> added = new ArrayList(newPrecinct.getAdjacentPrecinctIds());

        deleted.remove(new ArrayList(newPrecinct.getAdjacentPrecinctIds()));
        added.remove(new ArrayList(oldPrecinct.getAdjacentPrecinctIds()));


        for (var i : precinctDao.findAllById(deleted)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().remove(newPrecinct.getId());
            precinctDao.save(i);
        }


        for (var i : precinctDao.findAllById(added)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().add(newPrecinct.getId());
            precinctDao.save(i);

        }
//
        if (newPrecinct.getEthnicityData() != null) {

            var tempCounty = countyService.selectCountyById(newPrecinct.getCountyId());

            //update ethnicity data if it's not null
            tempCounty.setEthnicityData(newPrecinct.getEthnicityData());
            countyService.saveCounty(tempCounty);
            newPrecinct.setCounty(tempCounty);


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


        Precinct merged = precincts.get(0);
        Precinct placeholder = precincts.get(1);


        placeholder.getAdjacentPrecinctIds().forEach(e -> {


                    if (!merged.getAdjacentPrecinctIds().contains(e)) {

                        if (!e.equals(merged.getId())) {
                            merged.getAdjacentPrecinctIds().add(e);
                        }

                        var temp = precinctDao.findById(e).orElse(null);
                        temp.getAdjacentPrecinctIds().add(merged.getId());
                        temp.getAdjacentPrecinctIds().remove(placeholder.getId());

                        precinctDao.save(temp);


                    }


                }

        );

        merged.getAdjacentPrecinctIds().remove(placeholder.getId());

        precinctDao.deleteById(placeholder.getId());

        return precinctDao.save(merged);

    }


}
