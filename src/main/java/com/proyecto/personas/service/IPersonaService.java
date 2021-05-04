package com.proyecto.personas.service;

import com.proyecto.personas.model.Persona;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonaService {
	public Mono<Persona> registrar (Persona p);
	public Mono<Persona> modificar (Persona p);
	public Flux<Persona> listar();
	public Mono<Persona> listarPorId(String id);
	public Mono<Void> eliminar(String id);
}
