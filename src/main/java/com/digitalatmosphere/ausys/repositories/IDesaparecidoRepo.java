package com.digitalatmosphere.ausys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalatmosphere.ausys.domains.Desaparecido;

public interface IDesaparecidoRepo extends JpaRepository<Desaparecido, String> {
	
}
