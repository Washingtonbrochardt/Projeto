package com.Projeto.Votacao.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public enum Tipo {

	ADMIN("Usuario tipo Administrador"), 
	COPERADO("Usuario tipo Coperado");
	
	private String label;

	private Tipo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	

}
