package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.ITiendaDao;
import com.springboot.app.models.entity.Tienda;

@Service
public class TiendaServiceImplement implements ITiendaService {

	@Autowired
	private ITiendaDao tiendaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tienda> findAll() {
		// TODO Auto-generated method stub
		return (List<Tienda>) tiendaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Tienda tienda) {
		// TODO Auto-generated method stub
		tiendaDao.save(tienda);
	}

	@Override
	@Transactional(readOnly = true)
	public Tienda findOne(Long id) {
		// TODO Auto-generated method stub
		return tiendaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		tiendaDao.deleteById(id);
	}

}
