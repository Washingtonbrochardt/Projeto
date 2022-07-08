package com.Projeto.Votacao.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "tb_sessaoVotacao")
public class SessaoVotacao {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "duração")
	private Integer duracao;

	@Column(name = "fechamento")
	private Instant fechamento;

	@OneToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "votingSession", cascade = CascadeType.ALL)
	private List<Voto> votos = new ArrayList<>();

	public boolean expirado() {
		
		return this.getFechamento() != null && this.getFechamento().isBefore(Instant.now());
	}

	public boolean aberto() {
		
		return getPauta().getStatus().equals(StatusEnum.valueOf("Aberto"));
	}

}
