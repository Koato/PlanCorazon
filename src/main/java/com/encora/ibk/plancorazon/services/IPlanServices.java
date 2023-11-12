package com.encora.ibk.plancorazon.services;

import java.util.Optional;
import com.encora.ibk.plancorazon.dao.Person;

public interface IPlanServices {

	Optional<Person> getPerson(String codigoUnico);
	
	Optional<Person> updatePerson(Person person);
}
