package com.example.demo.service;

import com.example.demo.entity.Precinct;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;

//todo test will be independent to the current database later
/*
 * @created 27/04/2020 - 3:16 PM
 * @project IntelliJ IDEA
 * @author Hong Zheng
 */
public class PrecinctServiceTest {

    @Autowired
    PrecinctService ps;

    @Test
    public void selectPrecinctById() {
        Assert.assertThat(ps.selectPrecinctById("pid").getId(), is("pid"));
    }

    @Test
    public void updatePrecinct() {
        Precinct current = ps.selectPrecinctById("pid");
        current.setGhost(true);
        ps.updatePrecinct(current);
        Assert.assertThat(ps.selectPrecinctById("pid").isGhost(), is(true));
    }

}