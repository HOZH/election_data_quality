package com.example.demo.service;


import com.example.demo.dao.PrecinctDao;
import com.example.demo.model.District;
import com.example.demo.model.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Service
public class PrecinctService {


    private final PrecinctDao precinctDao;
    private final DistrictService districtService;

    @Autowired
    public PrecinctService(PrecinctDao precinctDao, DistrictService districtService) {
        this.precinctDao = precinctDao;
        this.districtService = districtService;
    }


    public Precinct savePrecinct(Precinct precinct) {


        if (precinct.getId() == null) {

            var tempDistrict = districtService.selectDistrictById(precinct.getDistrictId());
            if (tempDistrict == null) {

                tempDistrict = new District();
                tempDistrict.setId(precinct.getDistrictId());
                tempDistrict.setStateId(precinct.getStateId());
                districtService.saveDistrict(tempDistrict);
            }

            precinct.setDistrict(tempDistrict);


            var result = precinctDao.save(precinct);


            precinctDao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

                if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                    e.getAdjacentPrecinctIds().add(result.getId());

                    precinctDao.save(e);


                }

            });


            return result;
        } else {

            var oldPrecinct = precinctDao.findById(precinct.getId()).orElse(null);

            if (oldPrecinct.getAdjacentPrecinctIds().containsAll(precinct.getAdjacentPrecinctIds()) && oldPrecinct.getAdjacentPrecinctIds().size() == precinct.getAdjacentPrecinctIds().size()) {
                return precinctDao.save(precinct);
            } else {
                return updateNeighbors(precinct);
            }
        }
    }


    public Precinct updateNeighbors(Precinct newPrecinct) {

        var oldPrecinct = precinctDao.findById(newPrecinct.getId()).orElse(null);


        ArrayList<Long> deleted = new ArrayList(oldPrecinct.getAdjacentPrecinctIds());
        ArrayList<Long> added = new ArrayList(newPrecinct.getAdjacentPrecinctIds());

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

        return precinctDao.save(newPrecinct);


    }

    public void deletePrecinctById(Long id) {


        precinctDao.deleteById(id);


    }


    public Precinct selectPrecinctById(Long id) {
        return precinctDao.findById(id).orElse(null);
    }

    public List<Precinct> selectAllPrecincts() {
        return precinctDao.findAll();


    }

    public Precinct mergePrecincts(List<Precinct> precincts) {

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
