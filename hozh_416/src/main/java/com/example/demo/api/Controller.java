package com.example.demo.api;


import com.example.demo.model.County;
import com.example.demo.model.Precinct;
import com.example.demo.model.State;
import com.example.demo.service.CountyService;
import com.example.demo.service.PrecinctService;
import com.example.demo.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@RequestMapping("api")
@RestController
public class Controller {


    private final PrecinctService precinctService;
    private final StateService stateService;
    private final CountyService countyService;


    @Autowired
    public Controller(PrecinctService precinctService, StateService stateService, CountyService countyService) {
        this.precinctService = precinctService;
        this.stateService = stateService;
        this.countyService = countyService;
    }


    /**
     * Get method router for records of all the precincts
     *
     * @return a json array of all the precincts,
     * status code is always set to 200
     * unless the accessibility to the server is blocked which will automatically
     * return a status code with 500 level
     */
    @GetMapping(path = "/precinct/all")
    public ResponseEntity<List<Precinct>> getAllPrecinctRequest() {


        return new ResponseEntity<>(precinctService.selectAllPrecincts(), HttpStatus.OK);
    }

    /**
     * Get method router for getting record of a state by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return State Object base on its id, null when the Object is not found in our database via query
     * status code is set to 200 if a record in the database is found otherwise 404
     */
    @GetMapping(path = "/state/{id}")
    public ResponseEntity<State> getStateByIdRequest(@PathVariable("id") String id) {


        var queryResult = stateService.selectStateById(id);

        return new ResponseEntity<>(queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

    }

    /**
     * Get method router for getting record of a district by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return District object base on its id, null when the Object is not found in our database via query
     * status code is set to 200 if a record in the database is found otherwise 404
     */
    @GetMapping(path = "/county/{id}")
    public ResponseEntity<County> getCountyByIdRequest(@PathVariable("id") String id) {

        var queryResult = countyService.selectCountyById(id);

        return new ResponseEntity<>(queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

    }


    /**
     * Get method router for getting record of a precinct by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return Precinct object base on its id, null when the Object is not found in our database via query
     * status code is set to 200 if a record in the database is found otherwise 404
     */
    @GetMapping(path = "/precinct/{id}")
    public ResponseEntity<Precinct> getPrecinctByIdRequest(@PathVariable("id") String id) {


        var queryResult = precinctService.selectPrecinctById(id);
        return new ResponseEntity<>(queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);


    }


    /**
     * Delete method router for delete record of precinct in database by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return String of deletion state
     * status code is always set to 200
     * //todo may change to void before code review/final delivery
     */
    @DeleteMapping(path = "/precinct/{id}")
    public ResponseEntity<String> removePrecinctByIdRequest(@PathVariable("id") String id) {

        precinctService.deletePrecinctById(id);

        return new ResponseEntity<>("OK", HttpStatus.OK);

    }


    /**
     * Put/Post method router for adding/updating a precinct with database.
     * Distinction between add/update will be detect on service layer
     *
     * @param precinct -> type Precinct, fetched through request body
     * @return Precinct object of saved precinct
     * status code is set to 200 if insertion/modification of the precinct is completed otherwise 400
     * //todo may change type of return before code review/final delivery
     */
    @RequestMapping(path = "/precinct", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Precinct> savePrecinctRequest(@Valid @NotNull @RequestBody Precinct precinct) {
        System.out.println("123");

        System.err.println(precinct.getId());
        System.out.println("123");


        var operationResult = precinctService.savePrecinct(precinct);

        return new ResponseEntity<>(operationResult, operationResult == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);


    }

    /**
     * Delete method router for merging two precincts
     * index 0 -> primary precinct, index 1 deleting precinct
     * front end should already merge all the data from the deleting precinct expect for the adjacentPrecinctIds
     *
     * @param precincts -> type List<Precinct>, fetched through request body
     * @return Precinct object of survived precinct
     * status code is set to 200 if the merging operation of two precincts is completed otherwise 400
     */

    @DeleteMapping(path = "/precinct/merge")
    public ResponseEntity<Precinct> mergePrecinctsRequest(@Valid @NotNull @RequestBody List<Precinct> precincts) {


        var mergingResult = precinctService.mergePrecincts(precincts);

        return new ResponseEntity<>(mergingResult, mergingResult == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);


    }


    //fixme testing route
//@GetMapping(path = "/precinct/default/{defaultId}")
//
//public Precinct findPrecinctByDefaultIdRequest(@PathVariable("defaultId") String defaultId) {
//
//return precinctService.selectByDefaultId(defaultId);
//    }


}