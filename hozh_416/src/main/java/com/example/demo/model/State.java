package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.ALL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "STATE_TBL")
@Table
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



//    private String stateId;
    private String canonicalName;



    @OneToMany( fetch = FetchType.LAZY, cascade=ALL, mappedBy = "state")
    @JsonIgnoreProperties("state")

    private List<Precinct> precincts;


}
