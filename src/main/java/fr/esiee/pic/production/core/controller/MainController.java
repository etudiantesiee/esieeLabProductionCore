package fr.esiee.pic.production.core.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.esiee.pic.production.core.controller.resources.body.InitResourceBody;

@RestController
@RequestMapping("/")
public class MainController {

	/**
	 * Version de l'API
	 */
	private static final String API_VERSION = "0.0.1";

	/**
	 * Initialisation des URL des ressources
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<InitResourceBody> getInitResource() {
		InitResourceBody initBody = new InitResourceBody(API_VERSION);
		return new ResponseEntity<InitResourceBody>(initBody, HttpStatus.OK);
	}
}
