package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @author Hong Zheng, Hyejun Jeong
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */

@Data
//@ToString(exclude = {"county"})
@NoArgsConstructor
@Entity(name = "precinct")
@Table(name = "PRECINCTS")
public class Precinct {

  /** primary key for PRECINCT_TBL */
  @Id
  @Column(length = 128)
  @JsonProperty("precinctId")
  private String id;

  /** flag to determine whether this precinct is a ghost precinct */
  @Column(name = "is_ghost")  
  private boolean ghost;

  /** flag to determine whether this precinct contains multiple border error */
  @Column(name = "has_multiple_border")
  private boolean multipleBorder;

  /** String of coordinates -> geo data */
  @Column(columnDefinition="longtext")
  private String coordinates;

  /** county that this precinct belongs to */
  @JsonIgnoreProperties("precinct, county, state")
  @ManyToOne(fetch = FetchType.LAZY)
  private County county;

  /** election data of this precinct */
  @SuppressWarnings("JpaDataSourceORMInspection")
  @JsonIgnore
  @ElementCollection
  @MapKeyColumn(name="election_type")
  @Column(name="election_result")
  @CollectionTable(name = "ELECTION_DATA")
  private Map<ElectionEnum, Integer> electionData;

  /** list of precinct's ids for which adjacent to this precinct */
  @SuppressWarnings("JpaDataSourceORMInspection")
  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "ADJACENT_PRECINCTS")
  @Column(name = "adjacent_precinct_ids",columnDefinition="longtext")
  private List<String> adjPrecIds;

  /** list of precinct's ids for which enclosing to this precinct -> used for determine errors */
  @SuppressWarnings("JpaDataSourceORMInspection")
  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "ENCLOSING_PRECINCTS")
  @Column(name = "enclosing_precinct_ids",columnDefinition="longtext")
  private List<String> enclPrecIds;

  /** map for log messages */
  @SuppressWarnings("JpaDataSourceORMInspection")
  @JsonIgnore
  @ElementCollection
  @MapKeyColumn(name="id")
  @Column(name="log")
  @CollectionTable(name = "LOGS")
  private Map<Integer, String> logBag;

  /** following are the help fields of the object which won't be persist in the database */
  @Transient 
  private String canonicalName;
  
  /** help field for mapping the precinct to its belonging county */
  @Transient 
  private String stateId;
  
  /** help field for mapping the precinct's belonging county to its belonging state */
  @Transient 
  private String countyId;

  /** flag to determine whether to update this precinct's belonging county's demographic data */
  @Transient 
  private boolean demoModified;

  /**
   * following are the demographic population help fields, can be ignore if demographicDataModified
   * is set to false
   */
  @Transient
  private int white;
  @Transient 
  private int africanAmer;
  @Transient 
  private int asian;
  @Transient 
  private int nativeAmer;
  @Transient 
  private int others;
  @Transient 
  private int pasifika;

  public int getWhite() {
    return county.getWhite();
  }
  public int getAfricanAmer() {
    return county.getAfricanAmer();
  }
  public int getAsian() {
    return county.getAsian();
  }
  public int getNativeAmer() {
    return county.getNativeAmer();
  }
  public int getOthers() {
    return county.getOthers();
  }
  public int getPasifika() {
    return county.getPasifika();
  }
  public String getStateId() {
    return county.getStateId();
  }
  public String getCountyId() {
    return county.getId();
  }

  public void setCountyId(String id) {
    county.setId(id);
  }

  @Override
  public String toString() {
    return "pid: " + id +
            "\ncoord: " + coordinates +
            "\nisghost: " + ghost +
            "\nhasmultiborder: " + multipleBorder +
            "\ncountyid: " + county.getId();
  }
}
