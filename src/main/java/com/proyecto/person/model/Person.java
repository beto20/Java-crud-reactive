package com.proyecto.person.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "people")
public class Person {


	@Id
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastname;
	@NotNull
	private String dni;
	
	private String address;
	
	private String photo;
	
	private String state;

}
