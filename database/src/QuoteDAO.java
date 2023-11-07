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

	public List<user> listAllUsers() throws SQLException {
		List<user> listUser = new ArrayList<user>();
		String sql = "SELECT * FROM User";
		connect_func();
		statement = (Statement) connect.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			String email = resultSet.getString("email");
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");
			String password = resultSet.getString("password");
			String birthday = resultSet.getString("birthday");
			String adress_street_num = resultSet.getString("adress_street_num");
			String adress_street = resultSet.getString("adress_street");
			String adress_city = resultSet.getString("adress_city");
			String adress_state = resultSet.getString("adress_state");
			String adress_zip_code = resultSet.getString("adress_zip_code");
			int cash_bal = resultSet.getInt("cash_bal");
			int PPS_bal = resultSet.getInt("PPS_bal");
			String role = resultSet.getString("role");

			user users = user.builder().email(email).firstName(firstName).lastName(lastName).password(password)
					.birthday(birthday).adress_street_num(adress_street_num).adress_street(adress_street)
					.adress_city(adress_city).adress_state(adress_state).adress_zip_code(adress_zip_code)
					.cash_bal(cash_bal).PPS_bal(PPS_bal).role(role).build();
//            user users = new user(email,firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code, cash_bal,PPS_bal);
			listUser.add(users);
		}
		resultSet.close();
		disconnect();
		return listUser;
	}

	protected void disconnect() throws SQLException {
		if (connect != null && !connect.isClosed()) {
			connect.close();
		}
	}

	public void insert(user users) throws SQLException {
		connect_func("root", "pass1234");
		String sql = "insert into User(email, firstName, lastName, password, birthday,adress_street_num, adress_street,adress_city,adress_state,adress_zip_code,cash_bal,PPS_bal, role) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getFirstName());
		preparedStatement.setString(3, users.getLastName());
		preparedStatement.setString(4, users.getPassword());
		preparedStatement.setString(5, users.getBirthday());
		preparedStatement.setString(6, users.getAdress_street_num());
		preparedStatement.setString(7, users.getAdress_street());
		preparedStatement.setString(8, users.getAdress_city());
		preparedStatement.setString(9, users.getAdress_state());
		preparedStatement.setString(10, users.getAdress_zip_code());
		preparedStatement.setInt(11, users.getCash_bal());
		preparedStatement.setInt(12, users.getPPS_bal());
		preparedStatement.setString(13, users.getRole());

		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	public boolean delete(String email) throws SQLException {
		String sql = "DELETE FROM User WHERE email = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, email);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		return rowDeleted;
	}

	public boolean update(user users) throws SQLException {
		String sql = "update User set firstName=?, lastName =?,password = ?,birthday=?,adress_street_num =?, adress_street=?,adress_city=?,adress_state=?,adress_zip_code=?, cash_bal=?, PPS_bal =? where email = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getFirstName());
		preparedStatement.setString(3, users.getLastName());
		preparedStatement.setString(4, users.getPassword());
		preparedStatement.setString(5, users.getBirthday());
		preparedStatement.setString(6, users.getAdress_street_num());
		preparedStatement.setString(7, users.getAdress_street());
		preparedStatement.setString(8, users.getAdress_city());
		preparedStatement.setString(9, users.getAdress_state());
		preparedStatement.setString(10, users.getAdress_zip_code());
		preparedStatement.setInt(11, users.getCash_bal());
		preparedStatement.setInt(12, users.getPPS_bal());

		boolean rowUpdated = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		return rowUpdated;
	}

	public user getUser(String email) throws SQLException {
		user user = null;
		String sql = "SELECT * FROM User WHERE email = ?";

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, email);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");
			String password = resultSet.getString("password");
			String birthday = resultSet.getString("birthday");
			String adress_street_num = resultSet.getString("adress_street_num");
			String adress_street = resultSet.getString("adress_street");
			String adress_city = resultSet.getString("adress_city");
			String adress_state = resultSet.getString("adress_state");
			String adress_zip_code = resultSet.getString("adress_zip_code");
			int cash_bal = resultSet.getInt("cash_bal");
			int PPS_bal = resultSet.getInt("PPS_bal");
			user = new user(email, firstName, lastName, password, birthday, adress_street_num, adress_street,
					adress_city, adress_state, adress_zip_code, cash_bal, PPS_bal);
		}

		resultSet.close();
		statement.close();

		return user;
	}

	public boolean checkEmail(String email) throws SQLException {

		boolean checks = false;
		String sql = "SELECT * FROM User WHERE email = ?";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, email);
		ResultSet resultSet = preparedStatement.executeQuery();

		System.out.println(checks);

		if (resultSet.next()) {
			checks = true;
		}

		System.out.println(checks);
		return checks;
	}

	public boolean checkAddress(String adress_street_num, String adress_street, String adress_city, String adress_state,
			String adress_zip_code) throws SQLException {
		boolean checks = false;
		String sql = "SELECT * FROM User WHERE adress_street_num = ? AND adress_street = ? AND adress_city = ? AND adress_state = ? AND adress_zip_code = ?";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, adress_street_num);
		preparedStatement.setString(2, adress_street);
		preparedStatement.setString(3, adress_city);
		preparedStatement.setString(4, adress_state);
		preparedStatement.setString(5, adress_zip_code);
		ResultSet resultSet = preparedStatement.executeQuery();

		System.out.println(checks);

		if (resultSet.next()) {
			checks = true;
		}

		System.out.println(checks);
		return checks;
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
		String sql = "SELECT * FROM Quote";
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

			quotes.add(Quote.builder().email(email).createdTime(createdTime).modifiedTime(modifiedTime)
					.description(description).status(status).requestType(requestType).build());

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
						+ "  `requestType` varchar(40) DEFAULT NULL,\n" + "  PRIMARY KEY (`createdTime`,`email`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {

				"INSERT INTO `Quote` (`createdTime`, `email`, `description`, `status`, `modifiedTime`, `requestType`)\n"
						+ "VALUES\n"
						+ "	('0022-04-19 10:34:24', 'jo@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-20 10:34:25', 'jo@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-22 10:34:24', 'c@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-22 10:34:24', 'd@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-24 10:34:24', 'f@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-24 10:34:24', 'g@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-24 10:34:24', 'h@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE'),\n"
						+ "	('0022-04-24 10:34:24', 'y@gmail.com', 'Cut tree', 'BACKLOG', '0022-04-22 10:34:24', 'RESCHEDULE');\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
