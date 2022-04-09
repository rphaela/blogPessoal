package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();
		
	}

	
	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuário")
	public void deveCadastrarUmUsuario() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,"Jose","jose@imperiobronze.com","trabalholindo","https://i.imgur.com/FETvs2O.jpg"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	
	}
	
	@Test
	@Order(2)
	@DisplayName("Não permitir duplicação de usuário")
	public void naoPermitirDuplicarUser() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Mariazinha Inha", "marimaria@email.com", "mariamaria", "foto.png"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Mariazinha Inha", "marimaria@email.com", "mariamaria", "foto.png"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
	
	@Test
	@Order(3)
	@DisplayName("Atualizar usuário")
	public void atualizarUsuario() {
		
		Optional<Usuario> usarioCreate 	= usuarioService.cadastrarUsuario(new Usuario(0L, "Belinha Silva", "bela@email.com", "bela4567", "foto.png"));
		
		Usuario usuarioUpdate = new Usuario(usarioCreate.get().getId(), "Belinha Silva", "bela@email.com", "bela4567", "foto.png");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os usuários")
	public void listarTodosUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Mia Colucci", "miaHtinhas2@email.com", "miguels2", "dificil_ser_eu.jpeg"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Diego B.", "bonequinhodeplastico@email.com", "voucontarpromeupai", "roberta_esta_louca.jpeg"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
	
}
