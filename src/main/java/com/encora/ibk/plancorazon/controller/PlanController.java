package com.encora.ibk.plancorazon.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.encora.ibk.plancorazon.crypto.CryptoRSA;
import com.encora.ibk.plancorazon.services.IPlanServices;

@RestController
@RequestMapping(value = "/api/plan")
public class PlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

	@Autowired
	private IPlanServices planServices;

	@Autowired
	private CryptoRSA cryptoRSA;

	@GetMapping(value = "/corazon")
	public ResponseEntity<?> getPerson(@RequestHeader String codigoUnico) throws Exception {
		LOGGER.info("codigoUnicoCrypted: {}", codigoUnico);
		// Decodificiar el mensaje con base64
		byte[] base64 = Base64.getDecoder().decode(codigoUnico);
		String codigoUnicoDecrypt = cryptoRSA.decrypt(base64);
		LOGGER.info("codigoUnicoDecrypt: {}", codigoUnicoDecrypt);
		return planServices.getPerson(codigoUnicoDecrypt).map(persona -> {
			LOGGER.info("persona: {}", persona);
			return ResponseEntity.ok(persona);
		}).orElseGet(() -> {
			LOGGER.info("Persona no encontrada para el código: {}", codigoUnicoDecrypt);
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping(value = "/encrypt/{codigoUnico}")
	public ResponseEntity<?> getCodigoUnicoCryoted(@PathVariable(required = true) String codigoUnico) throws Exception {
		LOGGER.info("codigoUnico: {}", codigoUnico);
		try {
			// Utilizar Supplier para la encriptación
			Supplier<byte[]> codigoUnicoCryptedSupplier = () -> {
				try {
					return cryptoRSA.encrypt(codigoUnico);
				} catch (Exception e) {
					throw new RuntimeException("Error en la encriptación", e);
				}
			};
			// Obtener el resultado de la operación de encriptación
			byte[] codigoUnicoCrypted = codigoUnicoCryptedSupplier.get();
			LOGGER.info("codigoUnicoCrypted: {}", new String(codigoUnicoCrypted));
			// Crear un mapa para el objeto de respuesta
			Map<String, byte[]> responseMap = new HashMap<>();
			responseMap.put("codigoUnico", codigoUnicoCrypted);
			return ResponseEntity.ok(responseMap);
		} catch (RuntimeException e) {
			// Manejo de la excepción
			LOGGER.error("Error al encriptar codigoUnico", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
