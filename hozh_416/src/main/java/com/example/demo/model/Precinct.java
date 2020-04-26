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

    @Id
    @JsonProperty("precinctId")
//    @Column(length = 60)
    private String id;



    @Transient
    private String canonicalName;
    private int population;
    private boolean ghost;

    private boolean multipleBorder;


    @Transient
    private String stateId;


    @Transient
    private String countyId;




    @Column(length = 2000)
    private String coordinates;


//    //todo is required when filling data into database/ not required for production stage
//    @Transient
//    private Map<EthnicityEnum, Integer> ethnicityData;






    @Transient
    private boolean demographicDataModified;

    @Transient
    private int white;

    @Transient
    private int africanAmerican;

    @Transient
    private int asianPacific;

    @Transient
    private int nativeAmerican;

    @Transient
    private int others;

    @Transient
    private int pacificIslanders;












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


    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "LOG_BAG")
    private Map<Integer, String> logBag;


}
