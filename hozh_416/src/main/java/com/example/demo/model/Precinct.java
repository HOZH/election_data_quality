package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//fixme fix relationships between collection attrs and its container

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "adjacentTo","adjacentPrecinct"})
@EqualsAndHashCode(exclude = { "adjacentTo","adjacentPrecinct"})
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


//    @ManyToOne
//    @JoinColumn
//    private Precinct parent;

    //    @OneToMany(mappedBy = "parent")
    @JsonIgnoreProperties("adjacentTo")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Precinct> adjacentPrecincts;


    @JsonIgnoreProperties("adjacentPrecinct")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adjacentPrecincts" )
    private List<Precinct> adjacentTo;

    @Transient
//    @ElementCollection
    private List<Long> adjacentPrecinctIds;

    @ElementCollection
    private List<ArrayList<ArrayList<Double>>> coordinates;

    @ElementCollection
    private Map<Integer, String> logBag;


}
