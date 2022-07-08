package com.Projeto.Votacao.dto;

import com.Projeto.Votacao.models.Tipo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {

	private String nome;
	private String cpf;
	private Tipo tipo;
	private String email;
	private String token;
}