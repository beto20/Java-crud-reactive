package com.proyecto.personas.test;

import static org.mockito.Mockito.mock;

import java.net.URI;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;

import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.util.MultiValueMap;

import com.proyecto.person.business.PersonBusiness;
import com.proyecto.person.business.impl.PersonBusinessImpl;
import com.proyecto.person.dto.PersonDTO;
import com.proyecto.person.exception.NotFoundException;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.PersonRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class PersonTest {

	PersonRepo repo;
	PersonBusiness service;

	@BeforeEach
	public void init() {
		repo = mock(PersonRepo.class);
		service = new PersonBusinessImpl(repo);
	}

	@Test
	public void testEdit_thenOk(){
		Person p = new Person();
		p.setId("123");
		p.setName("nombre");
		p.setLastname("apellido");
		p.setDni("1234");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");

		PersonDTO pDTO = new PersonDTO();
		pDTO.setId("123");
		pDTO.setName("nombre");
		pDTO.setLastname("apellido");
		pDTO.setDni("1234");
		pDTO.setAddress("prueba");
		pDTO.setPhoto("prueba");
		pDTO.setState("prueba");

		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(moPerson);
		Mockito.when(repo.save(p))
				.thenReturn(moPerson);

		StepVerifier.create(service.edit(pDTO,"123"))
				.consumeNextWith(responseEntity -> {
					Assertions.assertThat(responseEntity).isNotNull();
					Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("nombre");
					Assertions.assertThat(responseEntity.getBody().getLastname()).isEqualTo("apellido");
					Assertions.assertThat(responseEntity.getBody().getDni()).isEqualTo("1234");
				})
				.verifyComplete();
	}

	@Test
	void testList_thenOk(){
		Person p = new Person();
		p.setId("123");
		p.setName("pruebaNOM");
		p.setLastname("pruebaAPE");
		p.setDni("12345678");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");

		Flux<Person> fxPerson = Flux.just(p);
		Mockito.when(repo.findAll())
				.thenReturn(fxPerson);
		StepVerifier.create(service.list())
				.consumeNextWith(prueba -> {
					Assertions.assertThat(prueba).isNotNull();
					Assertions.assertThat(prueba).isEqualTo(p);
				});
	}

	@Test
	public void testDelete_thenOk(){
		Person p = new Person();
		p.setId("123");

		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(moPerson);

		StepVerifier.create(service.delete("123123213"))
				.consumeNextWith(prueba -> {
					Assertions.assertThat(prueba).isNotNull();
				});

	}

	@Test
	void testDelete_thenError(){
		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(Mono.empty());
		StepVerifier.create(service.delete("null"))
				.consumeErrorWith(error ->{
					Assertions.assertThat(error).isNotNull();
					Assertions.assertThat(error).isInstanceOf(NotFoundException.class);
				})
				.verify();
	}

	@Test
	void testListById_thenOk() {
		Person p = new Person();
		p.setName("pruebaNOM");
		p.setLastname("pruebaAPE");
		p.setDni("12345678");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");

		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(moPerson);

		StepVerifier.create(service.listById("123"))
				.consumeNextWith(responseEntity -> {
					Assertions.assertThat(responseEntity).isNotNull();
					Assertions.assertThat(responseEntity.getBody()).isNotNull();
					Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("pruebaNOM");
					Assertions.assertThat(responseEntity.getBody().getLastname()).isEqualTo("pruebaAPE");
				})
				.verifyComplete();
	}

	@Test
	void testListById_thenError() {
		
		Mockito.when(repo.findById(Mockito.anyString()))
		.thenReturn(Mono.empty());
		StepVerifier.create(service.listById("123"))
		.consumeErrorWith(error -> {
			 Assertions.assertThat(error).isNotNull();
			 Assertions.assertThat(error).isInstanceOf(NotFoundException.class);
			})
		.verify();
	}
	
	@Test
	void testInsert_thenOk() {
		Person p = new Person();
		p.setId("123");
		p.setName("pruebaNOM");
		p.setLastname("pruebaAPE");
		p.setDni("12345678");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");
		
		PersonDTO pdto = new PersonDTO();
		pdto.setId("123");
		pdto.setName("pruebaNOM");
		pdto.setLastname("pruebaAPE");
		pdto.setDni("12345678");
		pdto.setAddress("prueba");
		pdto.setPhoto("prueba");
		pdto.setState("prueba");

		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.insert(Mockito.any(Person.class)))
		.thenReturn(moPerson);
		
		StepVerifier.create(service.insert(pdto, mockReq()))
		.consumeNextWith(responseEntity -> {
			 Assertions.assertThat(responseEntity).isNotNull();
			 Assertions.assertThat(responseEntity.getBody()).isNotNull();
			 Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("pruebaNOM");
			 Assertions.assertThat(responseEntity.getBody().getLastname()).isEqualTo("pruebaAPE");
			})
		.verifyComplete();
	} 
	
	public ServerHttpRequest mockReq() {
		return new ServerHttpRequest() {
			
			@Override
			public Flux<DataBuffer> getBody() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public HttpHeaders getHeaders() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URI getURI() {
				// TODO Auto-generated method stub
				return URI.create("localhost:8080/");
			}
			
			@Override
			public String getMethodValue() {
				// TODO Auto-generated method stub
				return "Post";
			}
			
			@Override
			public MultiValueMap<String, String> getQueryParams() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestPath getPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MultiValueMap<String, HttpCookie> getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
