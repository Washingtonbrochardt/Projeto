package com.Projeto.Votacao.dto;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;


import com.Projeto.Votacao.models.VotoEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class VotoRequisicaoDto {
	
	private Long sessaoVotoId;

    @NotNull(message = "CPF do Usuario obrigatório.")
    private String cpfUsuario;

    @Enumerated
    @NotNull(message = "Mensagem de voto é obrigatório e precisa seguir o padrão: SIM/NAO")
    private VotoEnum mensagemVoto;
}
