package fr.esiee.pic.production.core.controller.resources.body;

import org.springframework.hateoas.ResourceSupport;

/**
 * Description de l'API
 * 
 * @author etudiant
 *
 */
public class InitResourceBody extends ResourceSupport {
	
	/**
	 * Version de l'API
	 */
	private String version;
	
	/**
	 * Constructeur avec params
	 * @param version
	 */
	public InitResourceBody(String version) {
		this.version = version;
	}

	/**
	 * getter de version
	 * 
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * setter de version
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
