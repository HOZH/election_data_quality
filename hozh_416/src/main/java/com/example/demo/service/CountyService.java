package com.example.demo.service;


import com.example.demo.dao.CountyDao;
import com.example.demo.model.County;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Service
public class CountyService {


    private final CountyDao countyDao;
    private final StateService stateService;


    @Autowired
    public CountyService(CountyDao countyDao, StateService stateService) {
        this.countyDao = countyDao;
        this.stateService = stateService;
    }


    public County selectCountyById(String id) {


        return countyDao.findById(id).orElse(null);

    }


    public County saveCounty( County county) {

        System.out.print(county);

        var tempState = stateService.selectStateById(county.getStateId());

        if (tempState == null) {
            tempState = new State();
            tempState.setId(county.getStateId());
            stateService.saveState(tempState);
        }

        county.setState(tempState);


        return countyDao.save(county);
    }


}
