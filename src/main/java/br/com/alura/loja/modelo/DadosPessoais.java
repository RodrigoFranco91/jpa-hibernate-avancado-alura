package br.com.alura.loja.modelo;

import javax.persistence.Embeddable;

//Indica que essa classe é embutivel, ou seja, seus atributos vão ser colunas de outra classe/tabela
@Embeddable
public class DadosPessoais {

	private String nome;
	private String cpf;
	
	//Para a JPA
	@Deprecated
	public DadosPessoais() {

		
	}
	
	public DadosPessoais(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
		
	}
	
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	
}
