package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public BigDecimal valorTotalVendido() {
		//Podemos chamar qualquer função existente no SQL: SUM, AVG, MAX...(Padrão de todos os bancos)
		//AO tentar rodar algo específico de um banco, a JPA tenta repassar a função para o banco, se ele não conseguir dará erro!
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return this.em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	//Aqui teremos retorno de várias entidades (não é por exemplo só de Pedido)
	//Opção é retornar via Object
	public List<Object[]> relatorioDeVendasObject(){
		
		String jpql = "SELECT produto.nome, SUM(item.quantidade), MAX(pedido.data) FROM Pedido pedido JOIN pedido.itensPedidos item JOIN item.produto produto GROUP BY produto.nome ORDER BY item.quantidade DESC";
		return this.em.createQuery(jpql, Object[].class)
				.getResultList();
	}
	
	//Outra opção é retornar via Classe que criamos para representar o relatório
	//Na jpql temos que dar um new na classe
	public List<RelatorioDeVendasVo> relatorioDeVendas(){
		
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo(produto.nome, SUM(item.quantidade), MAX(pedido.data)) FROM Pedido pedido JOIN pedido.itensPedidos item JOIN item.produto produto GROUP BY produto.nome ORDER BY item.quantidade DESC";
		return this.em.createQuery(jpql, RelatorioDeVendasVo.class)
				.getResultList();
	}
	
	//Resolvendo o problema de LazyInitializationException
	//Com esse método o objeto Pedido é retornado com seu relacionamento com Cliente, graça ao JOIN FETCH
	public Pedido buscarPedidoComCLiente(Long id) {
		return this.em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
		.setParameter("id", id)
		.getSingleResult();
		
	}

}
