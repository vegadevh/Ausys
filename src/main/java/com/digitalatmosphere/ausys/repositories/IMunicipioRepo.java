package com.digitalatmosphere.ausys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalatmosphere.ausys.domains.Municipio;

public interface IMunicipioRepo extends JpaRepository<Municipio, Integer> {

}
