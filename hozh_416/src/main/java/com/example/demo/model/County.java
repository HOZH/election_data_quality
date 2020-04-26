package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.ALL;


/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Data
@ToString(exclude = {"state"})
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "COUNTY_TBL")
@Table
public class County {


    @SuppressWarnings("JpaDataSourceORMInspection")
    @ElementCollection
    @CollectionTable(name = "ETHNICITY_DATA")

    private Map<EthnicityEnum, Integer> ethnicityData;

    @Id
    @Column(length = 60)

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private String canonicalName;

    @Transient
    private String stateId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("county")

    private State state;


    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "county")
    @JsonIgnoreProperties("county")

    private List<Precinct> precincts;


}
