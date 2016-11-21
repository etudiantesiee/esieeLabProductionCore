package fr.esiee.pic.esieeLabProductionCore.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.esiee.pic.esieeLabProductionCore.Application;

/**
 * Classe de test unitaire du dépôt d'utilisateur
 * 
 * @author etudiant
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void testConstructeur() {
		assertNotNull("User Repo is null", userRepo);
	}
	
	@Test
	public void testAjoutUserNull() {
		boolean result = userRepo.addUser(null);
		
		assertFalse("User should not been added", result);
	}
}
