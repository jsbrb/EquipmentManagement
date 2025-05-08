package com.example.equipmentmanagement.repository;

import com.example.equipmentmanagement.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository  extends JpaRepository<Work, Long> {
}
