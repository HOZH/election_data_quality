package com.example.demo.service;


import com.example.demo.dao.DistrictDao;
import com.example.demo.model.District;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Service
public class DistrictService {


    private final DistrictDao districtDao;
    private final StateService stateService;


    @Autowired
    public DistrictService(DistrictDao districtDao, StateService stateService) {
        this.districtDao = districtDao;
        this.stateService = stateService;
    }


    public District selectDistrictById(Long id) {


        return districtDao.findById(id).orElse(null);

    }


    public District saveDistrict(District district) {

        var tempState = stateService.selectStateById(district.getStateId());

        if (tempState == null) {
            tempState = new State();
            tempState.setId(district.getStateId());
            stateService.saveState(tempState);
        }

        district.setState(tempState);


        return districtDao.save(district);
    }


}
