package br.com.alura.loja.testes;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.CategoriaId;
import br.com.alura.loja.util.JPAUtil;

public class TesteBuscaChaveComposta {

	public static void main(String[] args) {
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		CategoriaId categoriaID = new CategoriaId("CELULARES", "xpto");
		Categoria categoria = em.find(Categoria.class, categoriaID);
		
		em.close();
	}
	
}
