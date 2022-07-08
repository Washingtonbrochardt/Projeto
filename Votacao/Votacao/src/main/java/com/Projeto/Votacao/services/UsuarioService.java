package com.Projeto.Votacao.services;



import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Projeto.Votacao.models.Usuario;
import com.Projeto.Votacao.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService {
	
	@Autowired
	CpfService cpfService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	 public Usuario getUsuario(String nome) {

	        return usuarioRepository.findByNome(nome).orElse(null);
	    }
	 
	 public Usuario getUsuarioById(Long id){

	        return usuarioRepository.findById(id).orElse(null);
	    }


	public Optional<Usuario> criarUsuario (Usuario usuario) {
		if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
			log.error("CPF ja cadastrado {}", usuario.getCpf());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CPF já cadastrado",null);
		}
		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			log.error("Email ja cadastrado {}", usuario.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"EMAIL já cadastrado",null);
		}
		if (!cpfService.validarCpf(usuario.getCpf())) {
			log.error("CPF invalido {}", usuario.getCpf());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CPF não é válido",null);
		}
		
		usuario.setSenha(encriptyPassword(usuario.getSenha()));
		
		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(@Valid Usuario usuario) {
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {		
			Optional<Usuario> findUsuario = usuarioRepository.findByEmail(usuario.getEmail());
			
			if (findUsuario.isPresent()) {
				if (findUsuario.get().getId()!= usuario.getId()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario Existente",null);
				}
			}
			if (!cpfService.validarCpf(usuario.getCpf())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CPF não é válido",null);			
			}
			return Optional.of(usuarioRepository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario não encontrado",null);
	}
	
	public String encriptyPassword (String password) {
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		String passwordEncoder = enconder.encode(password);
		return passwordEncoder;
	}

	public void deleteUsuario(Long id) {
		
		Optional<Usuario> deletarUsuario = usuarioRepository.findById(id);
		
		if (deletarUsuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		usuarioRepository.deleteById(id);
	
		
	}
	
	 private String encryptPassword(String senha){
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        return encoder.encode(senha);
	    }


	
	
}
