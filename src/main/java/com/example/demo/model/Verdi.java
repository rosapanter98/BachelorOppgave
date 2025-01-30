package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema="CE", name="verdi", catalog= "h602116")
public class Verdi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tittel")
    private String tittel;
    @JsonIgnore
    @OneToMany(mappedBy = "verdi", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VerdiForing> verdiFøringer = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "verdier", fetch = FetchType.EAGER)
    private List<Sporsmol> sporsmol = new ArrayList<>();

    // Constructor, getters, and setters    
    // Getters and setters

    public Verdi() {
        // Default constructor is necessary for JPA
    }
    public Verdi(String tittel) {
        this.tittel = tittel;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public List<VerdiForing> getVerdiFøringer() {
        return verdiFøringer;
    }

    public void setVerdiFøringer(List<VerdiForing> verdiFøringer) {
        this.verdiFøringer = verdiFøringer;
    }

    public List<Sporsmol> getSporsmol() {
        return sporsmol;
    }

    public void setSporsmol(List<Sporsmol> sporsmol) {
        this.sporsmol = sporsmol;
    }

    // Helper methods for managing bi-directional relationships

    public void addSporsmol(Sporsmol sporsmal) {
        this.sporsmol.add(sporsmal);
        sporsmal.getVerdier().add(this);
    }

    public void removeSporsmol(Sporsmol sporsmal) {
        this.sporsmol.remove(sporsmal);
        sporsmal.getVerdier().remove(this);
    }

    @Override
public String toString() {
    return "Verdi{" +
            "id=" + id +
            ", tittel='" + tittel + '\'' +
            ", antallVerdiForinger=" + verdiFøringer.size() +
            ", antallSporsmol=" + sporsmol.size() +
            '}';
}
}