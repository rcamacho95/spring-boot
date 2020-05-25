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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tiendas")
public class Tienda implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIENDAS_SQ_GEN")
	@SequenceGenerator(name="TIENDAS_SQ_GEN", sequenceName="TIENDA_SQ", allocationSize = 1)
	@Column(name = "id_tienda")
	private Long idTienda;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 50)
	private String horario;
	
	@Column(length = 255)
	private String direccion;
	
	@JoinTable(
	        name = "tiendas_productos",
	        joinColumns = @JoinColumn(name = "ID_TIENDA", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="ID_PRODUCTO", nullable = false)
	    )
	
	@ManyToMany(cascade = CascadeType.ALL)
    private List<Producto> producto;
	
	public void agregarProducto(Producto product){
        if(this.producto == null){
            this.producto = new ArrayList<>();
        }
        
        this.producto.add(product);
    }

	
	public List<Producto> getProducto() {
		return producto;
	}



	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}



	public Long getIdTienda() {
		return idTienda;
	}



	public void setIdTienda(Long idTienda) {
		this.idTienda = idTienda;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getHorario() {
		return horario;
	}



	public void setHorario(String horario) {
		this.horario = horario;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	private static final long serialVersionUID = 1L;
	
	

}
