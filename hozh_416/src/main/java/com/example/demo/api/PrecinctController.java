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
public class PrecinctController {

    //fixme optimized code after I finished geojson side
    // todo completement merge precincts later

    private final PrecinctService precinctService;


    @Autowired
    public PrecinctController(PrecinctService precinctService) {
        this.precinctService = precinctService;
    }


    @PutMapping(path = "/neighbor")
    public Precinct updateNeighborPrecinct(@Valid @NotNull @RequestBody Precinct precinct) {

        return precinctService.updateNeighbors(precinct);

    }

    @GetMapping(path = "/getAllPrecincts")
    public List<Precinct> getAllPrecinct() {


        return precinctService.selectAllPrecincts();
    }

    //
//
    @GetMapping(path = "/{id}")
    public Precinct getPrecinctById(@PathVariable("id") Long id) {

        return precinctService.selectPrecinctById(id);


    }

    //
    @DeleteMapping(path = "/{id}")
    public String deletePrecinctById(@PathVariable("id") Long id) {

        precinctService.deletePrecinctById(id);
        return "OK";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Precinct savePrecinct(@Valid @NotNull @RequestBody Precinct precinct) {

        return precinctService.savePrecinct(precinct);

    }
//
//
//    @DeleteMapping(path = "/merge/{id1}/{id2}")
//    public Precinct mergePrecincts(@PathVariable("id1") UUID id1, @PathVariable("id2") UUID id2) {
//
//        return this.precinctService.mergePrecincts(id1, id2);
//
//    }


}