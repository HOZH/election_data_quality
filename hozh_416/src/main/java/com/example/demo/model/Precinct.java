package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Data
@ToString(exclude = {"county"})
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRECINCT_TBL")
@Table

public class Precinct {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private String canonicalName;
    private int population;
    private boolean ghost;

    private boolean multipleBorder;

    @Transient
    private String stateId;

    @Transient
    private String countyId;


//    private String defaultId;



    private String coordinates;



    //fixme will be change later
    @Transient
    private Map<EthnicityEnum, Integer> ethnicityMap; //todo is required when filling data into database/ not required for production stage

//    @Transient
//    private Long districtId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("precincts")
    private County county;


    @ElementCollection
    private Map<ElectionEnum, Integer> electionMap;

    @ElementCollection
    private List<String> adjacentPrecinctIds;


    //fixme change naming later
    @ElementCollection
    private List<String> enclosingPrecinctIds;

//    @Column(length = 2000)
//    @ElementCollection
//    private List<ArrayList<ArrayList<Double>>> coordinates;



    //todo change name later
    @ElementCollection
    private Map<Integer, String> logBag;


}
