package com.Projeto.Votacao.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Projeto.Votacao.models.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByCpf(@NotNull(message = "Insira somente os numeros") String cpf);

	Optional<Usuario> findByEmail(@NotNull(message = "Insira email valido") String email);

	Optional<Usuario> findByNome(String name);

}
