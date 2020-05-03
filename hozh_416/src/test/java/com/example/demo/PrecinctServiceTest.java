package com.example.demo;

import com.example.demo.entity.Precinct;
import com.example.demo.service.PrecinctService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

// todo test will be independent to the current database later
/*
 * @created 27/04/2020 - 3:16 PM
 * @project IntelliJ IDEA
 * @author Hong Zheng, Hyejun Jeong
 */

@ComponentScan()
@EnableJpaRepositories()
@SpringBootTest
@RunWith(SpringRunner.class)
public class PrecinctServiceTest {

  @Autowired
  PrecinctService ps;

  @Test
  public void selectPrecinctById() {
    Assert.assertThat(ps.selectPrecinctById("21-117-B123").getId(), is("21-117-B123"));
  }

  @Test
  public void updatePrecinct() {
    Precinct current = ps.selectPrecinctById("21-117-B123");
    current.setGhost(true);
    ps.updatePrecinct(current);
    Assert.assertThat(ps.selectPrecinctById("21-117-B123").isGhost(), is(true));
  }
}
