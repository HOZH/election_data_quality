package com.example.demo;

import com.example.demo.entity.Precinct;
import com.example.demo.entitymanager.PrecinctEntityManager;
import com.example.demo.service.CountyService;
import com.example.demo.service.PrecinctService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

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
  @Autowired
  CountyService cs;

  @PersistenceContext
  public EntityManager em;

//  @Test
//  public void selectPrecinctById() {
//
//    var queryResult = ps.selectPrecinctById("21-117-B123").getId();
//    System.out.println("queryResult = " + queryResult);
//
//    List l = em.createQuery(
//            "select p from precinct p where p.id LIKE :pid")
//            .setParameter("pid", queryResult)
//            .getResultList();
//    for(Object p:l){
//      System.out.println(p);
//    }
//  }

  @Test
  public void updatePrecinct() {
    String pid = "21-117-B123";
    Precinct current = ps.selectPrecinctById(pid);
    current.setGhost(true);
    ps.updatePrecinct(current);
    Assert.assertThat(ps.selectPrecinctById(pid).isGhost(), is(true));
  }
}
