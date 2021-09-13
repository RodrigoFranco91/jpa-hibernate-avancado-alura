package br.com.alura.loja.testes;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Livro;
import br.com.alura.loja.util.JPAUtil;

public class TesteCadastroLivro {

	
	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		Livro livro = new Livro("Livro do Rodrigo", 100);
		
		em.persist(livro);
		
		em.getTransaction().commit();
		em.close();
	}
}
