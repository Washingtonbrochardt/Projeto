package com.Projeto.Votacao.controllers;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.Projeto.Votacao.models.Usuario;
import com.Projeto.Votacao.repositories.UsuarioRepository;
import com.Projeto.Votacao.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<Usuario> getUsuario(@RequestParam String nome) {
		
		return new ResponseEntity<>(usuarioService.getUsuario(nome), HttpStatus.OK);
	}
	
	@PostMapping("/criar")
	@Transactional
	public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioService.criarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.build());		
	}
	
	@PutMapping("/atualizar")
	@Transactional
	public ResponseEntity<Usuario> atualizarUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioService.atualizarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.build());		
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void deletarUsuario(@PathVariable Long id) {
		
		 usuarioService.deleteUsuario(id);
	}
}