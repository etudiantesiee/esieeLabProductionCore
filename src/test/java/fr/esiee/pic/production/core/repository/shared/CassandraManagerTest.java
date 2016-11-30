package fr.esiee.pic.production.core.repository.shared;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.esiee.pic.production.core.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class CassandraManagerTest {
	
	@Autowired
	CassandraManager manager; 
	
	@Test
	public void testConstructeur() {
		assertNotNull("Injection of Cassandra manager failed", manager);
	}
}
