package com.example.demo.model;


import java.util.UUID;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Log {

    private UUID logId;
    private LogEnum logType;
    private LogState logState;
    private String comment;
    //    private ArrayList<Precinct> overlappingPrecinct;
//    private Precinct enclosingPrecinct;
//    private Precinct currentPrecinct;
//    private Precinct selectedPrecinct;
    private String timeStamp;


}
