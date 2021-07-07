package com.fortalezasec.firewarning.domain.DTOs;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;

public class UsuarioNewDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String nome;
  private String email;

  @JsonIgnore
  private String senha;
  private Set<Tipo> tipo;
  private EmpresaFavoritaNewDTO empresaFavorita;

  public UsuarioNewDTO() {
  }

  public UsuarioNewDTO(Usuario usuario) {
    id = usuario.getId();
    nome = usuario.getNome();
    email = usuario.getEmail();
    senha = usuario.getSenha();
    tipo = usuario.getPerfis();
  }

  public UsuarioNewDTO(String nome, String email, String senha, Set<Tipo> tipo) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.tipo = tipo;
  }

  public UsuarioNewDTO(String nome, String email, String senha, Set<Tipo> tipo, EmpresaFavoritaNewDTO empresaFavorita) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.tipo = tipo;
    this.empresaFavorita = empresaFavorita;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Tipo> getTipo() {
    return tipo;
  }

  public void setTipo(Set<Tipo> tipo) {
    this.tipo = tipo;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public EmpresaFavoritaNewDTO getEmpresaFavorita() {
    return empresaFavorita;
  }

  public void setEmpresaFavorita(EmpresaFavoritaNewDTO empresaFavorita) {
    this.empresaFavorita = empresaFavorita;
  }

}
