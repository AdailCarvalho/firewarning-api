package com.fortalezasec.firewarning.domain;

public enum NivelPerigo {
  OK(1, "OK"), WARNING(2, "WARNING"), DANGER(3, "DANGER");

  private int cod;
  private String descricao;

  private NivelPerigo(int cod, String descricao) {
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
