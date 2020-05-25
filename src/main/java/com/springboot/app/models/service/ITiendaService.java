package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Tienda;

public interface ITiendaService {

public List<Tienda> findAll();
	
	public void save(Tienda tienda);
	
	public Tienda findOne(Long id);
	
	public void delete(Long id);
	
}
