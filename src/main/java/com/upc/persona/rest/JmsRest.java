package com.upc.persona.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.upc.persona.entidades.Persona;
import com.upc.persona.jms.JmsPersona;

@RestController
@RequestMapping("/api")

public class JmsRest {
	
	@Autowired
	private JmsPersona jmsPersona;
	
	@PostMapping("/enviar")
	public Persona enviar(@RequestBody Persona persona) 
	{
		ObjectMapper mapper = new ObjectMapper();
		
		//Objetict a JSON en String
		String jsonString = null;
		
		try {
			jsonString = mapper.writeValueAsString(persona);
			//se envia a la cola en String
			
			jmsPersona.send(jsonString);
			persona.setRespuesta("Enviado a Cola!");
			
			
			
		}catch (JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
			persona.setRespuesta("Error en trama, no se envía! ");
			
		}
		
		return persona;
	}

}
