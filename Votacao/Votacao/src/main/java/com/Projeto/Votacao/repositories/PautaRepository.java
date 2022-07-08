package com.Projeto.Votacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Projeto.Votacao.models.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

	Optional<Pauta> findById(Long id);

	Optional<Pauta> findByNome(String nome);
}
