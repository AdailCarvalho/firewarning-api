package com.fortalezasec.firewarning.domain;

import java.io.Serializable;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EmpresaFavorita implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cnpj;
  private String comentario;

  @ElementCollection(fetch = FetchType.LAZY)
  @OneToOne(mappedBy = "empresaFavorita")
  private Usuario usuario;

  public EmpresaFavorita(Long id, String cnpj, String comentario) {
    this.id = id;
    this.cnpj = cnpj;
    this.comentario = comentario;
  }

  public EmpresaFavorita() {
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
    this.cnpj = cnpj;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
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
    EmpresaFavorita other = (EmpresaFavorita) obj;
    if (comentario == null) {
      if (other.comentario != null)
        return false;
    } else if (!comentario.equals(other.comentario))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
