package com.example.demo.service;
/*
 * @created 19/03/2020 - 4:14 PM
 * @project  hozh-416-server
 * @author Hong Zheng
 */

import com.example.demo.dao.StateDao;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StateService {


    private final StateDao stateDao;


    @Autowired
    public StateService(StateDao stateDao) {
        this.stateDao = stateDao;

    }

    public State selectStateById(Long id) {

        return stateDao.findById(id).orElse(null);
    }


    public State saveState(State state) {


        return stateDao.save(state);
    }

}
