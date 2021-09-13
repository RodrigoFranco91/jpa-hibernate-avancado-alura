package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}

	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Produto.class).getResultList();
	}
	
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoriaNamedQuery(String nome) {
		return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
				//Setando a variável lá da NamedQuery
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)
				.getSingleResult();
	}
	
	//Busca mais flexivel, pode ser realizada por diversos parâmetros (todos são opcionais).
	//Essa primeira soluçao é no modo dificil, com gambiarra!!!
	//A gambiarra 1=1 é para deixar o WHERE na primeira linha e sempre trazer todos itens!
	public List<Produto> buscarPorParametrosModoDificil(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = "SELECT p FROM Produto p WHERE 1=1";
		if(nome != null && !nome.trim().isEmpty()) {
			jpql = " AND p.nome = :nome";
		}
		if(preco != null) {
			jpql = " AND p.preco = :preco";
		}
		if(dataCadastro != null) {
			jpql = " AND p.dataCadastro = :dataCadastro";
		}
		
		//Para preparar a query vou ter que fazer vários if's e por isso
		// devo criar uma váriavel para guardar a query!
		TypedQuery<Produto> query = this.em.createQuery(jpql, Produto.class);
		
		if(nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if(preco != null) {
			query.setParameter("preco", preco);
		}
		if(dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}

		return query.getResultList();
	}
	
	//Busca mais flexivel, pode ser realizada por diversos parâmetros (todos são opcionais).
	//Vamos usar o CRITERIA
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		
		//Indica de onde será o FROM da query "JPQL"
		//Por padrão o SELECT é igual o FROM, se fosse diferente poderiamos mudar com query.select()
		Root<Produto> from = query.from(Produto.class);
		
		//Responsável por criar e juntar os filtros da query "JPQL"
		Predicate filtros = builder.and();
		if(nome != null && !nome.trim().isEmpty()) {
			//Passo o valor atual do filtro (primeiro argumento)
			//Depois passo o que eu quero adicionar ao filtro que estou construindo, por ser = uso o metodo equal()
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if(preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if(dataCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		//Com os filtros criado, agora falta juntar a parte do SELECT com o FILTRO, e quem faz isso é o WHERE
		query.where(filtros);
		
		//Executando a QUERY
		return em.createQuery(query).getResultList();
	}
}
