package com.jorge.testing.practice.service;

import com.jorge.testing.practice.model.Cuenta;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

public interface CuentaService {
    Optional<Cuenta> findById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId);
    Optional<Cuenta> findByPersona(String persona);
}
