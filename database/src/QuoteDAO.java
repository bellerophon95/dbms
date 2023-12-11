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
@WebServlet("/quoteDAO")
public class QuoteDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public QuoteDAO() {
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
	
	public boolean delete(String email, String createdOn) throws SQLException {
		String sql = "DELETE FROM `Quote` WHERE (`createdTime` = ? AND `email` = ?);\n"
				+ "";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, createdOn);
		preparedStatement.setString(2, email);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		return rowDeleted;
	}

	public boolean insertQuote(Quote quote) throws SQLException {
		String sql = "INSERT INTO `Quote` (`createdTime`, `email`, `description`, `status`, `modifiedTime`, `requestType`)\n"
				+ "VALUES\n" + "	(?, ?, ?, ?, ?, ?);\n" + "";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, quote.getCreatedTime());
		preparedStatement.setString(2, quote.getEmail());
		preparedStatement.setString(3, quote.getDescription());
		preparedStatement.setString(4, quote.getStatus());
		preparedStatement.setString(5, quote.getModifiedTime());
		preparedStatement.setString(6, quote.getRequestType());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

		preparedStatement.close();
//		statement.close();
		return wasSuccessful;
	}

	public List<Quote> listQuotes() throws SQLException {
		String sql = "SELECT * FROM Quote;";
		List<Quote> quotes = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String modifiedTime = resultSet.getString("modifiedTime");
			String createdTime = resultSet.getString("createdTime");
			String description = resultSet.getString("description");
			String email = resultSet.getString("email");
			String status = resultSet.getString("status");
			String requestType = resultSet.getString("requestType");
			Integer treeID = Integer.valueOf(resultSet.getString("treeID"));

			quotes.add(Quote.builder().email(email).createdTime(createdTime).modifiedTime(modifiedTime)
					.description(description).status(status).requestType(requestType).treeID(treeID).build());

		}

		resultSet.close();
//		statement.close();

		return quotes;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists Quote; ",

				("CREATE TABLE `Quote` (\n" + "  `createdTime` datetime NOT NULL,\n"
						+ "  `email` varchar(50) NOT NULL,\n"
						+ "  `description` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `status` varchar(20) DEFAULT NULL,\n" + "  `modifiedTime` datetime DEFAULT NULL,\n"
						+ "  `requestType` varchar(40) DEFAULT NULL,\n" + "  `agreedPrice` int DEFAULT NULL,\n"
						+ "  `engagements` int DEFAULT NULL,\n" + "  `quoteRequestID` int DEFAULT NULL,\n"
						+ "  `treeID` int DEFAULT NULL,\n" + "  PRIMARY KEY (`createdTime`,`email`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {
				"INSERT INTO `Quote` (`createdTime`, `email`, `description`, `status`, `modifiedTime`, `requestType`, `agreedPrice`, `engagements`, `quoteRequestID`, `treeID`)\n"
						+ "VALUES\n"
						+ "	('0022-04-19 10:34:24', 'jo@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 23, 3, 14, 1),\n"
						+ "	('0022-04-20 10:34:25', 'jo@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 45, 10, 14, 2),\n"
						+ "	('0022-04-22 10:34:24', 'angelo@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 53, 21, 21, 3),\n"
						+ "	('0022-04-22 10:34:24', 'margarita@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 23, 1, 17, 4),\n"
						+ "	('0022-04-24 10:34:24', 'don@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 12, 2, 15, 6),\n"
						+ "	('0022-04-24 10:34:24', 'rudy@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 78, 2, 10, 10),\n"
						+ "	('0022-04-24 10:34:24', 'sophie@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 55, 0, 11, 5),\n"
						+ "	('0022-04-24 10:34:24', 'wallace@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE', 45, 2, 13, 8),\n"
						+ "	('2023-11-07 01:32:05', 'jo@gmail.com', 'Cut tree on front lawn', 'BACKLOG', '2023-11-07 01:32:05', 'RESCHEDULE', 12, 2, 14, 14),\n"
						+ "	('2023-11-07 02:06:56', 'jo@gmail.com', 'cut a tree in the frontyard', 'BACKLOG', '2023-11-07 02:06:56', 'NEGOTIATE_PRICE', 34, 0, 12, 13);\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
