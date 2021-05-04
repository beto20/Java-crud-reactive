package com.proyecto.personas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.personas.model.Persona;
import com.proyecto.personas.repo.IPersonaRepo;
import com.proyecto.personas.service.IPersonaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private IPersonaRepo repo;

	@Override
	public Mono<Persona> registrar(Persona p) {
		return repo.save(p);
	}

	@Override
	public Mono<Persona> modificar(Persona p) {
		return repo.save(p);
	}

	@Override
	public Flux<Persona> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Persona> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

}
