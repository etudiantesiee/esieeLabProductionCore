package fr.esiee.pic.production.core.repository.shared;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Represent a generic cassandra repo
 * 
 * @author etudiant
 *
 */
public abstract class CassandraRepository {
	
	  private static Session cassandraSession = null;

	  /**
	   * Default constructor
	   */
	  public CassandraRepository () {
	    super();
	  }

	  /**
	   *
	   *  Return the Cassandra session.
	   *  When the application starts up, the session
	   *  is set to null.  When this function is called, it checks to see if the session is null.
	   *  If so, it creates a new session, and sets the static session.
	   *
	   *  All of the DAO classes are subclasses of this
	   *
	   * @return - a valid cassandra session
	   */
	  public static Session getSession() {

	    if (cassandraSession == null) {
	      cassandraSession = createSession();
	    }

	    return cassandraSession;

	  }

	  /**
	   *
	   * Create a new cassandra Cluster() and Session().  Returns the Session.
	   *
	   * @return A new Cassandra session
	   */
	  protected static Session createSession() {
	    Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	    return cluster.connect("playlist_comments");
	  }

}
