package com.jorge.testing.practice;

import com.jorge.testing.practice.exception.DineroInsuficienteException;
import com.jorge.testing.practice.model.Banco;
import com.jorge.testing.practice.model.Cuenta;
import com.jorge.testing.practice.repository.BancoRepository;
import com.jorge.testing.practice.repository.CuentaRepository;
import com.jorge.testing.practice.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static com.jorge.testing.practice.Data.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TestingPracticeApplicationTests {
    @MockBean
    CuentaRepository cuentaRepository;
    @MockBean
    BancoRepository bancoRepository;
    @Autowired
    CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(CUENTA_001));
        when(cuentaRepository.findById(2L)).thenReturn(Optional.of(CUENTA_002));
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(BANCO_001));
    }

    @Test
    void contextLoads() {
        BigDecimal saldo1 = cuentaService.revisarSaldo(1L);
        BigDecimal saldo2 = cuentaService.revisarSaldo(2L);
        assertEquals("1000.12", saldo1.toPlainString());
        assertEquals("1500.20", saldo2.toPlainString());

        cuentaService.transferir(1L, 2L, new BigDecimal("100"), 1L);

        saldo1 = cuentaService.revisarSaldo(1L);
        saldo2 = cuentaService.revisarSaldo(2L);

        assertEquals("900.12", saldo1.toPlainString());
        assertEquals("1600.20", saldo2.toPlainString());

        int total = cuentaService.revisarTotalTransferencias(1L);
        assertEquals(1, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(cuentaRepository, times(2)).save(any(Cuenta.class));
        verify(bancoRepository, times(2)).findById(1L);
        verify(bancoRepository).save(any(Banco.class));
    }

    @Test
    void contextLoads2() {

        BigDecimal saldo1 = cuentaService.revisarSaldo(1L);
        BigDecimal saldo2 = cuentaService.revisarSaldo(2L);
        assertEquals("1000.12", saldo1.toPlainString());
        assertEquals("1500.20", saldo2.toPlainString());

        assertThrows(DineroInsuficienteException.class, () -> cuentaService.transferir(1L, 2L, new BigDecimal("1200"), 1L));

        saldo1 = cuentaService.revisarSaldo(1L);
        saldo2 = cuentaService.revisarSaldo(2L);

        assertEquals("1000.12", saldo1.toPlainString());
        assertEquals("1500.20", saldo2.toPlainString());

        int total = cuentaService.revisarTotalTransferencias(1L);
        assertEquals(0, total);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(cuentaRepository, never()).save(any(Cuenta.class));
        verify(bancoRepository, times(1)).findById(1L);
    }

}
