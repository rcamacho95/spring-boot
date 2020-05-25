package com.springboot.app.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IClienteProductoDao;
import com.springboot.app.models.entity.ClienteProductos;

@Service
public class ClienteProductosTiendaServiceImplement implements IClienteProductoService {

	@Autowired
	private IClienteProductoDao cpDao;

	@Override
	@Transactional
	public Iterable<ClienteProductos> save(List<ClienteProductos> cp) {
		return cpDao.saveAll(cp);	
	}

	@Override
	public Long valorSecuencia() {
		// TODO Auto-generated method stub
		return cpDao.valorSecuencia();
	}
	
	

}
