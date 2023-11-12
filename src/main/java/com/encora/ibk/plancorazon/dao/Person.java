package com.encora.ibk.plancorazon.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Modelo de Persona")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Identificador de persona", required = false)
	private Long id;
	
	@ApiModelProperty(value = "Nombres", required = false)
	private String nombres;
	
	@ApiModelProperty(value = "Apellidos", required = false)
	private String apellidos;
	
	@Column(name = "tipodocumento")
	@ApiModelProperty(value = "Tipo de documento", required = false)
	private String tipoDocumento;
	
	@Column(name = "numerodocumento")
	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Número de documento", required = true)
	private Long numeroDocumento;
	
	@Column(name = "codigounico")
	@NotEmpty(message = "no puede estar vacio")
	@ApiModelProperty(value = "Código único", required = true)
	private String codigoUnico;
}
