package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository  extends JpaRepository<Operator, Long> {
}
