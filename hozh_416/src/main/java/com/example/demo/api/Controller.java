package com.example.demo.api;


import com.example.demo.model.District;
import com.example.demo.model.Precinct;
import com.example.demo.model.State;
import com.example.demo.service.DistrictService;
import com.example.demo.service.PrecinctService;
import com.example.demo.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final DistrictService districtService;


    @Autowired
    public Controller(PrecinctService precinctService, StateService stateService, DistrictService districtService) {
        this.precinctService = precinctService;
        this.stateService = stateService;
        this.districtService = districtService;
    }


    /**
     * Get method router for records of all the precincts
     *
     * @return a json array of all the precincts
     */
    @GetMapping(path = "/precinct/all")
    public List<Precinct> getAllPrecinctRequest() {


        return precinctService.selectAllPrecincts();
    }

    /**
     * Get method router for getting record of a state by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return State Object base on its id, null when the Object is not found in our database via query
     */
    @GetMapping(path = "/state/{id}")
    public State getStateByIdRequest(@PathVariable("id") Long id) {


        return stateService.selectStateById(id);

    }

    /**
     * Get method router for getting record of a district by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return District object base on its id, null when the Object is not found in our database via query
     */
    @GetMapping(path = "/district/{id}")
    public District getDistrictByIdRequest(@PathVariable("id") Long id) {

        var temp = districtService.selectDistrictById(id);

        return temp;

    }


    /**
     * Get method router for getting record of a precinct by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return Precinct object base on its id, null when the Object is not found in our database via query
     */
    @GetMapping(path = "/precinct/{id}")
    public Precinct getPrecinctByIdRequest(@PathVariable("id") Long id) {

        return precinctService.selectPrecinctById(id);


    }


    /**
     * Delete method router for delete record of precinct in database by given id
     *
     * @param id -> type Long, fetched through path variable
     * @return String of deletion state
     * //todo may change to void before code review/final delivery
     */
    @DeleteMapping(path = "/precinct/{id}")
    public String removePrecinctByIdRequest(@PathVariable("id") Long id) {

        precinctService.deletePrecinctById(id);

        return "OK";
    }


    /**
     * Put/Post method router for adding/updating a precinct with database.
     * Distinction between add/update will be detect on service layer
     *
     * @param precinct -> type Precinct, fetched through request body
     * @return Precinct object of saved precinct
     * //todo may change type of return before code review/final delivery
     */
    @RequestMapping(path = "/precinct", method = {RequestMethod.POST, RequestMethod.PUT})
    public Precinct savePrecinctRequest(@Valid @NotNull @RequestBody Precinct precinct) {

        return precinctService.savePrecinct(precinct);

    }

    /**
     * Delete method router for merging two precincts
     * index 0 -> primary precinct, index 1 deleting precinct
     * front end should already merge all the data from the deleting precinct expect for the adjacentPrecinctIds
     *
     * @param precincts -> type List<Precinct>, fetched through request body
     * @return Precinct object of survived precinct
     */

    @DeleteMapping(path = "/precinct/merge")
    public Precinct mergePrecinctsRequest(@Valid @NotNull @RequestBody List<Precinct> precincts) {


        return this.precinctService.mergePrecincts(precincts);

    }


}