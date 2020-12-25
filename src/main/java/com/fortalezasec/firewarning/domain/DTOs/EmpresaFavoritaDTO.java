package com.fortalezasec.firewarning.domain.DTOs;

import java.io.Serializable;

import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.NivelPerigo;

public class EmpresaFavoritaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String cnpj;
  private String fantasia;
  private NivelPerigo nivelPerigo;
  private String comentario;

  public EmpresaFavoritaDTO(EmpresaFavorita empresaFavorita, Empresa empresa) {
    this.cnpj = empresaFavorita.getCnpj();
    this.fantasia = empresa.getFantasia();
    this.nivelPerigo = empresa.getNivelPerigo();
    this.comentario = empresaFavorita.getComentario();
  }

  public EmpresaFavoritaDTO() {
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

  public String getFantasia() {
    return fantasia;
  }

  public void setFantasia(String fantasia) {
    this.fantasia = fantasia;
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
    EmpresaFavoritaDTO other = (EmpresaFavoritaDTO) obj;
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
