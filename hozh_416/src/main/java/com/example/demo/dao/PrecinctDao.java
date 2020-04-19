package com.example.demo.dao;

/*
 * @created 19/03/2020 - 4:14 PM
 * @project  hozh-416-server
 * @author Hong Zheng
 */

import com.example.demo.model.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecinctDao extends JpaRepository<Precinct, Long> {


}
