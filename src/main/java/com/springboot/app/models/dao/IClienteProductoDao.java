package com.springboot.app.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.ClienteProductos;


public interface IClienteProductoDao extends CrudRepository<ClienteProductos, Long>{
	
	
	@Query(value = "SELECT CLIENTE_PRODUCTOS_SQ.NEXTVAL FROM DUAL", nativeQuery = true)
	public Long valorSecuencia();
	
}
