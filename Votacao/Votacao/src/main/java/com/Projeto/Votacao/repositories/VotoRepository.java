package com.Projeto.Votacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Projeto.Votacao.models.SessaoVotacao;
import com.Projeto.Votacao.models.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	Boolean existsBySessaoVotacaoAndCpfUsuario(Optional<SessaoVotacao> sessaoVotacao, String cfpUsuario);
}
