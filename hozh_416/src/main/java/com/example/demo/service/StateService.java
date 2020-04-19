package com.example.demo.service;

import com.example.demo.dao.PrecinctDao;
import com.example.demo.dao.StateDao;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StateService {


    private final PrecinctDao precinctDao;
    private final StateDao stateDao;


    @Autowired
    public StateService(PrecinctDao precinctDao,StateDao stateDao) {
        this.precinctDao = precinctDao;
        this.stateDao = stateDao;

    }

    public State selectStateById(Long id){

        return stateDao.findById(id).orElse(null);
    }

}
