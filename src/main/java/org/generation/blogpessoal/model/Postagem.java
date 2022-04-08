package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // Transforma em tabela
@Table(name="tb_postagens") // Nomeia a tabela
public class Postagem {
	
	@Id // Define chave primaria
	@GeneratedValue(strategy= GenerationType.IDENTITY) // auto_increment
	private Long id;
	
	@NotNull 
	private String titulo;
	
	@Size(max=1000) //Define o tamanho min e/ou max que o usuario pode inserir
	private String texto;
	
	@UpdateTimestamp // Padroniza a data e hora na versão configurada do computador, versao br por ex
	private LocalDateTime date; // Pega a data e hora no padrao americano
	
	@ManyToOne // criar relacionammento de tabelas
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	// getters e setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
