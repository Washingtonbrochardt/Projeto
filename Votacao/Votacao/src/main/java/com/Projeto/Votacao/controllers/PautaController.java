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

import com.Projeto.Votacao.dto.PautaDto;
import com.Projeto.Votacao.models.Pauta;
import com.Projeto.Votacao.services.PautaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/pauta")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PautaController {

	@Autowired
	private PautaService pautaService;
	
	 @GetMapping("/all")
	    public ResponseEntity<List<Pauta>> getAll(){

	        return ResponseEntity.ok(pautaService.getAll());
	    }

	 @GetMapping(value = "/{id}")
	    public ResponseEntity<PautaDto> getById(@PathVariable("id") Long id) {
	        log.debug("Buscando a pauta pelo ID = {}", id);
	        return ResponseEntity.ok(pautaService.getById(id));
	    }
	 
	 @GetMapping
	    public ResponseEntity<Pauta> getByNome(String nome){

	        return new ResponseEntity<>(pautaService.getByNome(nome), HttpStatus.OK);
	    }
	 
	 
	@PostMapping("/criar")
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody Pauta pauta) {
        log.debug("Salvando a pauta  = {}", pauta.getDescricao());
        return new ResponseEntity<> (pautaService.criarPauta(pauta),
        		HttpStatus.CREATED);
    }
	
	  
	
}
