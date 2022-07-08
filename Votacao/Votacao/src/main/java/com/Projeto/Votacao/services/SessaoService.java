package com.Projeto.Votacao.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Projeto.Votacao.dto.SessaoIniciarDto;
import com.Projeto.Votacao.dto.SessaoRequisicaoDto;
import com.Projeto.Votacao.exception.NotFoundException;
import com.Projeto.Votacao.models.Pauta;
import com.Projeto.Votacao.models.SessaoVotacao;
import com.Projeto.Votacao.models.StatusEnum;
import com.Projeto.Votacao.models.VotoEnum;
import com.Projeto.Votacao.repositories.PautaRepository;
import com.Projeto.Votacao.repositories.SessaoVotacaoRepository;

@Service
public class SessaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@Transactional()
	public List<SessaoVotacao> getAll() {
		return sessaoVotacaoRepository.findAll();
	}

	public SessaoVotacao getById(Long id) {

		return sessaoVotacaoRepository.findById(id).orElse(null);
	}


	@Transactional
	public SessaoVotacao iniciarSessaoVotacao(SessaoIniciarDto dto) {
		SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.findById(dto.getSessaoId())
				.orElseThrow(() -> new RuntimeException("Sessao de votação não existe"));

		  if (sessaoVotacao.getPauta().getStatus().equals(StatusEnum.valueOf("Fechado"))){
	            throw new RuntimeException("Essa pauta está fechada");
	        }

		  sessaoVotacao.setFechamento(Instant.now().plus(sessaoVotacao.getDuracao(), ChronoUnit.SECONDS));

	        return sessaoVotacaoRepository.save(sessaoVotacao);
	}

	public SessaoVotacao criarSessao(SessaoRequisicaoDto dto) {
		if (verificarPauta(dto.getPautaId())) {
			throw new RuntimeException("A pauta fornecida não existe");
		}

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
				.duracao(tempoEncerramento(dto.getDuracao()))
				.pauta(getPauta(dto))
				.build();

		return sessaoVotacaoRepository.save(sessaoVotacao);
	}
	
	@Scheduled(fixedDelay = 5000)
    public void closeSession() {

        List<SessaoVotacao> sessaoVotacaoList = getAllExpiredVotingsButNotClosed();

        sessaoVotacaoList.forEach(voting -> {
            voting.getPauta().setQtdVotos(voting.getVotos().size());
            voting.getPauta().setQtdSim(qtdSim(voting));
            voting.getPauta().setQtdNao(qtdNao(voting));
            sessaoVotacaoRepository.save(voting);
            pautaService.mudarStatus(voting.getPauta());
            pautaService.setPercentual(voting.getPauta());
            pautaService.setVencedor(voting.getPauta());
            pautaRepository.save(voting.getPauta());
        });
    }
	
	 private List<SessaoVotacao> getAllExpiredVotingsButNotClosed() {

	        return sessaoVotacaoRepository.findAll().stream().filter(
	                sessaoVotacao -> sessaoVotacao.expirado() && sessaoVotacao.aberto()
	        ).collect(Collectors.toList());
	    }
	 
	 public Integer qtdSim(SessaoVotacao sessaoVotacao){

	        return Math.toIntExact(sessaoVotacao.getVotos().stream()
	                .filter(c -> c.getMensagemVoto().equals(VotoEnum.valueOf("SIM")))
	                .count());
	    }

	    public Integer qtdNao(SessaoVotacao sessaoVotacao){

	        return Math.toIntExact(sessaoVotacao.getVotos().stream()
	                .filter(c -> c.getMensagemVoto().equals(VotoEnum.valueOf("NAO")))
	                .count());
	    }


	private boolean verificarPauta(Long PautaId) {

	        return sessaoVotacaoRepository.existsByPautaId(PautaId);
	    }
	
	private Integer tempoEncerramento(Integer duracao) {

	        return Objects.isNull(duracao) ? 60 : duracao;
	    }
	 

	private Pauta getPauta(SessaoRequisicaoDto dto) {
		return pautaRepository.findById(dto.getPautaId())
				.orElseThrow(() -> new RuntimeException("Pauta não existe"));
	}
}
