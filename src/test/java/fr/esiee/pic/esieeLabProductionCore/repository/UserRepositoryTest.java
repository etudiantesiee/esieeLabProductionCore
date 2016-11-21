package fr.esiee.pic.esieeLabProductionCore.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import fr.esiee.pic.esieeLabProductionCore.Application;
import static org.junit.Assert.*;

/**
 * Classe de test unitaire du dépôt d'utilisateur
 * 
 * @author etudiant
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userREpo;
	
	@Test
	public void testUserRepoInjection() {
		assertNotNull(userREpo);
	}
	
	@Test
	public void testConstructeur() {
		UserRepository userREpo = new UserRepository();
		assertNotNull("User Repo is null", userREpo);
	}
	
	@Test
	public void testAjoutUserNull() {
		UserRepository userREpo = new UserRepository();
		boolean result = userREpo.addUser(null);
		
		assertFalse("User should not been added", result);
	}

}
