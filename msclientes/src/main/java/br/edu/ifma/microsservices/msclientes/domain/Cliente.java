package br.edu.ifma.microsservices.msclientes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
@NoArgsConstructor
@Table(schema ="clientedb", name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @Column(unique = true, nullable = false)
    private String cpf;

    @Column
    private String nome;

    @Column
    private Integer idade;

    public Cliente(String cpf, String nome, Integer idade){
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    @PrePersist
    @PreUpdate
    private void formatarCpf() {
        this.cpf = this.cpf.replaceAll("[^0-9]", ""); // Remove pontos e traços
    }
}
