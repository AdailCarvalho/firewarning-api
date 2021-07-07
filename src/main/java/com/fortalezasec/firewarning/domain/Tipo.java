package com.fortalezasec.firewarning.domain;

public enum Tipo {
  POPULACAO(1, "ROLE_POPULACAO"), SISTEMA(2, "ROLE_SISTEMA"), ADMIN(3, "ROLE_ADMIN");

  private int cod;
  private String descricao;

  private Tipo(int cod, String descricao) {
    this.cod = cod;
    this.descricao = descricao;
  }

  public int getCod() {
    return cod;
  }

  public String getDescricao() {
    return descricao;
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
