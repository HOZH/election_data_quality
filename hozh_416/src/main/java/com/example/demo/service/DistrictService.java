package com.example.demo.service;


import com.example.demo.dao.DistrictDao;
import com.example.demo.model.District;
import com.example.demo.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {



    private final DistrictDao districtDao;
    private final StateService stateService;


    @Autowired
    public DistrictService(DistrictDao districtDao,StateService stateService) {
        this.districtDao = districtDao;
        this.stateService = stateService;
    }


    public District selectDistrictById(Long id){

        System.err.println("1"+districtDao.findById(id).orElse(null)+" \n");


        return districtDao.findById(id).orElse(null);

//        return districtDao.findById(id).orElse(null);
    }


    public District saveDistrict(District district){

        var tempState = stateService.selectStateById(district.getStateId());

//        var tempState = stateDao.findById(precinct.getStateId()).orElse(null);
        if(tempState==null)
        {
            tempState = new State();
            tempState.setId(district.getStateId());
            stateService.saveState(tempState);
//                stateDao.flush();
        }

        System.err.println(tempState);
        district.setState(tempState);



        return districtDao.save(district);
    }


}
