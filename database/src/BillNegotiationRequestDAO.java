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
@WebServlet("/billNegotiationRequestDAO")
public class BillNegotiationRequestDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public BillNegotiationRequestDAO() {
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

	public List<BillNegotiationRequest> listClientBillNegotiationRequests(user user) throws SQLException {
		String sql = "SELECT * FROM BillNegotiationRequest WHERE email = ? ";
		List<BillNegotiationRequest> billNegotiationRequests = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user.getEmail());

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			billNegotiationRequests.add(BillNegotiationRequest.builder().createdOn(resultSet.getString("createdOn"))
					.description(resultSet.getString("description"))
					.quoteRequestID(Integer.valueOf(resultSet.getString("quoteRequestID")))
					.email(resultSet.getString("email")).requestType(resultSet.getString("requestType"))
					.status(resultSet.getString("status")).build());

		}

		resultSet.close();
//		statement.close();

		return billNegotiationRequests;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists BillNegotiationRequest; ",

				("CREATE TABLE `BillNegotiationRequest` (\n" + "  `email` varchar(30) NOT NULL,\n"
						+ "  `createdOn` datetime NOT NULL,\n"
						+ "  `description` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `status` varchar(30) DEFAULT NULL,\n" + "  `requestType` varchar(30) DEFAULT NULL,\n"
						+ "  `quoteRequestID` int DEFAULT NULL,\n" + "  PRIMARY KEY (`email`,`createdOn`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {
				"INSERT INTO `BillNegotiationRequest` (`email`, `createdOn`, `description`, `status`, `requestType`, `quoteRequestID`)\n"
						+ "VALUES\n"
						+ "	('amelia@gmail.com', '2023-02-10 23:00:00', 'Negotiate down to 9900', 'PENDING', 'PRICE_NEGOTIATION', 1),\n"
						+ "	('amelia@gmail.com', '2023-02-12 00:01:00', 'Negotiate down to 80', 'PENDING', 'PRICE_NEGOTIATION', 4),\n"
						+ "	('angelo@gmail.com', '2023-02-01 00:00:00', 'Negotiate down to 120', 'PENDING', 'PRICE_NEGOTIATION', 3),\n"
						+ "	('jo@gmail.com', '2023-02-05 00:00:01', 'Adjust for damages, Remarks:- Fence damage', 'ACCEPTED', 'DAMAGES', 3),\n"
						+ "	('jo@gmail.com', '2023-02-13 00:03:00', 'Negotiate down to 120', 'PENDING', 'PRICE_NEGOTIATION', 4),\n"
						+ "	('margarita@gmail.com', '2023-02-21 00:17:00', 'Negotiate down to 750', 'PENDING', 'PRICE_NEGOTIATION', 6),\n"
						+ "	('rudy@gmail.com', '2022-12-01 00:00:00', '12 Month Suspended Payment', 'PENDING', 'FINANCIAL_ARRANGEMENT', 9),\n"
						+ "	('susie@gmail.com', '2023-02-01 00:00:00', 'Negotiate down to 120', 'PENDING', 'PRICE_NEGOTIATION', 6),\n"
						+ "	('wallace@gmail.com', '2023-02-20 00:04:00', 'Negotiate down to 140', 'PENDING', 'PRICE_NEGOTIATION', 5);\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
