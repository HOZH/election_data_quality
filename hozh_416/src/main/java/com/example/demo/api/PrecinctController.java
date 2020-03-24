package com.example.demo.api;

import com.example.demo.model.Precinct;
import com.example.demo.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api")
@RestController
public class PrecinctController {

    private final PrecinctService precinctService;


    @Autowired
    public PrecinctController(PrecinctService precinctService) {
        this.precinctService = precinctService;
    }


    @PostMapping(path = "/comment/{id}")
    public String addCommentById(@PathVariable("id") UUID id, String comment) {


        this.precinctService.addComment(id, comment);

        return "OK";


    }

    @DeleteMapping(path = "/comment/{id}/{cId}")
    public String removeCommentById(@PathVariable("id") UUID id, @PathVariable("cId") UUID cId) {

        this.precinctService.removeComment(id, cId);

        return "OK";
    }


    @PostMapping(path = "/neighbor/{id}/{neighborId}")
    public String addNeighborPrecinct(@PathVariable("id") UUID id, @PathVariable("neighborId") UUID neighborId) {

        this.precinctService.addNeighboringPrecincts(id, neighborId);

        return "OK";
    }

    @DeleteMapping(path = "/neighbor/{id}/{neighborId}")
    public String removeNeighborPrecinct(@PathVariable("id") UUID id, @PathVariable("neighborId") UUID neighborId) {

        this.precinctService.removeNeighboringPrecincts(id, neighborId);

        return "OK";
    }


    @GetMapping(path = "/getAllPrecincts")
    public List<Precinct> getAllPrecinct() {
        return precinctService.selectAllPrecincts();
    }


    @GetMapping(path = "/{id}")
    public Precinct getPrecinctById(@PathVariable("id") UUID id) {

        return precinctService.selectPrecinctById(id);

    }

    @DeleteMapping(path = "/{id}")
    public String deletePrecinctById(@PathVariable("id") UUID id) {

        precinctService.deletePrecinctById(id);
        return "OK";
    }


    @PutMapping(path = "/{id}")
    public String updatePrecinct(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Precinct precinct) {


        precinctService.updatePrecinctById(id, precinct);
        return "OK";


    }

    @PostMapping(path = "/{id}")
    public String addPrecinctById(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Precinct precinct) {


        precinctService.createPrecinctById(id, precinct);

        return "OK";
    }

    @PostMapping(path = "/add")
    public UUID addPrecinct(@Valid @NotNull @RequestBody Precinct precinct) {


        return precinctService.createPrecinct(precinct);

    }


    @DeleteMapping(path = "/merge/{id1}/{id2}")
    public Precinct mergePrecincts(@PathVariable("id1") UUID id1, @PathVariable("id2") UUID id2) {

        return this.precinctService.mergePrecincts(id1, id2);

    }


}