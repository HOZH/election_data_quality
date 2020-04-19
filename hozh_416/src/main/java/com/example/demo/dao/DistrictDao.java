package com.example.demo.dao;

import com.example.demo.model.District;
import com.example.demo.model.Precinct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictDao extends JpaRepository<District, Long> {
}