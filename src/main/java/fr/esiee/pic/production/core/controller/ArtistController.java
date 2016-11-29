package fr.esiee.pic.production.core.controller;

import java.util.List;

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

import fr.esiee.pic.production.core.controller.resources.body.ArtistBody;
import fr.esiee.pic.production.core.controller.resources.mapper.ArtistBodyMapper;
import fr.esiee.pic.production.core.domain.Artist;
import fr.esiee.pic.production.core.repository.ArtistRepository;

/**
 * Conroller des artistes
 * 
 * @author etudiant
 *
 */
@RestController
public class ArtistController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArtistController.class);

	@Autowired
	ArtistRepository artistRepo;
	
	@RequestMapping(value = "/artist/", method = RequestMethod.POST)
    public ResponseEntity<Void> addNewArtist(@RequestBody ArtistBody artistToCreate) {
		
		// Récupération du nom
		String artistName = artistToCreate.getName();
		Artist artist = new Artist(artistName);
		
		// Insertion dans le dépôt
		boolean artistCreationSucess = artistRepo.addArtist(artist);
		
		LOGGER.info("Ajout de l'artiste " + artistName + "avec le retour : " + artistCreationSucess);
		
		// Retour HTTP
		HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = artistCreationSucess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseEntity<Void> response = new ResponseEntity<Void>(headers, httpStatus);
		
		return response;
	}
	
	
	@RequestMapping(value = "/artist/{firstNameLetter}", method = RequestMethod.GET)
	public HttpEntity<List<ArtistBody>> listArtistByFirstLetter(@PathVariable("firstNameLetter") String firstNameLetter) {
		// Recherche de l'artiste
		List<Artist> artistsFromDB = artistRepo.listArtistByLetter(firstNameLetter, true);
		
		if (artistsFromDB == null) {
			return new ResponseEntity<List<ArtistBody>>(HttpStatus.NOT_FOUND);
		}
		
		// Construction du body
		List<ArtistBody> findedArtistsBody = ArtistBodyMapper.mapFromDomain(artistsFromDB);
		
		return new ResponseEntity<List<ArtistBody>>(findedArtistsBody, HttpStatus.OK);
	}

}
