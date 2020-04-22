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
@Entity(name = "County_TBL")
@Table
public class County {


    @ElementCollection
    private Map<EthnicityEnum, Integer> ethnicityMap;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String canonicalName;

    @Transient
    private Long stateId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("county")

    private State state;


    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "county")
    @JsonIgnoreProperties("county")

    private List<Precinct> precincts;


}
