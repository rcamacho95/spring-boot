package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CLIENTES_SQ_GEN")
	@SequenceGenerator(name="CLIENTES_SQ_GEN", sequenceName="CLIENTES_SQ", allocationSize = 1)
	@Column(name = "id_cliente")
	private Long idcliente;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 50)
	private String apellido;
	
	@JsonProperty("edad")
	private Long edad;
	
	@Column(name = "tipo_documento", length = 20)
	private String tipoDocumento;
	
	@Column(length = 20, unique = true)
	private String documento;
	
	@Column(length = 50, unique = true)
	private String correo;
	
	@Column(length = 20)
	private String usuario;
	
	@Column(length = 20)
	private String contrasena;
	
	@OneToMany(mappedBy = "primaryKey.cliente", cascade = CascadeType.ALL)
    private List<ClienteProductos> clienteProductos = new ArrayList<>();
	
	
	public List<ClienteProductos> getProducto() {
		return clienteProductos;
	}

	public void setProducto(List<ClienteProductos> producto) {
		this.clienteProductos = producto;
	}
	
	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getEdad() {
		return edad;
	}

	public void setEdad(Long edad) {
		this.edad = edad;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

}
