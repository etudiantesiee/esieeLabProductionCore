package fr.esiee.pic.production.core.domain;

/**
 * Represente un utilisateur.
 * Classe immuable
 * 
 * @author etudiant
 *
 */
public class User {
	
	/**
	 * Nom de l'utilisateur
	 */
	private final String name;
	
	/**
	 * Constructeur
	 * 
	 * @param name
	 */
	public User(String name) {
		super();
		this.name = name;
	}

	/**
	 * Retourne le nom de l'utilisateur
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
}
