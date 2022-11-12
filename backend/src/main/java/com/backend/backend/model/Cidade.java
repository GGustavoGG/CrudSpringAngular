package com.backend.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cidade")
@Getter
@Setter
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name="nome")
    private String nome;

    @NotNull
    @Column(name = "qtd_habitantes")
    private Long qtdHabitantes;

    @NotNull
    @Column(name = "estado")
    private String estado;



    @Override
    public boolean equals(Object object) {
        return (object instanceof Cidade) && new EqualsBuilder().append(getId(), ((Cidade)object).getId()).isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
