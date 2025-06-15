package com.sistematurnos.repository;

import com.sistematurnos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByDni(int dni);

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByFechaAltaBetweenAndEstado(LocalDateTime inicio, LocalDateTime fin, boolean estado);

	List<Usuario> findByFechaAltaBetween(LocalDateTime inicio, LocalDateTime fin);
<<<<<<< HEAD
=======

	boolean existsByEmail(String email);

>>>>>>> 99f4d3c (Version Funcional Spring Security)
}
