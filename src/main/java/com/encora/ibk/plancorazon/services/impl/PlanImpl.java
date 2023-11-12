package com.encora.ibk.plancorazon.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.encora.ibk.plancorazon.dao.Person;
import com.encora.ibk.plancorazon.repository.IPlanRepository;
import com.encora.ibk.plancorazon.services.IPlanServices;

@Service("iPlanServices")
public class PlanImpl implements IPlanServices {

	@Autowired
	private IPlanRepository planRepository;

	@Override
	public Optional<Person> getPerson(String codigoUnico) {
		return planRepository.getByCodigoUnico(codigoUnico);
	}

}
