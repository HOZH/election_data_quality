package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    /**
     * primary key for COUNTRY_TBL table
     */
    @Id
    private String id;

    /**
     * state that this county belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("county")
    private State state;

    /**
     * List of Precinct objects that belong to this county
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "county")
    @JsonIgnoreProperties("county")
    private List<Precinct> precincts;

    /**
     * following are the demographic data in term of population of this precinct
     */
    private int white;

    private int africanAmerican;

    private int asianPacific;

    private int nativeAmerican;

    private int others;

    private int pacificIslanders;

    /**
     * helper field for initialing the belonging state
     */
    @Transient
    private String stateId;
}
