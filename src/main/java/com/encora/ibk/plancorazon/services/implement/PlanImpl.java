package com.encora.ibk.plancorazon.services.implement;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encora.ibk.plancorazon.dao.Person;
import com.encora.ibk.plancorazon.repository.IPlanRepository;
import com.encora.ibk.plancorazon.services.IPlanServices;

@Service("iPlanServices")
public class PlanImpl implements IPlanServices {

	@Autowired
	private IPlanRepository planRepository;

	@Override
	@Transactional(readOnly = true)
	public Optional<Person> getPerson(String codigoUnico) {
		return planRepository.getByCodigoUnico(codigoUnico);
	}

	@Override
	@Transactional
	public Optional<Person> updatePerson(Person person) {
		planRepository.save(person);
		return getPerson(person.getCodigoUnico());
	}

}
