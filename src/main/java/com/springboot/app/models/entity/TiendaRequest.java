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
public class TiendaRequest {
	
	@JsonProperty("AdministrarTiendaRequestMessage")
    private AdministrarTienda tienda;
	
	@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdministrarTienda {
		private Long id;
	    private String nombre;
	    private String horario;
	    private String direccion;
	    private String eliminar;
    }
	
	

}
