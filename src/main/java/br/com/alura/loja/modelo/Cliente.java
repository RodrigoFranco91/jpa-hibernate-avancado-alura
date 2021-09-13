package br.com.alura.loja.modelo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Os atributos da classe do atributo serão embutidos aqui (nesta classe)
	@Embedded
	private DadosPessoais dadosPessoais;

	
	//Somente para Hibernate
	@Deprecated
	public Cliente() {

	}
	
	public Cliente(String nome, String cpf) {
		this.dadosPessoais = new DadosPessoais(nome,cpf);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}
	
	public String getNome() {
		return dadosPessoais.getNome();
	}
	
	public String getCpf() {
		return dadosPessoais.getCpf();
	}

}
