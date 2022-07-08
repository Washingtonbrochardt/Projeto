package com.Projeto.Votacao.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto.Votacao.dto.SessaoIniciarDto;
import com.Projeto.Votacao.dto.SessaoRequisicaoDto;
import com.Projeto.Votacao.models.SessaoVotacao;
import com.Projeto.Votacao.repositories.SessaoVotacaoRepository;
import com.Projeto.Votacao.services.PautaService;
import com.Projeto.Votacao.services.SessaoService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/sessao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@Data
public class SessaoController {

	@Autowired
	private PautaService pautaService;

	@Autowired
	private SessaoService sessaoService;

	@GetMapping("/{id}")
	public ResponseEntity<SessaoVotacao> getById(@PathVariable Long id) {
		return new ResponseEntity<>(sessaoService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<SessaoVotacao>> getAll() {

		return new ResponseEntity<>(sessaoService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/criar")
	    public ResponseEntity<SessaoVotacao> createSession(@Valid @RequestBody SessaoRequisicaoDto dto){
			log.info("Criando a sessão de votação...", dto.getPautaId());
	        return new ResponseEntity<>(sessaoService.criarSessao(dto), HttpStatus.CREATED);
	    }

	@PostMapping("/iniciar")
	public ResponseEntity<SessaoVotacao> iniciarSessaoVotacao(SessaoIniciarDto dto) {
		log.info("Iniciando sessão de votação...", dto.getSessaoId());
		
		

		return new ResponseEntity<>(sessaoService.iniciarSessaoVotacao(dto), HttpStatus.OK);
	}
}
