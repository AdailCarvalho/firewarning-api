package com.fortalezasec.firewarning.domain;

import com.fortalezasec.firewarning.CustomExceptions.TypeDoNotExistsException;

public enum Status {
  ABERTO(1, "ABERTO"), RESOLVIDO(2, "RESOLVIDO");

  private int cod;
  private String descricao;

  private Status(int cod, String descricao) {
    this.cod = cod;
    this.descricao = descricao;
  }

  public static Status getStatusByDescricao(String descricao) throws TypeDoNotExistsException {
    switch (descricao) {
      case "RESOLVIDO":
        return Status.RESOLVIDO;
      case "ABERTO":
        return Status.ABERTO;
      default:
        throw new TypeDoNotExistsException("Status não existe ou não informado");
    }
  }

  public int getCod() {
    return cod;
  }

  public void setCod(int cod) {
    this.cod = cod;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public static Tipo toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }

    for (Tipo t : Tipo.values()) {
      if (t.getCod() == cod)
        return t;
    }

    throw new IllegalArgumentException("Código invalido: " + cod);

  }
}
