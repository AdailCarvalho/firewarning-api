package com.fortalezasec.firewarning.domain;

import java.io.Serializable;

public class EmpresaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String cnpj;
  private String fantasia;
  private String contato;
  private NivelPerigo nivelPerigo;

  public EmpresaDTO(Empresa empresa) {
    this.cnpj = empresa.getCnpj();
    this.fantasia = empresa.getFantasia();
    this.contato = empresa.getContato();
    this.nivelPerigo = empresa.getNivelPerigo();
  }

  public EmpresaDTO() {
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getFantasia() {
    return fantasia;
  }

  public void setFantasia(String fantasia) {
    this.fantasia = fantasia;
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
    EmpresaDTO other = (EmpresaDTO) obj;
    if (cnpj == null) {
      if (other.cnpj != null)
        return false;
    } else if (!cnpj.equals(other.cnpj))
      return false;
    return true;
  }

}
