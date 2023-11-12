package com.encora.ibk.plancorazon.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.encora.ibk.plancorazon.crypto.CryptoRSA;
import com.encora.ibk.plancorazon.dao.Person;
import com.encora.ibk.plancorazon.services.IPlanServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/plan")
@Api(tags = { "Controlador de PlanCorazon" }, description = "Esta API tiene el RU de personas")
public class PlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

	@Autowired
	private IPlanServices planServices;

	@Autowired
	private CryptoRSA cryptoRSA;

	@PostMapping(value = "/corazon")
	@ApiOperation(value = "Actualización de persona", notes = "Actualización de persona por código único", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "CAMPOS FALTANTES"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "ELEMENTO NO ENCONTRADO") })
	public ResponseEntity<?> updatePerson(
			@ApiParam(value = "Estructura del persona a actualizar", required = true) @RequestBody @Valid Person person,
			BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		LOGGER.info("person: {}", person);
		return planServices.updatePerson(person).map(persona -> {
			LOGGER.info("persona: {}", persona);
			return ResponseEntity.ok(persona);
		}).orElseGet(() -> {
			LOGGER.info("Persona no encontrada para el código: {}", person.getCodigoUnico());
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping(value = "/corazon")
	@ApiOperation(value = "Obtener persona según código único encriptado", notes = "Obtener persona por código único encriptado enviado en el Header", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "ELEMENTO NO ENCONTRADO") })
	public ResponseEntity<?> getPerson(
			@ApiParam(value = "Código único encriptado", required = false) @RequestHeader String codigoUnico)
			throws Exception {
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
	@ApiOperation(value = "Obtener código único encriptado", notes = "Obtener código único encriptado", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "NO SE PUDO ENCRIPTAR") })
	public ResponseEntity<?> getCodigoUnicoCryoted(
			@ApiParam(value = "Código único", required = false) @PathVariable(required = true) String codigoUnico)
			throws Exception {
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
