package com.springboot.app.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {
	
	@JsonProperty("AdministrarProductoRequestMessage")
    private AdministrarProducto producto;
	
	@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdministrarProducto {
		private Long id;
	    private String nombre;
	    private String descripcion;
	    private String precio;
	    private String codigoBarras;
	    private String eliminar;
    }
	
	

}
