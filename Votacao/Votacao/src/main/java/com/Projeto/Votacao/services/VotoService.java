package com.Projeto.Votacao.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Projeto.Votacao.dto.VotoRequisicaoDto;
import com.Projeto.Votacao.exception.NotFoundException;
import com.Projeto.Votacao.models.Pauta;
import com.Projeto.Votacao.models.SessaoVotacao;
import com.Projeto.Votacao.models.Voto;
import com.Projeto.Votacao.repositories.PautaRepository;
import com.Projeto.Votacao.repositories.SessaoVotacaoRepository;
import com.Projeto.Votacao.repositories.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
 	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	
    public List<Voto> findAllVotacoes() {
        return votoRepository.findAll();
    }
	
    public Optional<Pauta> getPauta(Long id) {
    	return pautaRepository.findById(id);
    }
    
    private Optional<SessaoVotacao> getSessaoVotacao(Pauta pauta) {
    	return sessaoVotacaoRepository.findByPauta(pauta);
    }
    
    @Transactional
    public Voto votar( VotoRequisicaoDto dto) {
    	 Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(dto.getSessaoVotoId());

         if (sessaoVotacao.isEmpty()) {
             throw new RuntimeException("Essa Sessão de Votação não existe");
         }
        
        Voto voto = Voto.builder()
        		.cpfUsuario(dto.getCpfUsuario())
        		.mensagemVoto(dto.getMensagemVoto())
        		.dataVoto(Instant.now())
        		.build();
       
        if(votoRepository.existsBySessaoVotacaoAndCpfUsuario(sessaoVotacao, voto.getCpfUsuario())) {
            throw new NotFoundException("VOTO_JA_REGISTRADO");
        }
        
        verificacaoDeTempoSessãoVotação(voto);
        
        return votoRepository.save(voto);
    }
    
    private void verificacaoDeTempoSessãoVotação(Voto voto) {
        if (voto.getDataVoto().isAfter(voto.getSessaoVotacao().getFechamento())) {
            throw new RuntimeException("Sessão de votação expirada");
        }
    }
}
