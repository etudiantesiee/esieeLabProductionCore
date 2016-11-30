package fr.esiee.pic.production.core.repository.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Encapsule tous les éléments techniques de la connection à Cassandra
 * 
 * @author etudiant
 * 
 */
@Component
public class CassandraManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CassandraManager.class);

	private Session cassandraSession = null;

	@Value("${cluster_contact_point}")
	String contactPoint;
	
	@Value("${application_keyspace}")
	String keyspace;

	/**
	 * 
	 * Return the Cassandra session. When the application starts up, the session
	 * is set to null. When this function is called, it checks to see if the
	 * session is null. If so, it creates a new session, and sets the static
	 * session.
	 * 
	 * All of the DAO classes are subclasses of this
	 * 
	 * @return - a valid cassandra session
	 */
	public Session getSession() {

		if (cassandraSession == null) {
			cassandraSession = createSession();
		}

		return cassandraSession;

	}

	/**
	 * 
	 * Create a new cassandra Cluster() and Session(). Returns the Session.
	 * 
	 * @return A new Cassandra session
	 */
	protected Session createSession() {
		LOGGER.info("Creating cassandra session with cluster contact point : " + contactPoint + " and keyspace : " + keyspace);
		
		Cluster cluster = Cluster.builder().addContactPoint(contactPoint)
				.build();
		
		LOGGER.info("Cassandra session created.");
		return cluster.connect(keyspace);
	}

}
