package br.com.alura.loja.testes;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.util.JPAUtil;

public class TesteCadastroClienteEmbedded {
	
	public static void main(String[] args) {
		
		
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		Cliente cliente = new Cliente("Felipe", "000000000");
		
		em.persist(cliente);
		
		em.getTransaction().commit();
		em.close();
	}

}
