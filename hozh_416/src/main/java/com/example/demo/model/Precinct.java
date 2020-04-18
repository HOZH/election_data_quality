package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//fixme fix relationships between collection attrs and its container

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRECINCT_TBL")
@Table
public class Precinct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private UUID precinctId;
    private UUID districtId;
    private UUID countyId;
    private UUID stateId;
    private String canonicalName;
    private int population;
    private boolean ghost;


    @ElementCollection
    private Map<EthnicityEnum, Integer> ethnicityMap;

    @ElementCollection
    private Map<ElectionEnum, Integer> electionMap;

    @ElementCollection
    private List<Long> adjacentPrecinctIds;

    @ElementCollection
    private List<ArrayList<ArrayList<Double>>> coordinates;

    @ElementCollection
    private Map<Integer, String> logBag;


}
