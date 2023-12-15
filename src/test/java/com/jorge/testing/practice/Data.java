package com.jorge.testing.practice;

import com.jorge.testing.practice.model.Banco;
import com.jorge.testing.practice.model.Cuenta;

import java.math.BigDecimal;

public class Data {
    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Jorge", new BigDecimal("1000.12"));
    public static final Cuenta CUENTA_002 = new Cuenta(1L, "Luis", new BigDecimal("1500.20"));
    public static final Banco BANCO_001 = new Banco(1L, "Banco Financiero", 0);
}
