package com.jorge.testing.practice.service.impl;

import com.jorge.testing.practice.model.Banco;
import com.jorge.testing.practice.model.Cuenta;
import com.jorge.testing.practice.repository.BancoRepository;
import com.jorge.testing.practice.repository.CuentaRepository;
import com.jorge.testing.practice.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final BancoRepository bancoRepository;
    @Autowired
    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }


    @Override
    public Optional<Cuenta> findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId)
                .orElseThrow(() -> new IllegalArgumentException("Banco no existe"));
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(cuentaId);
        return cuenta.map(Cuenta::getSaldo).orElseThrow();
    }

    @Override
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = cuentaRepository.findById(numeroCuentaOrigen)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no existe"));
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numeroCuentaDestino)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no existe"));
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId)
                .orElseThrow(() -> new IllegalArgumentException("Banco no existe"));
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.save(banco);
    }

    @Override
    public Optional<Cuenta> findByPersona(String persona) {
        return cuentaRepository.findByPersona(persona);
    }
}
