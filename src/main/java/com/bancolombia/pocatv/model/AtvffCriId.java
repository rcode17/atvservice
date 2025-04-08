package com.bancolombia.pocatv.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class AtvffCriId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "crano", nullable = false)
    private Integer crano;

    @Column(name = "crmes", nullable = false)
    private Integer crmes;

    @Column(name = "crcodsuc", nullable = false)
    private Integer crcodsuc;

    @Column(name = "crcopr", length = 2, nullable = false)
    private String crcopr;

    @Column(name = "crcodo", length = 3, nullable = false)
    private String crcodo;

    @Column(name = "crfear", nullable = false)
    private LocalDate crfear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AtvffCriId)) return false;
        AtvffCriId that = (AtvffCriId) o;
        return Objects.equals(crano, that.crano) &&
                Objects.equals(crmes, that.crmes) &&
                Objects.equals(crcodsuc, that.crcodsuc) &&
                Objects.equals(crcopr, that.crcopr) &&
                Objects.equals(crcodo, that.crcodo) &&
                Objects.equals(crfear, that.crfear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crano, crmes, crcodsuc, crcopr, crcodo, crfear);
    }
}