package com.Projeto.Votacao.controllers;

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

import com.Projeto.Votacao.dto.VotoRequisicaoDto;
import com.Projeto.Votacao.models.Voto;
import com.Projeto.Votacao.services.VotoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/voto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class VotoController {

	@Autowired
	private VotoService votoService;
	
	@GetMapping("/{id}")
    public List<Voto> listVotacoes() {
        return votoService.findAllVotacoes();
    }
	
    @PostMapping("/votar")
    public ResponseEntity<Voto> votar( @RequestBody @Valid VotoRequisicaoDto voto) {
        log.info("Voto registrado com sucesso!");
       
       
        return new ResponseEntity<>(votoService.votar(voto), HttpStatus.CREATED);
    }
	
}
