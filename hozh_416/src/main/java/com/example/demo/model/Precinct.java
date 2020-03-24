package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Precinct {


    boolean isGhostPrecinct;
    private String stateId;
    private String countyId;
    private String districtId;
    private String PrecinctId;
    private int population;
    private int p16Dem;
    private int p16Rep;
    private int c16Dem;
    private int c16Rep;
    private int c18Dem;
    private int c18Rep;
    private int whiteA;
    private int africanA;
    private int asianA;
    private int hispanicA;
    private int nativeA;
    private ArrayList<Precinct> adjacentPrecincts;
    private HashMap<UUID, String> commentBag;

    public Precinct() {
    }

    public Precinct(String stateId, String countyId, String districtId, String precinctId, int population, int p16Dem, int p16Rep, int c16Dem, int c16Rep, int c18Dem, int c18Rep, int whiteA, int africanA, int asianA, int hispanicA, int nativeA, boolean isGhostPrecinct) {

        PrecinctId = precinctId;
        this.population = population;
        this.p16Dem = p16Dem;
        this.p16Rep = p16Rep;
        this.c16Dem = c16Dem;
        this.c16Rep = c16Rep;
        this.c18Dem = c18Dem;
        this.c18Rep = c18Rep;
        this.whiteA = whiteA;
        this.africanA = africanA;
        this.asianA = asianA;
        this.hispanicA = hispanicA;
        this.nativeA = nativeA;


        this.adjacentPrecincts = new ArrayList<>();
        this.commentBag = new HashMap<>();


        this.stateId = stateId;
        this.countyId = countyId;
        this.districtId = districtId;


        this.isGhostPrecinct = isGhostPrecinct;


    }

    public boolean isGhostPrecinct() {
        return isGhostPrecinct;
    }

    public void setGhostPrecinct(boolean ghostPrecinct) {
        isGhostPrecinct = ghostPrecinct;
    }

    public HashMap<UUID, String> getCommentBag() {
        return commentBag;
    }

    public void setCommentBag(HashMap<UUID, String> commentBag) {
        this.commentBag = commentBag;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getPrecinctId() {
        return PrecinctId;
    }

    public void setPrecinctId(String precinctId) {
        PrecinctId = precinctId;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getP16Dem() {
        return p16Dem;
    }

    public void setP16Dem(int p16Dem) {
        this.p16Dem = p16Dem;
    }

    public int getP16Rep() {
        return p16Rep;
    }

    public void setP16Rep(int p16Rep) {
        this.p16Rep = p16Rep;
    }

    public int getC16Dem() {
        return c16Dem;
    }

    public void setC16Dem(int c16Dem) {
        this.c16Dem = c16Dem;
    }

    public int getC16Rep() {
        return c16Rep;
    }

    public void setC16Rep(int c16Rep) {
        this.c16Rep = c16Rep;
    }

    public int getC18Dem() {
        return c18Dem;
    }

    public void setC18Dem(int c18Dem) {
        this.c18Dem = c18Dem;
    }

    public int getC18Rep() {
        return c18Rep;
    }

    public void setC18Rep(int c18Rep) {
        this.c18Rep = c18Rep;
    }

    public int getWhiteA() {
        return whiteA;
    }

    public void setWhiteA(int whiteA) {
        this.whiteA = whiteA;
    }

    @Override
    public String toString() {
        return "Precinct{" +
                "stateId='" + stateId + '\'' +
                ", countyId='" + countyId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", PrecinctId='" + PrecinctId + '\'' +
                ", population=" + population +
                ", p16Dem=" + p16Dem +
                ", p16Rep=" + p16Rep +
                ", c16Dem=" + c16Dem +
                ", c16Rep=" + c16Rep +
                ", c18Dem=" + c18Dem +
                ", c18Rep=" + c18Rep +
                ", whiteA=" + whiteA +
                ", africanA=" + africanA +
                ", asianA=" + asianA +
                ", hispanicA=" + hispanicA +
                ", nativeA=" + nativeA +
                ", adjacentPrecincts=" + adjacentPrecincts +
                '}';
    }

    public int getAfricanA() {
        return africanA;
    }

    public void setAfricanA(int africanA) {
        this.africanA = africanA;
    }

    public int getAsianA() {
        return asianA;
    }

    public void setAsianA(int asianA) {
        this.asianA = asianA;
    }

    public int getHispanicA() {
        return hispanicA;
    }

    public void setHispanicA(int hispanicA) {
        this.hispanicA = hispanicA;
    }

    public int getNativeA() {
        return nativeA;
    }

    public void setNativeA(int nativeA) {
        this.nativeA = nativeA;
    }


    public ArrayList<Precinct> getAdjacentPrecincts() {
        return adjacentPrecincts;
    }

    public void setAdjacentPrecincts(ArrayList<Precinct> adjacentPrecincts) {
        this.adjacentPrecincts = adjacentPrecincts;
    }


}
