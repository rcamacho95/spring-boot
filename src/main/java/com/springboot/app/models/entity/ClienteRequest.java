package com.springboot.app.models.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
	
	@JsonProperty("AdministrarClienteRequestMessage")
    private CrearCliente crearCliente;
	@JsonProperty("BuscarClienteRequestMessage")
    private BusquedaCliente busquedaCliente;
	@JsonProperty("ComprarProductosRequestMessage")
	private CompraProductos compraProductos;
	
	@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CrearCliente {
	    private String nombre;
	    private String apellido;
	    private String edad;
	    private String tipoDocumento;
	    private String documento;
	    private String correo;
	    private String usuario;
	    private String contrasena;
	    private String eliminar;
    }
	
	@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusquedaCliente {
	    private String usuario;
	    private String contrasena;
    }
	
	@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompraProductos {
	    private List<Long> idProducto;
	    private Long idCliente;
    }
	
	

}
