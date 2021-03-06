package fr.esiee.pic.production.core.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.esiee.pic.production.core.Application;

/**
 * Classe de test unitaire du dépôt d'utilisateur
 * 
 * @author etudiant
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ArtistRepositoryTest {
	
	@Autowired
	ArtistRepository userRepo;
	
	@Test
	public void testConstructeur() {
		assertNotNull("Artist Repo is null", userRepo);
	}
	
	@Test
	public void testAjoutArtistNull() {
		boolean result = userRepo.addArtist(null);
		
		assertFalse("Artist should not been added", result);
	}
}
