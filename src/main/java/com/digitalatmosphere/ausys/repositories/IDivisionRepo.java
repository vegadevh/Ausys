package com.digitalatmosphere.ausys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalatmosphere.ausys.domains.Division;

public interface IDivisionRepo extends JpaRepository<Division, Integer> {
	
}
