package fr.esiee.pic.esieeLabProductionCore.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import fr.esiee.pic.esieeLabProductionCore.domain.Artist;
import fr.esiee.pic.esieeLabProductionCore.repository.shared.CassandraRepository;

/**
 * Dépôt des artistes
 * 
 * @author etudiant
 * 
 */
@Repository
public class ArtistRepository extends CassandraRepository {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArtistRepository.class);

	/**
	 * Constructeur par defaut
	 */
	public ArtistRepository() {
		super();
	}

	/**
	 * Ajout d'un nouvel artiste
	 * 
	 * @param artist
	 * @return
	 */
	public boolean addArtist(Artist artist) {
		if (artist == null) {
			LOGGER.info("Artist parameter is null. No artist to add");
			return false;
		}

		String queryText = "INSERT INTO artists_by_first_letter (first_letter, artist) values (?, ?) IF NOT EXISTS";

		PreparedStatement preparedStatement = getSession().prepare(queryText);

		// Because we use an IF NOT EXISTS clause, we get back a result set with
		// 1 row containing 1 boolean column called "[applied]"
		String artistname = artist.getName();

		String firstLetter = retrieveFirstLetter(artistname);

		ResultSet resultSet = getSession().execute(
				preparedStatement.bind(firstLetter, artistname));

		// Determine if the artist was inserted. If not, throw an exception.
		boolean artistGotInserted = resultSet.one().getBool("[applied]");

		if (!artistGotInserted) {
			LOGGER.info("Artist already exists. No artist to added");
			return false;
		}

		LOGGER.info("Artist " + artistname + " added successfully.");
		return true;
	}

	/**
	 * @param value
	 * @return
	 */
	private static String retrieveFirstLetter(String value) {
		// Extraction of the first letter
		String firstLetter = value.trim().substring(0, 1);
		firstLetter = firstLetter.toLowerCase();
		return firstLetter;
	}

	/**
	 * Suppression d'un artiste existant
	 */
	public void deleteArtist(Artist artist) {
		String artistname = artist.getName();
		String firstLetter = retrieveFirstLetter(artistname);

		String queryText = "DELETE FROM artists_by_first_letter where first_letter = ? and artist = ?";
		PreparedStatement preparedStatement = getSession().prepare(queryText);
		BoundStatement boundStatement = preparedStatement.bind(firstLetter,
				artistname);

		// Delete artists with CL = Quorum
		boundStatement.setConsistencyLevel(ConsistencyLevel.QUORUM);
		getSession().execute(boundStatement);
	}

	/**
	 * Look up a artist by name first letter
	 * 
	 */
	public static List<Artist> listArtistByLetter(String firstLetter,
			boolean desc) {

		String queryText = "SELECT * FROM artists_by_first_letter WHERE first_letter = ?"
				+ (desc ? " ORDER BY artist DESC" : "");
		PreparedStatement preparedStatement = getSession().prepare(queryText);
		BoundStatement boundStatement = preparedStatement.bind(firstLetter);

		ResultSet results = getSession().execute(boundStatement);

		List<Artist> artists = new ArrayList<>();

		// iteration sur le resultSet
		for (Row row : results) {
			String artistName = row.getString("artist");
			Artist artist = new Artist(artistName);
			artists.add(artist);
		}

		return artists;
	}
}
