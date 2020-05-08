package com.example.demo.entitymanager;

import com.example.demo.entity.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Hong Zheng, Hyejun Jeong
 * @created 19/03/2020 - 4:14 PM
 * @project hozh-416-server
 */

@Repository("pem")
public interface PrecinctEntityManager extends JpaRepository<Precinct, String> {
}
