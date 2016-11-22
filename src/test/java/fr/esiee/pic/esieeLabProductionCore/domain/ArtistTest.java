package fr.esiee.pic.esieeLabProductionCore.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test unitaire de la classe Artist
 * 
 * @author etudiant
 *
 */
public class ArtistTest {
	
	@Test
	public void testConstructeur() {
		Artist artist = new Artist("test");
		
		assertNotNull("Artist null", artist);
	}
	
	@Test
	public void testGetter() {
		String artistName = "test";
		Artist artist = new Artist(artistName);
		String nom = artist.getName();
		
		assertEquals("Artist name not equals", artistName, nom);
	}
	
	@Test
	public void testToString() {
		String artistName = "test";
		Artist artist = new Artist(artistName);
		
		String toSringMsg = artist.toString();
		
		assertNotNull("ToString return null", toSringMsg);
	}
}
