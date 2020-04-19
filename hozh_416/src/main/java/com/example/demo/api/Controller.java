package com.example.demo.api;

import com.example.demo.model.Precinct;
import com.example.demo.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("api")
@RestController
public class Controller {

    //fixme optimized code after I finished geojson side
    // todo completement merge precincts later

    private final PrecinctService precinctService;


    @Autowired
    public Controller(PrecinctService precinctService) {
        this.precinctService = precinctService;
    }



    @GetMapping(path = "/getAllPrecincts")
    public List<Precinct> getAllPrecinct() {


        return precinctService.selectAllPrecincts();
    }

    @GetMapping(path = "/state/{id}")
    public List<Precinct> selectStateById(@PathVariable("id") Long id) {


        //todo next to implement once the real database is set up, for now we don't have enough realistic geo data from db
        return null;
    }


    @GetMapping(path = "/{id}")
    public Precinct getPrecinctById(@PathVariable("id") Long id) {

        return precinctService.selectPrecinctById(id);


    }

    @DeleteMapping(path = "/{id}")
    public String deletePrecinctById(@PathVariable("id") Long id) {

        precinctService.deletePrecinctById(id);

        return "OK";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Precinct savePrecinct(@Valid @NotNull @RequestBody Precinct precinct) {

        return precinctService.savePrecinct(precinct);

    }

    // index 0 merged precinct, index 1 deleting precinct
    // front end already merge all the data from the deleting precinct expect for the adjacentPrecinct
    @DeleteMapping(path = "/merge")
    public Precinct mergePrecincts(@Valid @NotNull @RequestBody List<Precinct> precincts) {



        return this.precinctService.mergePrecincts(precincts);

    }




}