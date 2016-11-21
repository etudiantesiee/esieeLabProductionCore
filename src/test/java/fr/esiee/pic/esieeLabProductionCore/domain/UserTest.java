package fr.esiee.pic.esieeLabProductionCore.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test unitaire de la classe User
 * 
 * @author etudiant
 *
 */
public class UserTest {
	
	@Test
	public void testConstructeur() {
		User user = new User("test");
		
		assertNotNull("User null", user);
	}
	
	@Test
	public void testGetter() {
		String userName = "test";
		User user = new User(userName);
		String nom = user.getName();
		
		assertEquals("User name not equals", userName, nom);
	}
	
	@Test
	public void testToString() {
		String userName = "test";
		User user = new User(userName);
		
		String toSringMsg = user.toString();
		
		assertNotNull("ToString return null", toSringMsg);
	}
}
