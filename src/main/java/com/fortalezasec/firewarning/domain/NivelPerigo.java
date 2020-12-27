package com.fortalezasec.firewarning.domain;

import com.fortalezasec.firewarning.CustomExceptions.TypeDoNotExistsException;

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

  public static NivelPerigo getByDescricao(String nome) throws TypeDoNotExistsException {
    switch (nome) {
      case "OK":
        return NivelPerigo.OK;
      case "WARNING":
        return NivelPerigo.WARNING;
      case "DANGER":
        return NivelPerigo.DANGER;
      default:
        throw new TypeDoNotExistsException("NivelPerigo não existe ou não informado");
    }
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
