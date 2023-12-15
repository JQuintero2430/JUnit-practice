package com.jorge.testing.practice.controller;

import com.jorge.testing.practice.model.Cuenta;
import com.jorge.testing.practice.model.dto.TransaccionDto;
import com.jorge.testing.practice.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Optional<Cuenta> detalle(@PathVariable Long id) {
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto) {
        cuentaService.transferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(), dto.getMonto(), dto.getBancoId());
        Map<String, Object> response = Map.of(
                "date", new Date(),
                "status", "OK",
                "mensaje", "Transferencia realizada con éxito",
                "transaccion", dto
        );
        return ResponseEntity.ok(response);
    }
}
