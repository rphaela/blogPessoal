package org.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario(0L, "Nina Assis", "ninasc@gmail.com", "test1234", "nina.jpeg" ));
		usuarioRepository.save(new Usuario(0L, "Erica Deprete", "edeprete@gmail.com", "1234test", "erica.jpeg" ));
		usuarioRepository.save(new Usuario(0L, "Aninha Borges", "aninhaestrela@gmail.com", "mariAna1903", "ana.jpeg" ));
		usuarioRepository.save(new Usuario(0L, "Débora Assis", "debdebcor@gmail.com", "senha53nh4", "deb.jpeg" ));
	}
	
	@Test
	@DisplayName("Retorna apenas um usuário")
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("aninhaestrela@gmail.com");
			assertTrue(usuario.get().getUsuario().equals("aninhaestrela@gmail.com"));
	}
	
	@Test
	@DisplayName("Retorna dois usuários")
	public void deveRetornarDoisUsuarios () {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Assis");
		assertEquals(2, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Nina Assis"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Débora Assis"));
	}
	

}
