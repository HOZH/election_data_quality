package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@NoArgsConstructor
@Entity(name = "PRECINCT_TBL")
@Table

public class Precinct {

//    public Precinct() {
//    }

    @Id
    @JsonProperty("precinctId")
    @Column(length = 60)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
//
//    @JsonProperty("cus")
//    private String temp;




    @Transient
    private String canonicalName;
    private int population;
    private boolean ghost;

    private boolean multipleBorder;


    //todo get rid of this two field later
    @Transient
    private String stateId;

    //todo get rid of this two field later

    @Transient
    private String countyId;


//    private String defaultId;


    private String coordinates;



    //fixme will be change later
    @Transient
    private Map<EthnicityEnum, Integer> ethnicityData; //todo is required when filling data into database/ not required for production stage

//    @Transient
//    private Long districtId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("precincts")
    private County county;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "ELECTION_DATA")
    private Map<ElectionEnum, Integer> electionData;


    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "ADJACENT_PRECINCT_IDS")
    private List<String> adjacentPrecinctIds;


    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "ENCLOSING_PRECINCT_IDS")
    private List<String> enclosingPrecinctIds;

//    @Column(length = 2000)
//    @ElementCollection
//    private List<ArrayList<ArrayList<Double>>> coordinates;


    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "LOG_BAG")
    private Map<Integer, String> logBag;


}
