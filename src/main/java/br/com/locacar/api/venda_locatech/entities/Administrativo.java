package br.com.locacar.api.venda_locatech.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tb_administrativo")
public class Administrativo {
    @Id
    private Integer numero;
    private String nome;

    public Administrativo() {}

    public Administrativo(Integer numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrativo that = (Administrativo) o;
        return Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Administrativo{" +
                "numero=" + numero +
                ", nome='" + nome + '\'' +
                '}';
    }
}
