package com.example.demo.service;


import com.example.demo.dao.PrecinctDao;
import com.example.demo.model.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrecinctService {


    private final PrecinctDao precinctDao;

    @Autowired
    public PrecinctService(@Qualifier("fakeDao") PrecinctDao precinctDao) {
        this.precinctDao = precinctDao;
        Precinct tempPrecinct = new Precinct();
        tempPrecinct.setPrecinctId("123");
        tempPrecinct.setPopulation(12345);
        this.precinctDao.insertPrecinct(UUID.randomUUID(), tempPrecinct);
    }


    public int addComment(UUID id, String newComment) {

        precinctDao.selectPrecinctById(id).getCommentBag().put(UUID.randomUUID(), newComment);
        return 0;

    }

    public int removeComment(UUID id, UUID cid) {

        precinctDao.selectPrecinctById(id).getCommentBag().remove(cid);
        return 0;

    }

    public int addNeighboringPrecincts(UUID id, UUID neighborId) {

        Precinct primaryPrecinct = precinctDao.selectPrecinctById(id);
        Precinct secondaryPrecinct = precinctDao.selectPrecinctById(neighborId);

        primaryPrecinct.getAdjacentPrecincts().add(secondaryPrecinct);
        secondaryPrecinct.getAdjacentPrecincts().add(primaryPrecinct);
        return 0;
    }

    public int removeNeighboringPrecincts(UUID id, UUID neighborId) {

        Precinct primaryPrecinct = precinctDao.selectPrecinctById(id);
        Precinct secondaryPrecinct = precinctDao.selectPrecinctById(neighborId);

        primaryPrecinct.getAdjacentPrecincts().remove(secondaryPrecinct);
        secondaryPrecinct.getAdjacentPrecincts().remove(primaryPrecinct);
        return 0;
    }

    public UUID createPrecinct(Precinct precinct) {

        UUID newID = UUID.randomUUID();
        precinctDao.insertPrecinct(newID, precinct);
        return newID;
    }

    public int createPrecinctById(UUID id, Precinct precinct) {

        precinctDao.insertPrecinct(id, precinct);
        return 0;
    }


    public int deletePrecinctById(UUID id) {

        return precinctDao.deletePrecinctById(id);

    }

    public int updatePrecinctById(UUID id, Precinct p) {

        return precinctDao.updatePrecinctById(id, p);


    }

    public Precinct selectPrecinctById(UUID id) {
        return precinctDao.selectPrecinctById(id);
    }

    public List<Precinct> selectAllPrecincts() {
        return precinctDao.selectAllPrecincts();
    }

    public Precinct mergePrecincts(UUID id1, UUID id2) {
        //fixme dunno what to do for now


        return null;
    }


}
