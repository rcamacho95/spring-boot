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
public class ClienteResponse {
	
	 private Mensaje message;
	 
	 @JsonProperty("BusquedaClienteResponseMessage")
	 private Busqueda client;
	 @JsonProperty("CompraResponseMessage")
	 private Compras compra;
	 
	 
	 @Data
	    @Builder
	    @AllArgsConstructor
	    @NoArgsConstructor
	    public static class Mensaje {
		 @JsonProperty("ClienteResponseMessage")
		  private String message;
	    }

	 
	 @Data
	    @Builder
	    @AllArgsConstructor
	    @NoArgsConstructor
	    public static class Busqueda {
			private Long id;
		    private String nombre;
		    private String apellido;
		    private String edad;
		    private String tipoDocumento;
		    private String documento;
		    private String correo;
	    }
	 
	 @Data
	    @Builder
	    @AllArgsConstructor
	    @NoArgsConstructor
	    public static class Compras {
		 	@JsonProperty("DatosCliente")
		    private datosCliente clienteDatos;
		 	@JsonProperty("DatosProducto")
		    private List<datosProducto> producto;
		    private String fecha;
		    private Long noCompra;
		    private Double precioTotal;
	    }
	 
	 @Data
	    @Builder
	    @AllArgsConstructor
	    @NoArgsConstructor
	    public static class datosCliente {
		 	private Long idCliente;
		    private String nombreCliente;
		    private String apellidoCliente;
		    private String tipoDocumentoCliente;
		    private String identificacion;
	    }
	 
	 @Data
	    @Builder
	    @AllArgsConstructor
	    @NoArgsConstructor
	    public static class datosProducto {
		 	private Long idProducto;
		    private String nombreProducto;
		    private Double precioProducto;
	    }

}
