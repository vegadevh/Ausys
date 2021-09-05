package com.digitalatmosphere.ausys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalatmosphere.ausys.domains.Departamento;

public interface IDepartamentoRepo extends JpaRepository<Departamento, Integer> {

}
