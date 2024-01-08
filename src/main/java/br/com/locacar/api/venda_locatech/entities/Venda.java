package br.com.locacar.api.venda_locatech.entities;

import br.com.locacar.api.venda_locatech.valueobject.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    private LocalDate dataAgendada;
    @ManyToOne
    @JoinColumn(name = "diretor_numero")
    private Diretor diretor;
    @ManyToOne
    @JoinColumn(name = "administrativo_numero")
    private Administrativo administrativo;
    @OneToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;
    private Status status;

    public Venda() {}

    public Venda(Long numero, LocalDate dataAgendada, Diretor diretor, Administrativo administrativo,
                 Veiculo veiculo, Status status) {
        this.numero = numero;
        this.dataAgendada = dataAgendada;
        this.diretor = diretor;
        this.administrativo = administrativo;
        this.veiculo = veiculo;
        this.status = status;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Diretor getDiretor() {
        return diretor;
    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }

    public Administrativo getAdministrativo() {
        return administrativo;
    }

    public void setAdministrativo(Administrativo administrativo) {
        this.administrativo = administrativo;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(numero, venda.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "numero=" + numero +
                ", dataAgendada=" + dataAgendada +
                ", diretor=" + diretor +
                ", administrativo=" + administrativo +
                ", veiculo=" + veiculo +
                ", status=" + status +
                '}';
    }
}
