package com.example.demo.dao;


import com.example.demo.model.Precinct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrecinctDao {


    int insertPrecinct(UUID id, Precinct precinct);


    default  Precinct formPrecinctFromObj(Precinct p){

       Precinct tempPrecinct= new Precinct(p.getStateId(),p.getCountyId(),p.getDistrictId(),p.getPrecinctId(), p.getPopulation(), p.getP16Dem(), p.getP16Rep(),
                p.getC16Dem(), p.getC16Rep(), p.getC18Dem(), p.getC18Rep(), p.getWhiteA(),
                p.getAfricanA(), p.getAsianA(), p.getHispanicA(), p.getNativeA());


        return tempPrecinct;
    }

    default int insertPrecinct(Precinct precinct) {

        UUID id = UUID.randomUUID();

        return insertPrecinct(id, precinct);

    }


    int deletePrecinctById(UUID id);

    int updatePrecinctById(UUID id, Precinct precinct);

    Precinct selectPrecinctById(UUID id);


    List<Precinct> selectAllPrecincts();
}
