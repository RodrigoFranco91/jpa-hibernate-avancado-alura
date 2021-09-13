package br.com.alura.loja.modelo;

import javax.persistence.Entity;

@Entity
public class Livro extends Produto {

	private String autor;
	private Integer numeroDePagina;
	
	public Livro() {
	}
	
	public Livro(String autor, Integer numeroDePagina) {
		this.autor = autor;
		this.numeroDePagina = numeroDePagina;
	}

	public String getAutor() {
		return autor;
	}

	public Integer getNumeroDePagina() {
		return numeroDePagina;
	}
	
}
