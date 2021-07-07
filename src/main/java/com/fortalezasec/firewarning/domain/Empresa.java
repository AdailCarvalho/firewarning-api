package com.fortalezasec.firewarning.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fortalezasec.firewarning.Utils.CustomValidators;

import br.com.caelum.stella.validation.CNPJValidator;

@Entity
public class Empresa implements Serializable {

  private static final long serialVersionUID = 1L;

  @Transient
  private CustomValidators validators = new CustomValidators(new CNPJValidator());

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "O CNPJ deve ser informado")
  private String cnpj;

  @NotNull(message = "O nome deve ser informado")
  @Size(min = 3, max = 40, message = "O nome deve ter no mínimo 3 e no máximo 40 caracteres")
  private String fantasia;

  @Email(message = "O email enviado está mal formado")
  private String email;

  @NotNull(message = "O contato deve ser informado")
  private String contato;

  @NotNull(message = "O nível de perigo deve ser informado")
  private NivelPerigo nivelPerigo;

  public Empresa(Long id, String cnpj, String fantasia, String email, String contato, NivelPerigo nivelPerigo) {
    this.id = id;
    setCnpj(cnpj);
    this.fantasia = fantasia;
    this.email = email;
    this.contato = contato;
    this.nivelPerigo = nivelPerigo;
  }

  public Empresa() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    validators.validarCnpj(cnpj);
    cnpj = cnpj.replaceAll("[./-]", "");
    this.cnpj = cnpj;
  }

  public String getFantasia() {
    return fantasia;
  }

  public void setFantasia(String fantasia) {
    this.fantasia = fantasia;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContato() {
    return contato;
  }

  public void setContato(String contato) {
    this.contato = contato;
  }

  public NivelPerigo getNivelPerigo() {
    return nivelPerigo;
  }

  public void setNivelPerigo(NivelPerigo nivelPerigo) {
    this.nivelPerigo = nivelPerigo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
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
    Empresa other = (Empresa) obj;
    if (cnpj == null) {
      if (other.cnpj != null)
        return false;
    } else if (!cnpj.equals(other.cnpj))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
