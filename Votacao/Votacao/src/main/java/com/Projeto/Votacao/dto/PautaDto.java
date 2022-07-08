package com.Projeto.Votacao.dto;

import com.Projeto.Votacao.models.Pauta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {

	private Long id;
	private String nome;
	private String descricao;
	
	public static PautaDto toDTO(Pauta pauta) {
		return PautaDto.builder()
				.id(pauta.getId())
				.nome(pauta.getNome())
				.descricao(pauta.getDescricao())
				.build();
	}
	
}
