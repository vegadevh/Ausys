package com.digitalatmosphere.ausys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalatmosphere.ausys.domains.Usuario;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

}
