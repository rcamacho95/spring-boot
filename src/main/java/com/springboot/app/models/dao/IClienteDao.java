package com.springboot.app.models.dao;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
	@Query("from Cliente c where c.documento = :doc")
	public Cliente findByDocumento(String doc);
	
	@Query("from Cliente c where c.usuario = :user and c.contrasena= :password")
	public Cliente findByUsername(String user, String password);
	
}
