package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema="CE", name="standard", catalog= "h602116")
public class Standard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tittel")
    private String tittel;

    @Column(name = "standard_nummer")
    private String standardNummer;

    @Column(name = "publiserings_ar")
    private int publiseringsAr;

    @Column(name = "amendments", columnDefinition = "TEXT")
    private String amendments;
    

    @OneToMany(mappedBy = "standard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VerdiForing> verdiFøringer = new ArrayList<>();

    public Standard(){

    }
    // Constructor, getters, and setters
    public Standard(String tittel, String standardNummer, int publiseringsAr){
        this.tittel=tittel;
        this.standardNummer=standardNummer;
        this.publiseringsAr=publiseringsAr;
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

    public String getStandardNummer() {
        return standardNummer;
    }

    public void setStandardNummer(String standardNummer) {
        this.standardNummer = standardNummer;
    }

    public int getPubliseringsAr() {
        return publiseringsAr;
    }

    public void setPubliseringsAr(int publiseringsAr) {
        this.publiseringsAr = publiseringsAr;
    }

    // Konverter den lagrede tekststrengen til et array av strenger når du henter den

    public String[] getAmendments() {
        return amendments.split(",");
    }

    public void setAmendments(List<String> amendments) {
        this.amendments = String.join(",", amendments);
    }
    



    public List<VerdiForing> getVerdiFøringer() {
        return verdiFøringer;
    }

    public void setVerdiFøringer(List<VerdiForing> verdiFøringer) {
        this.verdiFøringer = verdiFøringer;
    }

    public void addVerdiFøring(VerdiForing verdiFøring) {
        verdiFøringer.add(verdiFøring);
        verdiFøring.setStandard(this);
    }

    public void removeVerdiFøring(VerdiForing verdiFøring) {
        verdiFøringer.remove(verdiFøring);
        verdiFøring.setStandard(null);
    }

    @Override
    public String toString() {
        return "Standard{" +
                "id=" + id +
                ", tittel='" + tittel + '\'' +
                ", standardNummer='" + standardNummer + '\'' +
                ", publiseringsAr=" + publiseringsAr +
                ", amendments='" + amendments + '\'' +
                '}';
    }

}
