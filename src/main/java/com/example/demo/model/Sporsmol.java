package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "CE", name = "sporsmol", catalog = "h602116")
public class Sporsmol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tittel")
    private String tittel;

    @ManyToMany(/* cascade = CascadeType.ALL, */ fetch = FetchType.EAGER)
    @JoinTable(
        schema = "CE",
        name = "sporsmol_relations",
        joinColumns = @JoinColumn(name = "parent_sporsmol_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "child_sporsmol_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Sporsmol> children = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "children", fetch = FetchType.EAGER)
    private List<Sporsmol> parents = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        schema = "CE",
        name = "sporsmol_verdi",
        joinColumns = @JoinColumn(name = "sporsmol_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "verdi_id", referencedColumnName = "id")
    )
    private List<Verdi> verdier;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        schema = "CE",
        name = "sporsmol_forskrift",
        joinColumns = @JoinColumn(name = "sporsmol_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "forskrift_id", referencedColumnName = "id")
    )
    private List<Forskrift> forskrifter = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tilleggsinformasjon_id")
    private Tilleggsinformasjon tilleggsinformasjon;

    @Column(name="beskrivelse")
    private String beskrivelse;

    public Sporsmol() {
        this.verdier = new ArrayList<>();
    }

    public Sporsmol(String tittel, List<Sporsmol> parents, List<Sporsmol> children, List<Verdi> verdier, List<Forskrift> forskrifter, Tilleggsinformasjon tilleggsinformasjon, String beskrivelse) {
        this.tittel = tittel;
        this.parents = parents != null ? parents : new ArrayList<>();
        this.children = children != null ? children : new ArrayList<>();
        this.verdier = verdier != null ? verdier : new ArrayList<>();
        this.forskrifter=forskrifter !=null ? forskrifter: new ArrayList<>();
        this.tilleggsinformasjon = tilleggsinformasjon;
        this.beskrivelse=beskrivelse;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse){
        this.beskrivelse=beskrivelse;
    }
    // Getters and setters
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
    public List<Sporsmol> getChildren() {
        return children;
    }

    public void setChildren(List<Sporsmol> children) {
        this.children = children;
    }

    public List<Sporsmol> getParents() {
        return parents;
    }

    public void setParents(List<Sporsmol> parents) {
        this.parents = parents;
    }

    public List<Verdi> getVerdier() {
        return verdier;
    }

    public void setVerdier(List<Verdi> verdier) {
        this.verdier = verdier;
    }

    public void setForskrifter(List<Forskrift> forskrifter){
        this.forskrifter=forskrifter;
    }
    public List<Forskrift> getForskrifter(){
        return forskrifter;
    }
    public Tilleggsinformasjon getTilleggsinformasjon(){
        return tilleggsinformasjon;
    }
    public void setTilleggsinformasjon(Tilleggsinformasjon tilleggsinformasjon){
        this.tilleggsinformasjon=tilleggsinformasjon;
    }

    public void addChild(Sporsmol child) {
        if (child != null && !isCyclic(child)) {
            this.children.add(child);
            child.getParents().add(this);
        } else {
            throw new IllegalStateException("Adding this child would create a cycle!");
        }
    }

    public void addParent(Sporsmol parent) {
        if (parent != null && !isCyclic(this, new HashSet<>())) {
            this.parents.add(parent);
            parent.getChildren().add(this);
        } else {
            throw new IllegalStateException("Adding this parent would create a cycle!");
        }
    }

    private boolean isCyclic(Sporsmol candidateChild, Set<Sporsmol> visited) {
        if (visited.contains(this)) {
            return true;
        }
        visited.add(this);
        for (Sporsmol parent : this.parents) {
            if (parent.equals(candidateChild) || parent.isCyclic(candidateChild, new HashSet<>(visited))) {
                return true;
            }
        }
        visited.remove(this);
        return false;
    }

    // Helper method specifically for addChild to use initially
    private boolean isCyclic(Sporsmol candidateChild) {
        return isCyclic(candidateChild, new HashSet<>());
    }
}