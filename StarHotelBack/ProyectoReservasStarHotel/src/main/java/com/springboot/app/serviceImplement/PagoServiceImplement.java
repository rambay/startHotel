package com.springboot.app.serviceImplement;

import com.springboot.app.model.Pago;
import com.springboot.app.repository.PagoRepository;
import com.springboot.app.service.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PagoServiceImplement implements PagoService {

    @Autowired
    private PagoRepository dao;

    @Override
    public ResponseEntity<Map<String, Object>> listarPagos() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Pago> pagos = dao.findAll();
        
        if (!pagos.isEmpty()) {
            respuesta.put("mensaje", "Lista de pagos");
            respuesta.put("pagos", pagos);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> listarPagoPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Pago> pago = dao.findById(id);
        
        if (pago.isPresent()) {
            respuesta.put("pago", pago.get());
            respuesta.put("mensaje", "Búsqueda correcta");
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> agregarPago(Pago pago) {
        Map<String, Object> respuesta = new HashMap<>();
        dao.save(pago);
        respuesta.put("pago", pago);
        respuesta.put("mensaje", "Se añadió correctamente el pago");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editarPago(Pago pag, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Pago> pagoExiste = dao.findById(id);
        
        if (pagoExiste.isPresent()) {
            Pago pago = pagoExiste.get();
            pago.setReservacion(pag.getReservacion());
            pago.setMetodoPago(pag.getMetodoPago());
            pago.setTitularTarjeta(pag.getTitularTarjeta());
            pago.setNumTarjeta(pag.getNumTarjeta());
            pago.setMontoPagado(pag.getMontoPagado());
            dao.save(pago);
            respuesta.put("pago", pago);
            respuesta.put("mensaje", "Datos del pago modificados");
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarPago(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Pago> pagoExiste = dao.findById(id);
        
        if (pagoExiste.isPresent()) {
            Pago pago = pagoExiste.get();
            dao.delete(pago);
            respuesta.put("mensaje", "Eliminado correctamente");
            respuesta.put("status", HttpStatus.NO_CONTENT);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        } else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}