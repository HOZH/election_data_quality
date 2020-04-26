package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "STATE_TBL")
@Table
public class State {

    @Id
    @Column(length = 60)

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private String canonicalName;


    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "state")
    @JsonIgnoreProperties("state")

    private List<County> counties;


}
