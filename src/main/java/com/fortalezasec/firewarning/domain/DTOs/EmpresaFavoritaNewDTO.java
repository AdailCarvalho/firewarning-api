package com.fortalezasec.firewarning.domain.DTOs;

import java.io.Serializable;

import com.fortalezasec.firewarning.domain.EmpresaFavorita;

public class EmpresaFavoritaNewDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String cnpj;
  private String comentario;

  public EmpresaFavoritaNewDTO(EmpresaFavorita empresaFavorita) {
    this.cnpj = empresaFavorita.getCnpj();
    this.comentario = empresaFavorita.getComentario();
  }

  public EmpresaFavoritaNewDTO() {
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
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
    result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
    result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
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
    EmpresaFavoritaNewDTO other = (EmpresaFavoritaNewDTO) obj;
    if (cnpj == null) {
      if (other.cnpj != null)
        return false;
    } else if (!cnpj.equals(other.cnpj))
      return false;
    if (comentario == null) {
      if (other.comentario != null)
        return false;
    } else if (!comentario.equals(other.comentario))
      return false;
    return true;
  }

}
