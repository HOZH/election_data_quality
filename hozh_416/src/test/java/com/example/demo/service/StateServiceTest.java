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
public class StateServiceTest {

  @Autowired StateService ss;

  @Test
  public void selectStateById() {
    Assert.assertThat(ss.selectStateById("sid").getId(), is("sid"));
  }
}
