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
		preparedStatement.setString(5, updatedQuoteRequest.getUserComment());
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
			quoteRequests.add(QuoteRequest.builder().treeID(Integer.valueOf(resultSet.getString("treeID")))
					.quoteRequestID(Integer.valueOf(resultSet.getString("quoteRequestID")))
					.email(resultSet.getString("email")).createdOn(resultSet.getString("createdOn"))
					.description(resultSet.getString("description")).requestType(resultSet.getString("requestType"))
					.comment(resultSet.getString("comment")).status(resultSet.getString("status"))
					.userComment(resultSet.getString("userComment"))
					.proposedPrice(Integer.valueOf(resultSet.getString("proposedPrice"))).build());

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
			quoteRequests.add(QuoteRequest.builder().treeID(Integer.valueOf(resultSet.getString("treeID")))
					.quoteRequestID(Integer.valueOf(resultSet.getString("quoteRequestID")))
					.email(resultSet.getString("email")).createdOn(resultSet.getString("createdOn"))
					.description(resultSet.getString("description")).requestType(resultSet.getString("requestType"))
					.status(resultSet.getString("status")).comment(resultSet.getString("comment"))
					.userComment(resultSet.getString("userComment")).proposedPrice(resultSet.getInt("proposedPrice"))
					.build());

		}

		resultSet.close();
//		statement.close();

		return quoteRequests;
	}

	public Integer getMaxQuoteRequestID() throws SQLException {
		String sql = "SELECT max(quoteRequestID) as maxQuoteRequestID FROM QuoteRequest";

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("maxQuoteRequestID");

		}

		resultSet.close();
//		statement.close();

		return -1;
	}

	public boolean insertQuoteRequest(QuoteRequest quoteRequest) throws SQLException {
		String sql = "INSERT INTO `QuoteRequest` (`email`, `createdOn`, `description`, `status`, `requestType`, `comment`, `userComment`, `treeID`, `proposedPrice`, `quoteRequestID`)\n"
				+ "VALUES\n" + "	(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n" + "";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, quoteRequest.getEmail());
		preparedStatement.setString(2, quoteRequest.getCreatedOn());
		preparedStatement.setString(3, quoteRequest.getDescription());
		preparedStatement.setString(4, quoteRequest.getStatus());
		preparedStatement.setString(5, quoteRequest.getRequestType());
		preparedStatement.setString(6, quoteRequest.getComment());
		preparedStatement.setString(7, quoteRequest.getUserComment());
		preparedStatement.setInt(8, quoteRequest.getTreeID());
		preparedStatement.setInt(9, quoteRequest.getProposedPrice());
		preparedStatement.setInt(10, quoteRequest.getQuoteRequestID());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

		preparedStatement.close();
//		statement.close();
		return wasSuccessful;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists QuoteRequest; ",

				("CREATE TABLE `QuoteRequest` (\n" + "  `email` varchar(30) NOT NULL,\n"
						+ "  `createdOn` datetime NOT NULL,\n" + "  `quoteRequestID` int NOT NULL,\n"
						+ "  `treeID` int NOT NULL,\n"
						+ "  `description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `requestType` varchar(40) DEFAULT NULL,\n" + "  `comment` varchar(500) DEFAULT NULL,\n"
						+ "  `userComment` varchar(50) DEFAULT NULL,\n" + "  `proposedPrice` int DEFAULT NULL,\n"
						+ "  PRIMARY KEY (`quoteRequestID`,`treeID`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {
				"INSERT INTO `QuoteRequest` (`email`, `createdOn`, `quoteRequestID`, `treeID`, `description`, `status`, `requestType`, `comment`, `userComment`, `proposedPrice`)\n"
						+ "VALUES\n"
						+ "	('amelia@gmail.com', '2023-01-02 23:51:00', 10, 10, 'ChangeTime to Tue 26. REMARKS:- Schedule free on 29', 'REJECTED', 'ANCILLARY_TASK', 'NO_COMMENTS_YET', 'NO_USER_COMMENTS_YET', 23),\n"
						+ "	('rudy@gmail.com', '2023-01-02 23:51:00', 13, 4, 'change location to Downtown Dumpyard', 'UNDER_CONSIDERATION', 'RELOCATION', 'NO_COMMENTS_YET', 'NO_USER_COMMENTS_YET', 34),\n"
						+ "	('jo@gmail.com', '2023-01-01 23:51:01', 14, 5, 'ChangeTime to Mon 23', 'PENDING', 'RESCHEDULE', 'Need more time to reconsider until Feburary 8', 'NO_USER_COMMENTS_YET', 44),\n"
						+ "	('jo@gmail.com', '2023-01-01 23:51:02', 14, 6, 'ChangeLocation to Kirby Street', 'UNDER_CONSIDERATION', 'RELOCATE', 'Need more time to reconsider until Feburary 8', 'NO_USER_COMMENTS_YET', 12),\n"
						+ "	('jo@gmail.com', '2023-01-03 23:51:03', 14, 7, 'ChangeTime to Mon 24', 'PENDING', 'RELOCATE', 'NO_COMMENTS_YET', 'NO_USER_COMMENTS_YET', 24),\n"
						+ "	('jo@gmail.com', '2023-11-07 01:33:46', 17, 1, 'Cut tree on porch', 'PENDING', 'RESCHEDULE', 'Will do it', 'Noegotiate for 3 dollars', 12),\n"
						+ "	('jo@gmail.com', '2023-11-07 01:33:46', 18, 2, 'Cut tree on porch', 'PENDING', 'RESCHEDULE', 'Will do it', 'Noegotiate for 3 dollars', 45),\n"
						+ "	('jo@gmail.com', '2023-12-04 15:29:09', 19, 32, 'gweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 12),\n"
						+ "	('jo@gmail.com', '2023-12-04 15:29:50', 20, 33, 'fgweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 12),\n"
						+ "	('jo@gmail.com', '2023-12-04 15:44:55', 21, 36, 'wegweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 4124),\n"
						+ "	('jo@gmail.com', '2023-12-04 15:45:41', 22, 37, 'wegweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 4124),\n"
						+ "	('jo@gmail.com', '2023-12-04 15:45:49', 23, 38, 'wegweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 4124),\n"
						+ "	('jo@gmail.com', '2023-12-04 20:12:01', 24, 39, 'gwegwegweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 4124),\n"
						+ "	('jo@gmail.com', '2023-12-04 20:19:44', 25, 40, 'gweg', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'NO_USER_COMMENTS_YET', 4214),\n"
						+ "	('jo@gmail.com', '2023-12-04 20:20:21', 26, 41, 'erhhrwerh', 'PENDING', 'RESCHEDULE', 'NO_COMMENT_YET', 'can do', 547);\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
