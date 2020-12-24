package com.fortalezasec.firewarning.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "O nome deve ser informado")
  @Size(min = 3, max = 40, message = "O nome deve ter no mínimo 3 e no máximo 40 caracteres")
  private String nome;

  @NotNull(message = "Um Email deve ser informado")
  @Email(message = "O email enviado está mal formado")
  private String email;

  @NotNull(message = "Uma senha deve ser informada")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String senha;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERFIS")
  private Set<Tipo> perfis = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinTable(name = "usuario_empresa_favorita", 
    joinColumns = { @JoinColumn(name = "usuario_id", referencedColumnName = "id") },
    inverseJoinColumns = { @JoinColumn(name = "empresa_favorita_id", referencedColumnName = "id") }
  )
  private EmpresaFavorita empresaFavorita;

  public Usuario(Long id, String nome, String email, String senha, Set<Tipo> perfis, EmpresaFavorita empresaFavorita) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.empresaFavorita = empresaFavorita;
    setPerfis(perfis);
  }

  public Usuario() {
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

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Set<Tipo> getPerfis() {
    return perfis;
  }

  public void setPerfis(Set<Tipo> perfis) {
    this.perfis = perfis;
  }

  public EmpresaFavorita getEmpresaFavorita() {
    return empresaFavorita;
  }

  public void setEmpresaFavorita(EmpresaFavorita empresaFavorita) {
    this.empresaFavorita = empresaFavorita;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Usuario other = (Usuario) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
