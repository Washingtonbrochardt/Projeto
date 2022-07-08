package com.Projeto.Votacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Projeto.Votacao.models.Pauta;
import com.Projeto.Votacao.models.SessaoVotacao;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

	Optional<SessaoVotacao> findByPauta(Pauta pauta);

	boolean existsByPautaId(Long pautaId);

}
