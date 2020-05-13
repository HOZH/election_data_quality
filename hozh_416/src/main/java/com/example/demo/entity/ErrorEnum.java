package com.example.demo.entity;

public enum ErrorEnum {
    /** Errors */
    ERRGHOST,   // potential ghost
    ERROVER,    // overlapping
    ERRENCL,    // enclosed
    ERRDATA,    // data anomaly

    /** Change log */
    HSHAPE,     // changed shape
    CHDATA,     // changed data
    ADDNEIGH,   // add neighbor
    DELNEIGH    // delete neighbor
}
