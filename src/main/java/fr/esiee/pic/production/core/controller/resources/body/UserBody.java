package fr.esiee.pic.production.core.controller.resources.body;

import org.springframework.hateoas.ResourceSupport;

/**
 * Ressource utilisateur
 * 
 * @author etudiant
 *
 */
public class UserBody extends ResourceSupport {
	
	/**
	 * Nom de l'utilisateur
	 */
	private String name;

	/**
	 * Default constructeur
	 */
	public UserBody() {
		super();
	}
	/**
	 * Constructeur avec params
	 * 
	 * @param name
	 */
	public UserBody(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserBody [name=" + name + "]";
	}
}
