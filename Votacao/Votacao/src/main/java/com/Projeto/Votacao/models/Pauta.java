package com.Projeto.Votacao.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pauta")
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "Nome")
	@Size(min = 3)
	private String nome;
	
	@Column(name = "Descricao")
	@Size(min = 3)
	private String descricao;
	
	@Column(name = "status")
    @Enumerated
    private StatusEnum status;

    @Column(name = "vencedor")
    private VotoEnum vencedor;

    @Column(name = "qtdvotos")
    private Integer qtdVotos = 0;

    @Column(name = "qtdsim")
    private Integer qtdSim = 0;

    @Column(name = "qtdnao")
    private Integer qtdNao = 0;

    @Column(name = "simPercentual")
    private Double simPercentual = 0.00;

    @Column(name = "naoPercentual")
    private Double naoPercentual = 0.00;
	
}
