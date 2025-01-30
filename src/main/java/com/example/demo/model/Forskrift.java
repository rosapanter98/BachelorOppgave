package com.example.demo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "CE", name = "forskrifter", catalog = "h602116")
public class Forskrift {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="tittel") 
    private String tittel;
    @Column(name="beskrivelse") 
    private String beskrivelse;
    @JsonIgnore
    @Column(name="publiseringsdato")
    private LocalDate publiseringsdato;
    @Column(name="url")
    private String url;

    public Forskrift() {
    }
    public Forskrift(String tittel, String beskrivelse, LocalDate publiseringsdato, String url){
        this.tittel = tittel;
        this.beskrivelse = beskrivelse;
        this.publiseringsdato=publiseringsdato;
        this.url=url;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getTittel(){
        return tittel;
    }
    public void setTittel(String tittel){
        this.tittel=tittel;
    }
    public String getBeskrivelse(){
        return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse){
        this.beskrivelse=beskrivelse;
    }
    public LocalDate getPubliseringsdato() {
            return publiseringsdato;
        }

    public void setPubliseringsdato(LocalDate publiseringsdato){
        this.publiseringsdato=publiseringsdato;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
}
