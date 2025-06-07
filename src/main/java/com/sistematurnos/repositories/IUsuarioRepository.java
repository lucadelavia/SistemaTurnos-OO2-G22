package com.sistematurnos.repositories;

import java.io.Serializable;
import com.sistematurnos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable> { 
	
	Optional<Usuario> findById(int id);
	
	Optional<Usuario> findByDni(int dni); 
	
	Optional<Usuario> findByEmail(String email);
}