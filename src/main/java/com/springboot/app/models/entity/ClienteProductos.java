package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "cliente_productos")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.cliente",
        joinColumns = @JoinColumn(name = "ID_CLIENTE")),
    @AssociationOverride(name = "primaryKey.producto",
        joinColumns = @JoinColumn(name = "ID_PRODUCTO")) })
public class ClienteProductos implements Serializable{
	
	@EmbeddedId
	private ClienteProductosId primaryKey = new ClienteProductosId();
	
	@Column(name = "fecha_compra")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(length = 50, name = "no_compra")
	private Long compra;
	
	@Column(name= "precio_total")
	private Double precioTotal;
	
	public ClienteProductosId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ClienteProductosId primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
    public Cliente getCliente() {
        return getPrimaryKey().getCliente();
    }
 
    public void setCliente(Cliente cliente) {
        getPrimaryKey().setCliente(cliente);
    }
 
    @Transient
    public Producto getProducto() {
        return getPrimaryKey().getProducto();
    }
 
    public void setProducto(Producto producto) {
        getPrimaryKey().setProducto(producto);
    }
	
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Long getCompra() {
		return compra;
	}


	public void setCompra(Long compra) {
		this.compra = compra;
	}


	public Double getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private static final long serialVersionUID = 1L;
	
	

}
