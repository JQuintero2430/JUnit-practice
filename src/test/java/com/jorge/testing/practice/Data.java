package com.jorge.testing.practice;

import com.jorge.testing.practice.model.Banco;
import com.jorge.testing.practice.model.Cuenta;

import java.math.BigDecimal;

public class Data {
    static Cuenta CUENTA_001 = new Cuenta(1L, "Jorge", new BigDecimal("1000.12"));
    static Cuenta CUENTA_002 = new Cuenta(1L, "Luis", new BigDecimal("1500.20"));
    static Banco BANCO_001 = new Banco(1L, "Banco Financiero", 0);
}
