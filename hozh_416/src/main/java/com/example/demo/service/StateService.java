package com.example.demo.service;

import com.example.demo.entity.State;
import com.example.demo.entitymanager.StateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */

@Service
public class StateService {


    private final StateEntityManager stateEntityManager;


    @Autowired
    public StateService(StateEntityManager stateEntityManager) {
        this.stateEntityManager = stateEntityManager;

    }

    public State selectStateById(String id) {


        try {
            return stateEntityManager.findById(id).orElse(null);


        } catch (Exception ex) {

            //fixme for now we may encounter Illegal arg exception, change generic handler to more concrete one later
            System.err.println(ex.getMessage());
            return null;
        }
    }


    public State saveState(State state) {


        try {

            return stateEntityManager.save(state);
        } catch (Exception ex) {
            //fixme for now we may encounter null pointer exception, change generic handler to more concrete one later

            System.err.println(ex.getMessage());
            return null;
        }
    }

}
