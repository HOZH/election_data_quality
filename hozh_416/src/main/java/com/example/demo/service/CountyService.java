package com.example.demo.service;


import com.example.demo.entity.County;
import com.example.demo.entity.State;
import com.example.demo.entitymanager.CountyEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Service
public class CountyService {


    private final CountyEntityManager countyEntityManager;
    private final StateService stateService;


    @Autowired
    public CountyService(CountyEntityManager countyEntityManager, StateService stateService) {
        this.countyEntityManager = countyEntityManager;
        this.stateService = stateService;
    }


    public County selectCountyById(String id) {
        try {

            return countyEntityManager.findById(id).orElse(null);
        } catch (Exception ex) {


            //fixme for now we may encounter Illegal arg exception, change generic handler to more concrete one later

            System.err.println(ex.getMessage());
            return null;
        }

    }


    public County saveCounty(County county) {

        try {


            //if the target county is not yet assigned to a state (case newly created county)
            // create a state by the stateId field in county object
            if (county.getState() == null) {
                var targetState = stateService.selectStateById(county.getStateId());

                if (targetState == null) {
                    targetState = new State();
                    targetState.setId(county.getStateId());
                    stateService.saveState(targetState);
                }

                county.setState(targetState);

            }

            return countyEntityManager.save(county);


        } catch (Exception ex) {


            //fixme for now we may encounter null pointer exception, change generic handler to more concrete one later

            System.err.println(ex.getMessage());
            return null;
        }


    }


}
