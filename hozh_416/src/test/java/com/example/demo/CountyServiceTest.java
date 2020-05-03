package com.example.demo;

import com.example.demo.service.CountyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

/*
 * @created 27/04/2020 - 3:15 PM
 * @project IntelliJ IDEA
 * @author Hong Zheng, Hyejun Jeong
 */


@ComponentScan()
@EnableJpaRepositories()
@SpringBootTest
@RunWith(SpringRunner.class)
public class CountyServiceTest {

  @Autowired
  CountyService cs;

  @Test
  public void selectCountyById() {
    Assert.assertThat(cs.selectCountyById("cid").getId(), is("cid"));
  }
}
