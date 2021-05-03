package com.igor.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.igor.cursomc.domain.Categoria;
import com.igor.cursomc.domain.Cidade;
import com.igor.cursomc.domain.Cliente;
import com.igor.cursomc.domain.Endereco;
import com.igor.cursomc.domain.Estado;
import com.igor.cursomc.domain.Pagamento;
import com.igor.cursomc.domain.PagamentoComBoleto;
import com.igor.cursomc.domain.PagamentoComCartao;
import com.igor.cursomc.domain.Pedido;
import com.igor.cursomc.domain.Produto;
import com.igor.cursomc.domain.enums.EstadoPagamento;
import com.igor.cursomc.domain.enums.TipoCliente;
import com.igor.cursomc.repositories.CategoriaRepository;
import com.igor.cursomc.repositories.CidadeRepository;
import com.igor.cursomc.repositories.ClienteRepository;
import com.igor.cursomc.repositories.EnderecoRepository;
import com.igor.cursomc.repositories.EstadoRepository;
import com.igor.cursomc.repositories.PagamentoRepository;
import com.igor.cursomc.repositories.PedidoRepository;
import com.igor.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	public CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(0, "Informatica");
		Categoria cat2 = new Categoria(0, "Escritorio");

		Produto p1 = new Produto(0, "Computador", 2000.00);
		Produto p2 = new Produto(0, "Impressora", 800.00);
		Produto p3 = new Produto(0, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		  Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3637891277",
		  TipoCliente.PESSOAFISICA);
		  cli1.getTelefones().addAll(Arrays.asList("812376129","128923662"));
		  
		  Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim",
		  "38220834", cli1, c1);
		  
		  Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro",
		  "38777012", cli1, c2);
		  
		  cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		  
		  clienteRepository.saveAll(Arrays.asList(cli1));
		  enderecoRepository.saveAll(Arrays.asList(e1, e2));
		 
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		  
		  Pedido ped1 = new Pedido(null, sdf.parse("03/05/2021 10:28"), cli1, e1 );
		  Pedido ped2 = new Pedido(null, sdf.parse("01/05/2021 15:40"), cli1, e2 );
		  
		  Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		  ped1.setPagamento(pagto1);
		  
		  Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/05/2021 00:00"), null);
		  ped2.setPagamento(pagto2);
		  
		  cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		  
		  pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		  pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}

}

