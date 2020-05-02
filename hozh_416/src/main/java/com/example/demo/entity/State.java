package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Hong Zheng, Hyejun Jeong
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "state")
@Table(name = "STATES")
public class State {
  /** primary key for STATE_TBL table */
  @Id
  @Column(length = 2)
  private String id;

  /** String of coordinates -> geo data */
  @Column(columnDefinition="longtext")
  private String coordinates;

  /** List of County objects that belong to this state */
  @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "state")
  @JsonIgnoreProperties("state")
  private List<County> counties;
}
