package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.ReporteProblema;
import com.example.gestion_inventario.repository.ReporteProblemaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteProblemaService {
    @Autowired
    private ReporteProblemaRepository reporteProblemaRepository;

    public List<ReporteProblema> obtenerReportesProblema() {
        return reporteProblemaRepository.findAll();
    }

    public ReporteProblema obtenerUnReporteProblema(Integer id) {
        return reporteProblemaRepository.findById(id).get();
    }

    public ReporteProblema guardarReporteProblema(ReporteProblema reporteProblema) {
        return reporteProblemaRepository.save(reporteProblema);
    }

    public void eliminarReporteProblema(Integer id) {
        reporteProblemaRepository.deleteById(id);
    }
}
