package fr.esiee.pic.esieeLabProductionCore.domain;

/**
 * Represente un artiste.
 * Classe immuable
 * 
 * @author etudiant
 *
 */
public class Artist {
	
	/**
	 * Nom de l'artiste
	 */
	private final String name;
	
	/**
	 * Constructeur
	 * 
	 * @param name
	 */
	public Artist(String name) {
		super();
		this.name = name;
	}

	/**
	 * Retourne le nom de l'artiste
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
