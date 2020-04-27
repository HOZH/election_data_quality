package com.example.demo.entitymanager;

import com.example.demo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Hong Zheng
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */
@Repository
public interface StateEntityManager extends JpaRepository<State, String> {
}