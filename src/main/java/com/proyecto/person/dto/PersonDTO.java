package com.proyecto.person.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PersonDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastname;
	@NotEmpty
	private String dni;
	
	private String address;
	
	private String photo;
	
	private String state;

}
