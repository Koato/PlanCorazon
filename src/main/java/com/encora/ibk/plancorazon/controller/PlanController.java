package com.encora.ibk.plancorazon.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encora.ibk.plancorazon.dao.Person;
import com.encora.ibk.plancorazon.services.IPlanServices;

@RestController
@RequestMapping(value = "/api/plan")
public class PlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);
	
	@Autowired
	private IPlanServices planServices;
	
	@GetMapping(value = "/corazon/{codigoUnico}")
	public ResponseEntity<?> getPerson(@PathVariable(required = true) String codigoUnico) {
		LOGGER.info("codigoUnico: {}", codigoUnico);
		Optional<Person> persona = planServices.getPerson(codigoUnico);
		LOGGER.info("persona: {}", persona);
		if (persona.isPresent()) {
			return ResponseEntity.ok(persona.get());
		}
		return ResponseEntity.notFound().build();
	}
}
