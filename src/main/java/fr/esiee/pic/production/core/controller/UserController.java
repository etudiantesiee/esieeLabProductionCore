package fr.esiee.pic.production.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNewUser(@RequestBody UserBody userToCreate) {
		
		// Récupération du nom
		String userName = userToCreate.getName();
		User user = new User(userName);
		
		// Insertion dans le dépôt
		boolean userCreationSucess = userRepo.addUser(user);
		
		LOGGER.debug("Ajout de l'utilisateur " + userName + "avec le retour : " + userCreationSucess);
		
		// Retour HTTP
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = userCreationSucess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseEntity<Void> response = new ResponseEntity<Void>(headers, httpStatus);
		
		return response;
	}
	
	
	@RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
	public HttpEntity<UserBody> getTechnicien(@PathVariable("name") String name) {
		// Recherche de l'utilisateur
		User user = userRepo.getUserByName(name);
		
		if (user == null) {
			return new ResponseEntity<UserBody>(HttpStatus.NOT_FOUND);
		}
		
		// Construction du body
		UserBody findedUserBody = new UserBody();
		String findedUserName = user.getName();
		findedUserBody.setName(findedUserName);
		
		return new ResponseEntity<UserBody>(findedUserBody, HttpStatus.OK);
	}

}
