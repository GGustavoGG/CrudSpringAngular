package com.backend.backend.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
public class Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "apelido")
    private String apelido;

    @NotNull
    @Column(name = "time_coracao")
    private String timeCoracao;

    @NotNull
    @Column(name = "cpf")
    private String cpf;

    @NotNull
    @Column(name = "hobbie")
    private String hobbie;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @Override
    public boolean equals(Object object) {
        return (object instanceof Cidade) && new EqualsBuilder().append(getId(), ((Cidade)object).getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
}
