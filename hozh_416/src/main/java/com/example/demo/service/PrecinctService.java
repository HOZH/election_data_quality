package com.example.demo.service;


import com.example.demo.dao.PrecinctDao;
import com.example.demo.model.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrecinctService {


    private final PrecinctDao dao;

    @Autowired
    public PrecinctService(PrecinctDao precinctDao) {
        this.dao = precinctDao;
    }

    public Precinct savePrecinct(Precinct precinct) {


        var result = dao.save(precinct);


        System.err.println(precinct.getAdjacentPrecinctIds());
        System.err.println(precinct.getCoordinates().toString());
        dao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

            if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                e.getAdjacentPrecinctIds().add(result.getId());

                dao.save(e);


            }
        });


        return result;
    }



    public Precinct updateNeighbors(Precinct newPrecinct) {

        var oldPrecinct = dao.findById(newPrecinct.getId()).orElse(null);


        ArrayList<Long> deleted = new ArrayList(oldPrecinct.getAdjacentPrecinctIds());
        ArrayList<Long> added = new ArrayList(newPrecinct.getAdjacentPrecinctIds());

        deleted.remove(new ArrayList(newPrecinct.getAdjacentPrecinctIds()));
        added.remove(new ArrayList(oldPrecinct.getAdjacentPrecinctIds()));


        for (var i : dao.findAllById(deleted)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().remove(i.getAdjacentPrecinctIds().indexOf(newPrecinct.getId()));
            dao.save(i);
        }


        for (var i : dao.findAllById(added)) {

            //fixme may throw exception here
            i.getAdjacentPrecinctIds().add(newPrecinct.getId());
            dao.save(i);

        }

        return dao.save(newPrecinct);


    }

    public int deletePrecinctById(Long id) {


        dao.deleteById(id);

        return 0;
//        return precinctDao.deletePrecinctById(id);

    }


    public Precinct updatePrecinct(Precinct p) {

        return dao.save(p);


    }

    public Precinct selectPrecinctById(Long id) {
        return dao.findById(id).orElse(null);
    }

    public List<Precinct> selectAllPrecincts() {
        return dao.findAll();


    }

    public Precinct mergePrecincts(List<Precinct> precincts) {
        //fixme dunno what to do for now

        Precinct merged = precincts.get(0);
        Precinct placeholder = precincts.get(1);

//        List<Long> toRemove = new ArrayList<>();

        placeholder.getAdjacentPrecinctIds().forEach(e -> {


                    if (!merged.getAdjacentPrecinctIds().contains(e)) {

                        if(!e.equals(merged.getId()))
                        merged.getAdjacentPrecinctIds().add(e);

                        var temp = dao.findById(e).orElse(null);
                        temp.getAdjacentPrecinctIds().add(merged.getId());
                        temp.getAdjacentPrecinctIds().remove(placeholder.getId());

                        dao.save(temp);


                    }


//                    toRemove.add(e);


                }

        );

//        placeholder.getAdjacentPrecinctIds().removeAll(toRemove);
        merged.getAdjacentPrecinctIds().remove(placeholder.getId());
        dao.save(merged);
//        dao.save(placeholder);

        dao.deleteById(placeholder.getId());

        return null;
    }


}
