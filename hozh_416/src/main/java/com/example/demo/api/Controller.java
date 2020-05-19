package com.example.demo.api;

import com.example.demo.entity.County;
import com.example.demo.entity.Precinct;
import com.example.demo.entity.State;
import com.example.demo.service.CountyService;
import com.example.demo.service.PrecinctService;
import com.example.demo.service.StateService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Hong Zheng, Hyejun Jeong
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@RequestMapping("api")
@RestController
public class Controller {

  @Autowired private PrecinctService precinctService;
  @Autowired private StateService stateService;
  @Autowired private CountyService countyService;

  /**
   * Get method router for getting record of a state by given id
   *
   * @param id type Long, fetched through path variable
   * @return State Object base on its id, null when the Object is not found in our database via
   *     query status code is set to 200 if a record in the database is found otherwise 404
   */
  @JsonView(View.CountyCoords.class)
  @GetMapping(path = "/state/{id}")
  private ResponseEntity<State> getStateByIdHandler(@PathVariable("id") String id) {
    var queryResult = stateService.selectStateById(id);
    return new ResponseEntity<>(
        queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
  }

  @JsonView(View.StateView.class)
  @GetMapping(path = "/state/all")
  private ResponseEntity<List<State>> getAllStatesHandler() {
    return new ResponseEntity<>(stateService.selectAllStates(), HttpStatus.OK);
  }

  /**
   * Get method router for getting record of a district by given id
   *
   * @param id type Long, fetched through path variable
   * @return District object base on its id, null when the Object is not found in our database via
   *     query status code is set to 200 if a record in the database is found otherwise 404
   */
  @JsonView(View.PrecinctCoords.class)
  @GetMapping(path = "/county/{id}")
  private ResponseEntity<County> getCountyByIdHandler(@PathVariable("id") String id) {
    var queryResult = countyService.selectCountyById(id);
    return new ResponseEntity<>(
        queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
  }

  /**
   * Get method router for getting record of a precinct by given id
   *
   * @param id type Long, fetched through path variable
   * @return Precinct object base on its id, null when the Object is not found in our database via
   *     query status code is set to 200 if a record in the database is found otherwise 404
   */
  @JsonView(View.PrecinctData.class)
  @GetMapping(path = "/precinct/{id}")
  private ResponseEntity<Precinct> getPrecinctByIdHandler(@PathVariable("id") String id) {
    var queryResult = precinctService.selectPrecinctById(id);
    return new ResponseEntity<>(
        queryResult, queryResult == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
  }

  /**
   * Delete method router for delete record of precinct in database by given id
   *
   * @param id type Long, fetched through path variable
   * @return String of deletion state status code is always set to 200
   */
  @JsonView(View.PrecinctData.class)
  @DeleteMapping(path = "/precinct/{id}")
  private ResponseEntity<String> removePrecinctByIdHandler(@PathVariable("id") String id) {
    precinctService.deletePrecinctById(id);
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  /**
   * Post method router for adding a precinct with database.
   *
   * @param precinct type Precinct, fetched through request body
   * @return Precinct object of saved precinct status code is set to 200 if insertion of the
   *     precinct is completed otherwise 400
   */
  @PostMapping
  private ResponseEntity<Precinct> addPrecinctHandler(
      @Valid @NotNull @RequestBody Precinct precinct) {
    Precinct operationResult;
    operationResult = precinctService.addPrecinct(precinct);

    return new ResponseEntity<>(
        operationResult, operationResult == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
  }

  /**
   * Put method router for updating a precinct with database.
   *
   * @param precinct type Precinct, fetched through request body
   * @return Precinct object of saved precinct status code is set to 200 if modification of the
   *     precinct is completed otherwise 400
   */
  @JsonView(View.PrecinctData.class)
  @PutMapping
  //@RequestMapping(path = "/precinct", method = RequestMethod.PUT)
  private ResponseEntity<Precinct> updatePrecinctHandler(//HttpServletRequest request,
      @Valid @NotNull @RequestBody Precinct precinct) {
    System.out.println("precinct=" + precinct);

    Precinct operationResult;
    operationResult = precinctService.updatePrecinct(precinct);
    return new ResponseEntity<>(
        operationResult, operationResult == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
  }

  /**
   * Delete method router for merging two precincts index 0 primary precinct, index 1 deleting
   * precinct front end should already merge all the data from the deleting precinct expect for the
   * adjacentPrecinctIds
   *
   * @param precincts type List<Precinct>, fetched through request body
   * @return Precinct object of survived precinct status code is set to 200 if the merging operation
   *     of two precincts is completed otherwise 400
   */
  @DeleteMapping(path = "/precinct/merge")
  private ResponseEntity<Precinct> mergePrecinctsHandler(
      @Valid @NotNull @RequestBody List<Precinct> precincts) {
    var mergingResult = precinctService.mergePrecincts(precincts);
    return new ResponseEntity<>(
        mergingResult, mergingResult == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
  }
}
