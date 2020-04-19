package com.example.demo.model;

/*
 * @created 19/03/2020 - 4:14 PM
 * @project  hozh-416-server
 * @author Hong Zheng
 */

import java.util.UUID;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Log {

    private UUID logId;
    private LogEnum logType;
    private LogState logState;
    private String comment;

    private String timeStamp;


}
