package com.springboot.app.controller;

import static java.util.Objects.isNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.entity.ClienteProductos;
import com.springboot.app.models.entity.ClienteRequest;
import com.springboot.app.models.entity.ClienteResponse;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.ProductoRequest;
import com.springboot.app.models.entity.ProductoResponse;
import com.springboot.app.models.entity.Tienda;
import com.springboot.app.models.entity.TiendaRequest;
import com.springboot.app.models.entity.TiendaResponse;
import com.springboot.app.models.entity.ClienteResponse.Busqueda;
import com.springboot.app.models.entity.ClienteResponse.Compras;
import com.springboot.app.models.entity.ClienteResponse.datosCliente;
import com.springboot.app.models.entity.ClienteResponse.datosProducto;
import com.springboot.app.models.service.IClienteProductoService;
import com.springboot.app.models.service.IClienteService;
import com.springboot.app.models.service.IProductoService;
import com.springboot.app.models.service.ITiendaService;

import lombok.SneakyThrows;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/farmatodo")
@Log4j2
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	@Autowired
	private IProductoService productoService;
	@Autowired
	private ITiendaService tiendaService;
	@Autowired
	private IClienteProductoService clienteProductoService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Value("${topic.name}")
	private String nombreTopico;

	@PostMapping("/administrarCliente")
	public ResponseEntity<?> crearCliente(@RequestBody ClienteRequest cliente) {

		val validationErrors = validate(cliente);

		if (validationErrors.isPresent()) {
			return new ResponseEntity<>(new ClienteResponse.Mensaje(validationErrors.get()), HttpStatus.BAD_REQUEST);
		}

		Cliente cl = new Cliente();
		cl.setNombre(cliente.getCrearCliente().getNombre());
		cl.setApellido(cliente.getCrearCliente().getApellido());
		cl.setTipoDocumento(cliente.getCrearCliente().getTipoDocumento());
		cl.setDocumento(cliente.getCrearCliente().getDocumento());
		cl.setCorreo(cliente.getCrearCliente().getCorreo());
		cl.setUsuario(cliente.getCrearCliente().getUsuario());
		cl.setContrasena(cliente.getCrearCliente().getContrasena());

		try {
			cl.setEdad(Long.parseLong(cliente.getCrearCliente().getEdad()));
			Cliente c = clienteService.findByDocumento(cliente.getCrearCliente().getDocumento());

			if (c != null) {
				if (cliente.getCrearCliente().getEliminar().equals("Yes")) {
					clienteService.delete(c.getIdcliente());
					return new ResponseEntity<>(new ClienteResponse.Mensaje("Cliente eliminado con éxito"),
							HttpStatus.OK);
				}
				cl.setIdcliente(c.getIdcliente());
				clienteService.save(cl);
				return new ResponseEntity<>(new ClienteResponse.Mensaje("Cliente actualizado con éxito"),
						HttpStatus.OK);
			} else {

				clienteService.save(cl);
				return new ResponseEntity<>(new ClienteResponse.Mensaje("Cliente guardado con éxito"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ClienteResponse.Mensaje("Ha ocurrido un error, por favor verifique"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/administrarProducto")
	public ResponseEntity<?> crearProducto(@RequestBody ProductoRequest producto) {

		val validationErrors = validateProducto(producto);

		if (validationErrors.isPresent()) {
			return new ResponseEntity<>(new ProductoResponse(validationErrors.get()), HttpStatus.BAD_REQUEST);
		}

		Producto pr = new Producto();
		pr.setNombre(producto.getProducto().getNombre());
		pr.setDescripcion(producto.getProducto().getDescripcion());
		pr.setCodigoBarras(producto.getProducto().getCodigoBarras());

		try {
			pr.setPrecio(Double.parseDouble(producto.getProducto().getPrecio()));
			Producto p = null;
			if (producto.getProducto().getId() != null) {
				p = productoService.findOne(producto.getProducto().getId());
			}

			if (p != null) {
				if (producto.getProducto().getEliminar().equals("Yes")) {
					productoService.delete(p.getIdProducto());
					return new ResponseEntity<>(new ProductoResponse("Producto eliminado con éxito"), HttpStatus.OK);
				}
				pr.setIdProducto(p.getIdProducto());
				productoService.save(pr);
				return new ResponseEntity<>(new ProductoResponse("Producto actualizado con éxito"), HttpStatus.OK);
			} else {

				productoService.save(pr);
				return new ResponseEntity<>(new ProductoResponse("Producto guardado con éxito"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ProductoResponse("Ha ocurrido un error, por favor verifique"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/administrarTienda")
	public ResponseEntity<?> crearTienda(@RequestBody TiendaRequest tienda) {

		val validationErrors = validateTienda(tienda);

		if (validationErrors.isPresent()) {
			return new ResponseEntity<>(new TiendaResponse(validationErrors.get()), HttpStatus.BAD_REQUEST);
		}

		Tienda ti = new Tienda();
		ti.setNombre(tienda.getTienda().getNombre());
		ti.setDireccion(tienda.getTienda().getDireccion());
		ti.setHorario(tienda.getTienda().getHorario());

		try {

			Tienda t = null;

			if (tienda.getTienda().getId() != null) {
				t = tiendaService.findOne(tienda.getTienda().getId());
			}

			if (t != null) {
				if (tienda.getTienda().getEliminar().equals("Yes")) {
					tiendaService.delete(t.getIdTienda());
					return new ResponseEntity<>(new TiendaResponse("Tienda eliminada con éxito"), HttpStatus.OK);
				}
				ti.setIdTienda(t.getIdTienda());
				tiendaService.save(ti);
				return new ResponseEntity<>(new TiendaResponse("Tienda actualizada con éxito"), HttpStatus.OK);
			} else {

				tiendaService.save(ti);
				return new ResponseEntity<>(new TiendaResponse("Tienda guardada con éxito"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new TiendaResponse("Ha ocurrido un error, por favor verifique"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/buscarCliente")
	@SneakyThrows
	public ResponseEntity<?> busquedaCliente(@RequestBody ClienteRequest client) {

		try {
			
			log.info("Request Body: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client.getBusquedaCliente()));
			if (isNull(client.getBusquedaCliente().getUsuario())) {
				log.info("el campo usuario es requerido");
				return new ResponseEntity<>(new ClienteResponse.Mensaje("el campo usuario es requerido"),
						HttpStatus.BAD_REQUEST);
			}
			if (isNull(client.getBusquedaCliente().getContrasena())) {
				log.info("el campo contrasena es requerido");
				return new ResponseEntity<>(new ClienteResponse.Mensaje("El campo contrasena es requerido"),
						HttpStatus.BAD_REQUEST);
			}

			Cliente c = null;

			c = clienteService.findByUsername(client.getBusquedaCliente().getUsuario(),
					client.getBusquedaCliente().getContrasena());

			if (c != null) {
				
				ClienteResponse cr = new ClienteResponse();
				Busqueda b = new Busqueda();
				b.setNombre(c.getNombre());
				b.setApellido(c.getApellido());
				b.setEdad(c.getEdad().toString());
				b.setCorreo(c.getCorreo());
				b.setDocumento(c.getDocumento());
				b.setTipoDocumento(c.getTipoDocumento());
				b.setId(c.getIdcliente());

				cr.setClient(b);
				sendSuccess(cr.getClient());
				log.info("Response Body: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cr.getClient()));
				return new ResponseEntity<>(cr.getClient(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ClienteResponse.Mensaje("Cliente no encontrado"), HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.info("Ha ocurrido un error: "+e.getMessage());
			return new ResponseEntity<>(new ClienteResponse.Mensaje("Ha ocurrido un error, por favor verifique"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@SneakyThrows
    private void sendSuccess(Object response) {
		kafkaTemplate.send(nombreTopico,mapper.writeValueAsString(response));
    }

	@PostMapping("/compraProductos")
	public ResponseEntity<?> comprarProductos(@RequestBody ClienteRequest client) {
		
		try {
			
			if(client.getCompraProductos().getIdProducto()==null) {
				return new ResponseEntity<>(new ClienteResponse.Mensaje("el campo idProducto es requerido"),HttpStatus.BAD_REQUEST);
			}
			
			if(client.getCompraProductos().getIdCliente()==null) {
				return new ResponseEntity<>(new ClienteResponse.Mensaje("el campo idCliente es requerido"),HttpStatus.BAD_REQUEST);
			}
			
			Cliente c = null;
			c = clienteService.findOne(client.getCompraProductos().getIdCliente());
			
			if(c==null) {
				return new ResponseEntity<>(new ClienteResponse.Mensaje("Cliente no encontrado"),HttpStatus.NOT_FOUND);
			}
			
			Producto p = null;
			int cont=0;
			double precioTotal=0;
			List<Producto> pro = new ArrayList<Producto>();
			for(Long id : client.getCompraProductos().getIdProducto()) {
				p = productoService.findOne(id);
				if(p!=null) {
					pro.add(p);
					precioTotal += p.getPrecio();
					cont ++;
				}
			}
			
			if(cont == client.getCompraProductos().getIdProducto().size()) {
				List<ClienteProductos> cp = new ArrayList<ClienteProductos>();
				long noCompra = clienteProductoService.valorSecuencia();
				for(Producto clp : pro) {
					ClienteProductos clpr = new ClienteProductos();
					clpr.setCliente(c);
					clpr.setProducto(clp);
					clpr.setFecha(new Date());
					clpr.setPrecioTotal(precioTotal);
					clpr.setCompra(noCompra);
					cp.add(clpr);
					}
				   Iterable<ClienteProductos> save = clienteProductoService.save(cp);
				    
				    List<ClienteResponse> clres = new ArrayList<ClienteResponse>();
				    List<Compras> compras = new ArrayList<ClienteResponse.Compras>();
				    List<datosProducto> dp = new ArrayList<ClienteResponse.datosProducto>();
				    		    
				    
				    for(ClienteProductos clienteProduct: save) {
				    	Compras clientePro = new Compras();
				    	
				    	datosCliente dc = new datosCliente();
				    	dc.setIdCliente(clienteProduct.getCliente().getIdcliente());
				    	dc.setNombreCliente(clienteProduct.getCliente().getNombre());
				    	dc.setApellidoCliente(clienteProduct.getCliente().getApellido());
				    	dc.setTipoDocumentoCliente(clienteProduct.getCliente().getTipoDocumento());
				    	dc.setIdentificacion(clienteProduct.getCliente().getDocumento());
				    	
				    	datosProducto daP = new datosProducto();
			    		daP.setIdProducto(clienteProduct.getProducto().getIdProducto());
			    		daP.setNombreProducto(clienteProduct.getProducto().getNombre());
			    		daP.setPrecioProducto(clienteProduct.getProducto().getPrecio());
			    		dp.add(daP);
				    	 		
				   
				    	clientePro.setClienteDatos(dc);
				    	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");  
				    	clientePro.setFecha(f.format(clienteProduct.getFecha()));
				    	clientePro.setNoCompra(clienteProduct.getCompra());
				    	clientePro.setPrecioTotal(clienteProduct.getPrecioTotal());
				    	clientePro.setProducto(dp);
				    	compras.add(clientePro);
				    }
				    for(Compras s: compras) {
				    	ClienteResponse x = new ClienteResponse();
				    	x.setCompra(s);
				    	clres.add(x);
				    }
				return new ResponseEntity<>(clres.iterator().next().getCompra(),HttpStatus.OK);    				
				//return new ResponseEntity<>(new ClienteResponse.Mensaje("Compra asociada al cliente: "+c.getNombre() + " "+c.getApellido()), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ClienteResponse.Mensaje("Algún idProducto no existe, por favor verificar"),
						HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ClienteResponse.Mensaje("Ha ocurrido un error, por favor verifique"),
					HttpStatus.BAD_REQUEST);
		}
		
	}

	private static Optional<String> validate(ClienteRequest c) {
		val errors = new StringBuilder();
		val req = c.getCrearCliente();

		if (isNull(req)) {
			errors.append("AdministrarClienteRequestMessage es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getNombre())) {
			errors.append("El campo nombre es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getApellido())) {
			errors.append("El campo apellido es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getEdad())) {
			errors.append("El campo edad es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getDocumento())) {
			errors.append("El campo documento es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getTipoDocumento())) {
			errors.append("El campo tipoDocumento es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getCorreo())) {
			errors.append("El campo correo es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getUsuario())) {
			errors.append("El campo usuario es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getContrasena())) {
			errors.append("El campo contrasena es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getEliminar())) {
			errors.append("El campo eliminar es requerido");
			return Optional.of(errors.toString());
		}
		if (req.getEliminar().equals("Yes") || req.getEliminar().equals("No")) {
			return Optional.empty();
		} else {
			errors.append("El campo eliminar debe tener los valores 'Yes' o 'No'");
			return Optional.of(errors.toString());
		}

	}

	private static Optional<String> validateProducto(ProductoRequest p) {
		val errors = new StringBuilder();
		val req = p.getProducto();

		if (isNull(req)) {
			errors.append("AdministrarProductoRequestMessage es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getNombre())) {
			errors.append("El campo nombre es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getDescripcion())) {
			errors.append("El campo descripcion es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getPrecio())) {
			errors.append("El campo precio es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getCodigoBarras())) {
			errors.append("El campo codigoBarras es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getEliminar())) {
			errors.append("El campo eliminar es requerido");
			return Optional.of(errors.toString());
		}
		if (req.getEliminar().equals("Yes") || req.getEliminar().equals("No")) {
			return Optional.empty();
		} else {
			errors.append("El campo eliminar debe tener los valores 'Yes' o 'No'");
			return Optional.of(errors.toString());
		}

	}

	private static Optional<String> validateTienda(TiendaRequest t) {
		val errors = new StringBuilder();
		val req = t.getTienda();

		if (isNull(req)) {
			errors.append("AdministrarTiendaRequestMessage es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getNombre())) {
			errors.append("El campo nombre es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getHorario())) {
			errors.append("El campo horario es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getDireccion())) {
			errors.append("El campo direccion es requerido");
			return Optional.of(errors.toString());
		}
		if (isNull(req.getEliminar())) {
			errors.append("El campo eliminar es requerido");
			return Optional.of(errors.toString());
		}
		if (req.getEliminar().equals("Yes") || req.getEliminar().equals("No")) {
			return Optional.empty();
		} else {
			errors.append("El campo eliminar debe tener los valores 'Yes' o 'No'");
			return Optional.of(errors.toString());
		}

	}

}
