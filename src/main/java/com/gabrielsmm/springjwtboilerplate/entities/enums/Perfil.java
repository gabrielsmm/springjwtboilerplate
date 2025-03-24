package com.gabrielsmm.springjwtboilerplate.entities.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    USUARIO(2, "ROLE_USUARIO");

    private final int codigo;
    private final String descricao;

    Perfil(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte um código para um objeto do tipo Perfil.
     *
     * @param codigo Código do perfil.
     * @return Perfil
     */
    public static Perfil toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (Perfil p : Perfil.values()) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

}
