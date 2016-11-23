package fr.esiee.pic.production.core.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.esiee.pic.production.core.controller.resources.body.ArtistBody;
import fr.esiee.pic.production.core.controller.resources.body.InitResourceBody;
import fr.esiee.pic.production.core.controller.resources.body.UserBody;

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

		// User
		initBody.add(linkTo(methodOn(UserController.class).addNewUser(new UserBody("a_given_user_name"))).withRel("addNewUser"));
		initBody.add(linkTo(methodOn(UserController.class).findUserByName("a_given_user_name")).withRel("findUserByName"));
		
		// Artist
		initBody.add(linkTo(methodOn(ArtistController.class).addNewArtist(new ArtistBody("a_given_artist_name"))).withRel("addNewArtist"));
		initBody.add(linkTo(methodOn(ArtistController.class).listArtistByFirstLetter("a_given_letter")).withRel("listArtistByFirstLetter"));
		
		return new ResponseEntity<InitResourceBody>(initBody, HttpStatus.OK);
	}
}
