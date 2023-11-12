package com.encora.ibk.plancorazon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.encora.ibk.plancorazon.dao.Person;

@Repository
public interface IPlanRepository extends JpaRepository<Person, Long> {

	Optional<Person> getByCodigoUnico(String codigoUnico);

}
