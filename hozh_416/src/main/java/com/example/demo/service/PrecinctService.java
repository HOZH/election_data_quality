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

    public UUID createPrecinctById(UUID id) {

        UUID newID = UUID.randomUUID();
        precinctDao.insertPrecinct(newID, new Precinct());


        return newID;
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


}
