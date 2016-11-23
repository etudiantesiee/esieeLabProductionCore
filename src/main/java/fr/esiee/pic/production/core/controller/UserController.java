package fr.esiee.pic.production.core.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.esiee.pic.production.core.controller.resources.body.UserBody;
import fr.esiee.pic.production.core.domain.User;
import fr.esiee.pic.production.core.repository.UserRepository;

/**
 * Conroller des utilisateurs
 * @author etudiant
 *
 */
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/technicienSMI/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNewUser(@RequestBody UserBody userToCreate) {
		
		// Récupération du nom
		String userName = userToCreate.getName();
		User user = new User(userName);
		
		// Insertion dans le dépôt
		boolean userCreationSucess = userRepo.addUser(user);
		
		// Retour HTTP
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = userCreationSucess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseEntity<Void> response = new ResponseEntity<Void>(headers, httpStatus);
		
		return response;
	}

}
