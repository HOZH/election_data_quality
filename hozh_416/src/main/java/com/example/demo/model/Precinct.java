package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//fixme fix relationships between collection attrs and its container

@Data
@ToString(exclude = {"district"})
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRECINCT_TBL")
@Table

public class Precinct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String precinctId;
//    private String districtId;
    private String countyId;
//    private String stateId;
    private String canonicalName;
    private int population;
    private boolean ghost;

    @Transient
    private Long stateId;

    @Transient
    private Long districtId;


    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="state_id", nullable=false)
    @JsonIgnoreProperties("precincts")
    private District district;



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
