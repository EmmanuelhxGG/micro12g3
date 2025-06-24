package com.micro12.micro12g3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro12.micro12g3.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
}
