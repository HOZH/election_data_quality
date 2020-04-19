package com.example.demo.model;

/*
 * @created 19/03/2020 - 4:14 PM
 * @project  hozh-416-server
 * @author Hong Zheng
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@ToString(exclude = {"state"})
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "District_TBL")
@Table
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String canonicalName;

    @Transient
    private Long stateId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("district")

    private State state;


    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "district")
    @JsonIgnoreProperties("district")

    private List<Precinct> precincts;


}
