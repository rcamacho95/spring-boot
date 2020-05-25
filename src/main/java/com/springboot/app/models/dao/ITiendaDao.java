package com.springboot.app.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Tienda;

public interface ITiendaDao extends CrudRepository<Tienda, Long>{
	
	
}
