package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(schema="CE", name="verdiForing", catalog= "h602116")
public class VerdiForing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numerisk_verdi")
    private Long numeriskVerdi;  // Numerisk int tall som nevnt

    // Relasjon til Verdi
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verdi_id")
    private Verdi verdi;

    // Relasjon tilbake til Standard
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "standard_id")
    private Standard standard;

    // Constructor, Getters and Setters

    public VerdiForing() {
    }

    public VerdiForing(Long numeriskVerdi, Verdi verdi, Standard standard) {
        this.numeriskVerdi = numeriskVerdi;
        this.verdi = verdi;
        this.standard = standard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeriskVerdi() {
        return numeriskVerdi;
    }

    public void setNumeriskVerdi(Long numeriskVerdi) {
        this.numeriskVerdi = numeriskVerdi;
    }

    public Verdi getVerdi() {
        return verdi;
    }

    public void setVerdi(Verdi verdi) {
        this.verdi = verdi;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }
}