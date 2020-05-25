package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PRODUCTO_SQ_GEN")
	@SequenceGenerator(name="PRODUCTO_SQ_GEN", sequenceName="PRODUCTO_SQ", allocationSize = 1)
	@Column(name = "id_producto")
	private Long idProducto;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 255)
	private String descripcion;
	
	private Double precio;
	
	@Column(name = "codigo_barras", length = 255)	
	private String codigoBarras;
	
	@ManyToMany(mappedBy = "producto")
    private List<Tienda> tienda;
	
	@OneToMany(mappedBy = "primaryKey.producto", cascade = CascadeType.ALL) 
    private List<ClienteProductos> clientePr = new ArrayList<>();	
	
	public List<ClienteProductos> getCliente() {
		return clientePr;
	}


	public void setCliente(List<ClienteProductos> cliente) {
		this.clientePr = cliente;
	}


	public List<Tienda> getTienda() {
		return tienda;
	}


	public void setTienda(List<Tienda> tienda) {
		this.tienda = tienda;
	}



	public Long getIdProducto() {
		return idProducto;
	}



	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Double getPrecio() {
		return precio;
	}



	public void setPrecio(Double precio) {
		this.precio = precio;
	}



	public String getCodigoBarras() {
		return codigoBarras;
	}



	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	private static final long serialVersionUID = 1L;

}
