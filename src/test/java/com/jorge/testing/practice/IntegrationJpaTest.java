package com.jorge.testing.practice;

import com.jorge.testing.practice.model.Cuenta;
import com.jorge.testing.practice.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegrationJpaTest {
    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

        assertTrue(cuenta.isPresent());
        assertEquals("Jorge", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Maria");

        assertTrue(cuenta.isPresent());
        assertEquals("Maria", cuenta.orElseThrow().getPersona());
        assertEquals("2000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testThrowExceptio() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Juan");

        assertFalse(cuenta.isPresent());
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        assertFalse(cuentas.isEmpty());
        assertEquals(5, cuentas.size());
    }

    @Test
    void testSave() {
        // Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        Cuenta cuenta = cuentaRepository.save(cuentaPepe);
        // When
        cuentaRepository.findByPersona("Pepe");
        // Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
        assertEquals(6L, cuenta.getId());

    }

    @Test
    void testUpdate() {
        // Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new java.math.BigDecimal("3000"));
        Cuenta cuenta = cuentaRepository.save(cuentaPepe);
        // When
        cuentaRepository.findByPersona("Pepe");
        // And
        cuenta.setSaldo(new BigDecimal("3800"));
        cuenta.setPersona("Pedro");
        // Then
        assertEquals("Pedro", cuenta.getPersona());
        assertEquals("3800", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        // Given
        Cuenta cuenta = cuentaRepository.findById(4L).orElseThrow();
        assertEquals("Ana", cuenta.getPersona());
        // When
        cuentaRepository.delete(cuenta);
        // Then
        assertThrows(NoSuchElementException.class, () -> cuentaRepository
                .findByPersona("Ana").orElseThrow());
        assertEquals(4, cuentaRepository.findAll().size());
    }
}
