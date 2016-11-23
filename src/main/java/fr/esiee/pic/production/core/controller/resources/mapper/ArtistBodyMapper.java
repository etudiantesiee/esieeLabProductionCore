package fr.esiee.pic.production.core.controller.resources.mapper;

import java.util.ArrayList;
import java.util.List;

import fr.esiee.pic.production.core.controller.resources.body.ArtistBody;
import fr.esiee.pic.production.core.domain.Artist;

/**
 * Mapper entre le domaine et le body
 * 
 * @author etudiant
 *
 */
public final class ArtistBodyMapper {

	/**
	 * Constructeur par defaut caché
	 */
	private ArtistBodyMapper() {
		super();
	}
	
	/**
	 * Mapping à partir d'une liste
	 * 
	 * @param domainArtists
	 * @return
	 */
	public static List<ArtistBody> mapFromDomain(List<Artist> domainArtists) {
		List<ArtistBody> artistsBody = new ArrayList<>();
		
		if (domainArtists == null) {
			// paramètre null entrée
			return artistsBody;
		}
		
		// mapping
		for (Artist artist : domainArtists) {
			String name = artist.getName();
			ArtistBody artistbody = new ArtistBody(name);
			artistsBody.add(artistbody);
		}
		
		return artistsBody;
	}
}
