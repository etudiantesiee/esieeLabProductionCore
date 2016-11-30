package fr.esiee.pic.production.core.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import fr.esiee.pic.production.core.domain.User;
import fr.esiee.pic.production.core.repository.shared.CassandraManager;

/**
 * Dépôt des utilisateurs
 * 
 * @author etudiant
 * 
 */
@Repository
public class UserRepository {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserRepository.class);
	
	@Autowired
	CassandraManager manager;

	/**
	 * Constructeur par defaut
	 */
	public UserRepository() {
		super();
	}

	/**
	 * Ajout d'un nouvel utilisateur
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user) {
		if (user == null) {
			LOGGER.info("User parameter is null. No user to add");
			return false;
		}

		String queryText = "INSERT INTO users (username) values (?) IF NOT EXISTS";

		PreparedStatement preparedStatement = manager.getSession().prepare(queryText);

		// Because we use an IF NOT EXISTS clause, we get back a result set with
		// 1 row containing 1 boolean column called "[applied]"
		String username = user.getName();
		ResultSet resultSet = manager.getSession().execute(
				preparedStatement.bind(username));

		// Determine if the user was inserted. If not, throw an exception.
		boolean userGotInserted = resultSet.one().getBool("[applied]");

		if (!userGotInserted) {
			LOGGER.info("User already exists. No user to added");
			return false;
		}

		LOGGER.info("User " + username + " added successfully.");
		return true;
	}

	/**
	 * Suppression d'un utilisateur existant
	 */
	public void deleteUser(User user) {
		String username = user.getName();
		
		String queryText = "DELETE FROM users where username = ?";
		PreparedStatement preparedStatement = manager.getSession().prepare(queryText);
		BoundStatement boundStatement = preparedStatement.bind(username);

		// Delete users with CL = Quorum
		boundStatement.setConsistencyLevel(ConsistencyLevel.QUORUM);
		manager.getSession().execute(boundStatement);
	}

	/**
	 * Look up a user by e-mail address and return a UserDAO object for it
	 * 
	 * @param username
	 *            The username address to search
	 * @return A full UserDAO object
	 */
	public User getUserByName(String username) {
		String queryText = "SELECT * FROM users where username = ?";
		PreparedStatement preparedStatement = manager.getSession().prepare(queryText);
		BoundStatement boundStatement = preparedStatement.bind(username);

		Row userRow = manager.getSession().execute(boundStatement).one();

		if (userRow == null) {
			return null;
		}
		
		String findedUserName = userRow.getString("username");
		return new User(findedUserName);
	}

}
