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
        dao.findAllById(precinct.getAdjacentPrecinctIds()).forEach(e -> {

            if (!e.getAdjacentPrecinctIds().contains(result.getId())) {
                e.getAdjacentPrecinctIds().add(result.getId());

                dao.save(e);


            }
        });


        return result;
    }

//
//    public int addComment(UUID id, String newComment) {
//
//        precinctDao.selectPrecinctById(id).getCommentBag().put(UUID.randomUUID(), newComment);
//        return 0;
//
//    }
//
//    public int removeComment(UUID id, UUID cid) {
//
//        precinctDao.selectPrecinctById(id).getCommentBag().remove(cid);
//        return 0;
//
//    }
//
//    public int addNeighboringPrecincts(UUID id, UUID neighborId) {
//
//        Precinct primaryPrecinct = precinctDao.selectPrecinctById(id);
//        Precinct secondaryPrecinct = precinctDao.selectPrecinctById(neighborId);
//
//        primaryPrecinct.getAdjacentPrecincts().add(secondaryPrecinct);
//        secondaryPrecinct.getAdjacentPrecincts().add(primaryPrecinct);
//        return 0;
//    }
//
//    public int removeNeighboringPrecincts(UUID id, UUID neighborId) {
//
//        Precinct primaryPrecinct = precinctDao.selectPrecinctById(id);
//        Precinct secondaryPrecinct = precinctDao.selectPrecinctById(neighborId);
//
//        primaryPrecinct.getAdjacentPrecincts().remove(secondaryPrecinct);
//        secondaryPrecinct.getAdjacentPrecincts().remove(primaryPrecinct);
//        return 0;
//    }
//
//    public UUID createPrecinct(Precinct precinct) {
//
//        UUID newID = UUID.randomUUID();
//        precinctDao.insertPrecinct(newID, precinct);
//        return newID;
//    }
//
//    public int createPrecinctById(UUID id, Precinct precinct) {
//
//        precinctDao.insertPrecinct(id, precinct);
//        return 0;
//    }
//
//

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

//    public Precinct mergePrecincts(UUID id1, UUID id2) {
//        //fixme dunno what to do for now
//
//
//        return null;
//    }


}
