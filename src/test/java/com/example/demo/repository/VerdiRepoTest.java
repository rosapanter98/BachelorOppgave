package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Verdi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

@SpringBootTest
@Transactional
public class VerdiRepoTest {

    @Autowired
    private VerdiRepo verdiRepo;

    @Test
    public void whenFindByTittel_thenReturnVerdi() {
        String tittel = "UniqueTitle";
        Verdi verdi = new Verdi();
        verdi.setTittel(tittel);
        verdiRepo.save(verdi);

        Verdi found = verdiRepo.findByTittel(tittel);
        assertThat(found.getTittel()).isEqualTo(tittel);
    }

    @Test
    public void whenFindByTittel_withNonExistingTitle_thenReturnNull() {
        String tittel = "NonExistingTitle";
        Verdi found = verdiRepo.findByTittel(tittel);
        assertThat(found).isNull();
    }
}