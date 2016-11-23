package fr.esiee.pic.production.core.controller.resources.body;

import org.springframework.hateoas.ResourceSupport;

/**
 * Ressource artist
 * 
 * @author etudiant
 *
 */
public class ArtistBody extends ResourceSupport {
	
	/**
	 * Nom de l'artist
	 */
	private String name;

	/**
	 * Default constructeur
	 */
	public ArtistBody() {
		super();
	}
	/**
	 * Constructeur avec params
	 * 
	 * @param name
	 */
	public ArtistBody(String name) {
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
		return "ArtistBody [name=" + name + "]";
	}
}
