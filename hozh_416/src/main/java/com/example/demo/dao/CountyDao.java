package com.example.demo.dao;


import com.example.demo.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Repository
public interface CountyDao extends JpaRepository<County, String> {
}
