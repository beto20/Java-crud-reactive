package com.proyecto.personas.test;

import com.proyecto.person.business.PersonBusiness;
import com.proyecto.person.business.impl.PersonBusinessImpl;
import com.proyecto.person.dto.PersonDTO;
import com.proyecto.person.exception.NotFoundException;
import com.proyecto.person.model.Person;
import com.proyecto.person.repo.PersonRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

import static org.mockito.Mockito.mock;


public class PruebaTestResp {
	

	
	//@Autowired
	//WebTestClient webTestClient;
	//@Mock
	PersonRepo repo;
	PersonBusiness service;

	WebTestClient webTestClient;


	@BeforeEach
	public void init() {
		repo = mock(PersonRepo.class);
		service = new PersonBusinessImpl(repo);
	 /* EJEMPLO
		clientRepository = mock(ClientRepository.class);
	 addressesRepository = mock(AddressesRepository.class);
	 hanaLoadFilters = mock(HanaLoadFilters.class, RETURNS_DEEP_STUBS);
	 clientFacade = mock(ClientFacade.class);
	 jobService = mock(JobService.class);
	 clientService = new HanaClientService(clientRepository,
	         addressesRepository,
	         hanaLoadFilters,
	         clientFacade,
	         jobService);
	         */
	}


	@Test
	public void testEdit_thenOk(){
		Person p = new Person();
		p.setId("123");
		p.setName("nombre");
		p.setLastname("apellido");
		p.setDni("123");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");

		PersonDTO pdto = new PersonDTO();
		pdto.setId("123");
		pdto.setName("nombre");
		pdto.setLastname("apellido");
		pdto.setDni("123");
		pdto.setAddress("prueba");
		pdto.setPhoto("prueba");
		pdto.setState("prueba");

		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(moPerson);
		Mockito.when(repo.save(p))
				.thenReturn(moPerson);
		StepVerifier.create(service.edit(pdto,"123"))
				.consumeNextWith(responseEntity -> {
					Assertions.assertThat(responseEntity).isNotNull();
				})
				.verifyComplete();
	}

	@Test
	public void testDelete_thenOk(){
		Person p = new Person();
		p.setId("123");

		Mono<Void> moVoid = Mono.empty();
		Mono<Person> moPerson = Mono.just(p);

		Mockito.when(repo.findById(Mockito.anyString()))
				.thenReturn(moPerson);

		StepVerifier.create(service.delete("123123213"))
				.consumeNextWith(prueba -> {
					Assertions.assertThat(prueba).isNotNull();
					//Assertions.assertThat(prueba).returns(moVoid);
				});
				//.expectFusion(404,404);
	}

	@Test
	public void testDelete_thenError(){
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
	void testList_thenOk(){
		Person p = new Person();
		p.setId("123");
		p.setName("pruebaNOM");
		p.setLastname("pruebaAPE");
		p.setDni("12345678");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");
		Person p2 = new Person();
		p2.setId("123");
		p2.setName("pruebaNOM");
		p2.setLastname("pruebaAPE");
		p2.setDni("12345678");
		p2.setAddress("prueba");
		p2.setPhoto("prueba");
		p2.setState("prueba");


		Flux<Person> fxPerson = Flux.just(p,p2);
		//ResponseEntity<Flux<Person>> resPerson= ResponseEntity.ok(fxPerson);
		//Mono<ResponseEntity<Flux<Person>>> moResFxPerson = Mono.just(resPerson);

		Mockito.when(repo.findAll())
				.thenReturn(fxPerson);
		StepVerifier.create(service.list())
				.consumeNextWith(prueba -> {
					Assertions.assertThat(prueba).isNotNull();
					Assertions.assertThat(prueba).isEqualTo(p2);
				});
	}

	@Test
	public void testListById_thenOk() {

		Person p = new Person();
		p.setName("pruebaNOM");
		p.setLastname("pruebaAPE");
		p.setDni("12345678");
		p.setAddress("prueba");
		p.setPhoto("prueba");
		p.setState("prueba");

		Mono<Person> moPerson = Mono.just(p);
		//ResponseEntity<Person> resPerson= ResponseEntity.ok(p);
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
		//when(findAll.begin(any(.class)))
		//.thenReturn(Mono.just(mockJob));
	}

	@Test
	public void testListById_thenError() {
		
		Mockito.when(repo.findById(Mockito.anyString()))
		.thenReturn(Mono.empty());
		StepVerifier.create(service.listById("12"))
		.consumeErrorWith(error -> {
			 Assertions.assertThat(error).isNotNull();
			 Assertions.assertThat(error).isInstanceOf(NotFoundException.class);
			})
		.verify();
	}
	
	@Test
	public void testInsert_thenOk() {
		
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

		/*
		MockServerHttpRequest(String httpMethod,
				URI uri, @Nullable String contextPath, HttpHeaders headers, MultiValueMap<String, HttpCookie> cookies,
				@Nullable InetSocketAddress localAddress, @Nullable InetSocketAddress remoteAddress,
				@Nullable SslInfo sslInfo, Publisher<? extends DataBuffer> body)
		*/
		
		//MockServerHttpRequest req = MockServerHttpRequest.method(HttpMethod.POST, URI.create("localhost:8080/")).contextPath("/").build();

		Mono<Person> moPerson = Mono.just(p);
		//ResponseEntity<PersonDTO> resPerson= ResponseEntity.ok(pdto);

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
		//when(findAll.begin(any(.class)))
        //.thenReturn(Mono.just(mockJob));
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
