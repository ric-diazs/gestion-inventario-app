package com.example.gestion_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_inventario.model.ReporteProblema;
import com.example.gestion_inventario.service.ReporteProblemaService;

@RestController
@RequestMapping("/api/v1/reportes-problema")
public class ReporteProblemaController {
    @Autowired
    private ReporteProblemaService reporteProblemaService;

    @GetMapping
    public ResponseEntity<List<ReporteProblema>> obtenerReportesProblema() {
        List<ReporteProblema> reportesProblema = reporteProblemaService.obtenerReportesProblema();

        if(reportesProblema.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportesProblema);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteProblema> obtenerUnReporteProblema(@PathVariable Integer id) {
        try {
            ReporteProblema reporteProblema = reporteProblemaService.obtenerUnReporteProblema(id);

            return ResponseEntity.ok(reporteProblema);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReporteProblema> crearReporteProblema(@RequestBody ReporteProblema reporteProblema) {
        ReporteProblema nuevoReporteProblema = reporteProblemaService.guardarReporteProblema(reporteProblema);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporteProblema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteProblema> actualizarReporteProblema(@PathVariable Integer id, @RequestBody ReporteProblema reporteProblema) {
        try {
            ReporteProblema reporteProblemaActualizar = reporteProblemaService.obtenerUnReporteProblema(id);

            reporteProblemaActualizar.setId(id);
            reporteProblemaActualizar.setDescripcion(reporteProblema.getDescripcion());
            reporteProblemaActualizar.setTipoProblema(reporteProblema.getTipoProblema());
            reporteProblemaActualizar.setUsuario(reporteProblema.getUsuario());
            reporteProblemaActualizar.setNivelPrioridad(reporteProblema.getNivelPrioridad());

            reporteProblemaService.guardarReporteProblema(reporteProblemaActualizar);

            return ResponseEntity.ok(reporteProblema);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporteProblema(@PathVariable Integer id) {
        try {
            reporteProblemaService.eliminarReporteProblema(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
