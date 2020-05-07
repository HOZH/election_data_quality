package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Hong Zheng, Hyejun Jeong
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Data
//@ToString(exclude = {"state"})
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "county")
@Table(name = "COUNTIES")
public class County {

  /** primary key for COUNTRY_TBL table */
  @Id
  @Column(length = 6)
  private String id;

  /** String of coordinates -> geo data */
  @Column(columnDefinition="longtext")
  private String coordinates;

  /** state that this county belongs to */
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties("county")
  @JsonIgnore
  private State state;

  /** List of Precinct objects that belong to this county */
  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "county")
  //@JsonIgnoreProperties("county")
  private List<Precinct> precincts;

  /** following are the demographic data in term of population of this precinct */
  @SuppressWarnings("JpaDataSourceORMInspection")
  @Column(name = "african_american")
  private int africanAmer;

  @SuppressWarnings("JpaDataSourceORMInspection")
  @Column(name = "native_american")
  private int nativeAmer;

  @SuppressWarnings("JpaDataSourceORMInspection")
  @Column(name = "pacific_islanders")
  private int pasifika;

  private int white;
  private int asian;
  private int others;

  /** helper field for initialing the belonging state */
  @Transient private String stateId;

  public String getStateId() {
    return state.getId();
  }

  public void setStateId(String id) {
    state.setId(id);
  }

  @Override
  public String toString() {
    return "cid: " + id +
            "\nwhite: " + white +
            "\nafricanAmer: " + africanAmer +
            "\nasian: " + asian +
            "\nnativeAmer: " + nativeAmer +
            "\nothers: " + others +
            "\npasifika: " + pasifika +
            "\nstateid: " + state.getId();
  }
}
