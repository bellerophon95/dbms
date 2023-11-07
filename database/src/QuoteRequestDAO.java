import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Connect
 */
@WebServlet("/quoteRequestDAO")
public class QuoteRequestDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public QuoteRequestDAO() {
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void connect_func() throws SQLException {
		// uses default connection to the database
		if (connect == null || connect.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			connect = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234&serverTimezone=UTC");
			System.out.println(connect);
		}
	}

	public boolean database_login(String email, String password) throws SQLException {
		try {
			connect_func("root", "pass1234");
			String sql = "select * from user where email = ?";
			preparedStatement = connect.prepareStatement(sql);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.out.println("failed login");
			return false;
		}
	}

	// connect to the database
	public void connect_func(String username, String password) throws SQLException {
		if (connect == null || connect.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
					+ "useSSL=false&user=" + username + "&password=" + password + "&serverTimezone=UTC");
			System.out.println(connect);
		}
	}

	protected void disconnect() throws SQLException {
		if (connect != null && !connect.isClosed()) {
			connect.close();
		}
	}

	;

	public boolean deleteQuoteRequest(QuoteRequest toBeDeleteQuoteRequest) throws SQLException {

		String sql = "DELETE FROM `QuoteRequest` WHERE (`email` = ? AND `createdOn` = ?)";

		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, toBeDeleteQuoteRequest.getEmail());
		preparedStatement.setString(2, toBeDeleteQuoteRequest.getCreatedOn());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

//		statement.close();
		return wasSuccessful;
	}

	public boolean updateQuoteRequest(QuoteRequest updatedQuoteRequest) throws SQLException {

		String sql = "UPDATE `QuoteRequest` SET `requestType` = ?, `description` = ?, `status` = ?, `comment` = ?, `userComment` = ? "
				+ "WHERE `email` = ? AND `createdOn` = ?;\n";

		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, updatedQuoteRequest.getRequestType());
		preparedStatement.setString(2, updatedQuoteRequest.getDescription());
		preparedStatement.setString(3, updatedQuoteRequest.getStatus());
		preparedStatement.setString(4, updatedQuoteRequest.getComment());
		preparedStatement.setString(5, updatedQuoteRequest.getUserComment() );
		preparedStatement.setString(6, updatedQuoteRequest.getEmail());
		preparedStatement.setString(7, updatedQuoteRequest.getCreatedOn());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

//		statement.close();
		return wasSuccessful;
	}

	public List<QuoteRequest> listClientQuoteRequests(user user) throws SQLException {
		String sql = "SELECT * FROM QuoteRequest WHERE `email` = ?";
		List<QuoteRequest> quoteRequests = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user.getEmail());

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			quoteRequests.add(QuoteRequest.builder().email(resultSet.getString("email"))
					.createdOn(resultSet.getString("createdOn")).description(resultSet.getString("description"))
					.requestType(resultSet.getString("requestType")).comment(resultSet.getString("comment"))
					.status(resultSet.getString("status")).userComment(resultSet.getString("userComment")).build());

		}

		resultSet.close();
//		statement.close();

		return quoteRequests;
	}

	public List<QuoteRequest> listQuoteRequests() throws SQLException {
		String sql = "SELECT * FROM QuoteRequest";
		List<QuoteRequest> quoteRequests = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			quoteRequests.add(QuoteRequest.builder().email(resultSet.getString("email"))
					.createdOn(resultSet.getString("createdOn")).description(resultSet.getString("description"))
					.requestType(resultSet.getString("requestType")).status(resultSet.getString("status"))
					.comment(resultSet.getString("comment")).userComment(resultSet.getString("userComment")).build());

		}

		resultSet.close();
//		statement.close();

		return quoteRequests;
	}

	public boolean insertQuoteRequest(QuoteRequest quoteRequest) throws SQLException {
		String sql = "INSERT INTO `QuoteRequest` (`email`, `createdOn`, `description`, `status`, `requestType`, `comment`, `userComment`)\n"
				+ "VALUES\n" + "	(?, ?, ?, ?, ?, ?, ? );\n" + "";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, quoteRequest.getEmail());
		preparedStatement.setString(2, quoteRequest.getCreatedOn());
		preparedStatement.setString(3, quoteRequest.getDescription());
		preparedStatement.setString(4, quoteRequest.getStatus());
		preparedStatement.setString(5, quoteRequest.getRequestType());
		preparedStatement.setString(6, quoteRequest.getComment());
		preparedStatement.setString(7, quoteRequest.getUserComment());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

		preparedStatement.close();
//		statement.close();
		return wasSuccessful;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists QuoteRequest; ",

				("CREATE TABLE `QuoteRequest` (\n"
						+ "  `email` varchar(30) NOT NULL,\n"
						+ "  `createdOn` datetime NOT NULL,\n"
						+ "  `description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `requestType` varchar(40) DEFAULT NULL,\n"
						+ "  `comment` varchar(500) DEFAULT NULL,\n"
						+ "  `userComment` varchar(50) DEFAULT NULL,\n"
						+ "  PRIMARY KEY (`email`,`createdOn`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;;") };

		String[] TUPLES = {

				"INSERT INTO `QuoteRequest` (`email`, `createdOn`, `description`, `status`, `requestType`, `comment`, `userComment`)\n"
						+ "VALUES\n"
						+ "	('jo@gmail.com', '2023-01-01 23:51:01', 'ChangeTime to Mon 23', 'UNDER_CONSIDERATION', 'RESCHEDULE', 'Need more time to reconsider until Feburary 8', 'NO_USER_COMMENTS_YET'),\n"
						+ "	('jo@gmail.com', '2023-01-01 23:51:02', 'ChangeLocation to Kirby Street', 'UNDER_CONSIDERATION', 'RELOCATE','Need more time to reconsider until Feburary 8','NO_USER_COMMENTS_YET'),\n"
						+ "	('jo@gmail.com', '2023-01-03 23:51:03', 'ChangeTime to Mon 24', 'UNDER_CONSIDERATION', 'RELOCATE','NO_COMMENTS_YET','NO_USER_COMMENTS_YET'),\n"
						+ "	('c@gmail.com', '2023-01-01 23:51:00', 'Give 15% discount. REMARKS:- Foliage dense, will consider 8%, refile', 'REFILE', 'NEGOTIATE_PRICE','Refile with new quote request','NO_USER_COMMENTS_YET'),\n"
						+ "	('e@gmail.com', '2023-01-02 23:51:00', 'ChangeTime to Tue 26. REMARKS:- Schedule free on 29', 'REJECTED', 'ANCILLARY_TASK','NO_COMMENTS_YET','NO_USER_COMMENTS_YET'),\n"
						+ "	('f@gmail.com', '2023-01-02 23:51:00', 'change location to Devil\\'s Inn', 'UNDER_CONSIDERATION', 'RELOCATION','Need more time to reconsider until Feburary 8','NO_USER_COMMENTS_YET'),\n"
						+ "	('g.gmail.com', '2023-01-02 23:51:00', 'change location to Mount Fishmore', 'UNDER_CONSIDERATION', 'RELOCATION','NO_COMMENTS_YET','NO_USER_COMMENTS_YET'),\n"
						+ "	('h.gmail.com', '2023-01-02 23:51:00', 'change location to Downtown Dumpyard', 'UNDER_CONSIDERATION', 'RELOCATION','NO_COMMENTS_YET','NO_USER_COMMENTS_YET');\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
