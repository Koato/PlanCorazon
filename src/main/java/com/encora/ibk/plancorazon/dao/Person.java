package com.encora.ibk.plancorazon.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombres;
	private String apellidos;
	
	@Column(name = "tipodocumento")
	private String tipoDocumento;
	
	@Column(name = "numerodocumento")
	private Long numeroDocumento;
	
	@Column(name = "codigounico")
	private String codigoUnico;
}
