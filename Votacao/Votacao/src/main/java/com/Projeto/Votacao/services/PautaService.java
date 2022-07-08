package com.Projeto.Votacao.services;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Projeto.Votacao.dto.PautaDto;
import com.Projeto.Votacao.exception.NotFoundException;
import com.Projeto.Votacao.models.Pauta;
import com.Projeto.Votacao.models.StatusEnum;
import com.Projeto.Votacao.models.VotoEnum;
import com.Projeto.Votacao.repositories.PautaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	
	public List<Pauta> getAll(){

        return pautaRepository.findAll();
    }
	
    @Transactional
    public PautaDto getById(Long id) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(id);

        if (!pautaOptional.isPresent()) {
            log.error("Pauta não localizada para id {}", id);
            throw new NotFoundException("Pauta não localizada para o id " + id);
        }

        return PautaDto.toDTO(pautaOptional.get());
    }
    
    public Pauta getByNome(String nome){

        return pautaRepository.findByNome(nome).orElse(null);
    }

    @Transactional
    public Pauta criarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }
    
    public void delete(Long id){

        pautaRepository.deleteById(id);
    }
    
    public void mudarStatus(Pauta pauta){

        pauta.setStatus(StatusEnum.Fechado);

        pautaRepository.save(pauta);
    }
    
    public void setVencedor(Pauta pauta){

        if (pauta.getSimPercentual() > pauta.getNaoPercentual()){

            pauta.setVencedor(VotoEnum.SIM);
        }else {

            pauta.setVencedor(VotoEnum.NAO);
        }
    }

    public void setPercentual(Pauta pauta){

    	pauta.setSimPercentual(Precision.round(((
                Double.valueOf(pauta.getQtdSim())/ pauta.getQtdVotos())*100), 2));
    	pauta.setNaoPercentual(Precision.round(((
                Double.valueOf(pauta.getQtdNao())/ pauta.getQtdVotos())*100), 2));
    }
    
}
