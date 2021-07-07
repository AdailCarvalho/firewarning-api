package com.fortalezasec.firewarning.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fortalezasec.firewarning.Utils.CustomValidators;

import br.com.caelum.stella.validation.CNPJValidator;

@Entity
public class Incidente implements Serializable {

  private static final long serialVersionUID = 1L;

  @Transient
  private CustomValidators validators = new CustomValidators(new CNPJValidator());

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Informe o CNPJ da empresa")
  private String cnpjEmpresa;

  @NotNull(message = "Informe o nível de perigo")
  private NivelPerigo nivelPerigo;

  @NotNull(message = "Descreva o incidente")
  private String comentario;

  @NotNull(message = "Informe a data e o horário do incidente")
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private LocalDateTime data;

  @NotNull
  private Status status;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  @JsonInclude(Include.NON_EMPTY)
  private LocalDateTime dataResolucao;

  public Incidente(Long id, String cnpjEmpresa, NivelPerigo nivelPerigo, String comentario, LocalDateTime data,
      Status status, LocalDateTime dataResolucao) {
    this.id = id;
    this.cnpjEmpresa = cnpjEmpresa;
    this.nivelPerigo = nivelPerigo;
    this.comentario = comentario;
    this.data = data;
    this.status = status;
    this.dataResolucao = dataResolucao;
  }

  public Incidente() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCnpjEmpresa() {
    return cnpjEmpresa;
  }

  public void setCnpjEmpresa(String cnpjEmpresa) {
    validators.validarCnpj(cnpjEmpresa);
    cnpjEmpresa = cnpjEmpresa.replaceAll("[./-]", "");
    this.cnpjEmpresa = cnpjEmpresa;
  }

  public NivelPerigo getNivelPerigo() {
    return nivelPerigo;
  }

  public void setNivelPerigo(NivelPerigo nivelPerigo) {
    this.nivelPerigo = nivelPerigo;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getDataResolucao() {
    return dataResolucao;
  }

  public void setDataResolucao(LocalDateTime dataResolucao) {
    this.dataResolucao = dataResolucao;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((data == null) ? 0 : data.hashCode());
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
    Incidente other = (Incidente) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
