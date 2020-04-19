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



    //    private String stateId;
    private String canonicalName;

    @Transient
    private Long stateId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("district")

    private State state;



    @OneToMany( fetch = FetchType.LAZY, cascade=ALL, mappedBy = "district")
    @JsonIgnoreProperties("district")

    private List<Precinct> precincts;


}
