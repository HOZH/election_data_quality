package com.example.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;

/*
 * @created 27/04/2020 - 3:15 PM
 * @project IntelliJ IDEA
 * @author Hong Zheng
 */
public class CountyServiceTest {

    @Autowired
    CountyService cs;

    @Test
    public void selectCountyById() {
        Assert.assertThat(cs.selectCountyById("cid").getId(), is("cid"));
    }

}