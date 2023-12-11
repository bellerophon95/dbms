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
@WebServlet("/billDAO")
public class BillDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public BillDAO() {
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

	public boolean delete(String email) throws SQLException {
		String sql = "DELETE FROM User WHERE email = ?";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, email);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		return rowDeleted;
	}

	public List<Bill> listClientBills(user user) throws SQLException {
		String sql = "SELECT * FROM Bill WHERE email = ?";
		List<Bill> bills = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user.getEmail());

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			bills.add(Bill.builder().amount(Integer.valueOf(resultSet.getString("amount")))
					.dueDate(resultSet.getString("dueDate")).email(resultSet.getString("email"))
					.paymentStatus(resultSet.getString("paymentStatus")).raisedOn(resultSet.getString("raisedOn"))
					.settledOn(resultSet.getString("settledOn")).build());

		}

		resultSet.close();

		return bills;
	}

	public boolean insertBill(Bill bill) throws SQLException {
		String sql = "INSERT INTO `Bill` (`email`, `raisedOn`, `amount`, `settledOn`, `paymentStatus`, `dueDate`) VALUES\n"
				+ "(?, ?, ?, ?, ?, ?);\n" + "";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, bill.getEmail());
		preparedStatement.setString(2, bill.getRaisedOn());
		preparedStatement.setInt(3, bill.getAmount());
		preparedStatement.setString(4, bill.getSettledOn());
		preparedStatement.setString(5, bill.getPaymentStatus());
		preparedStatement.setString(6, bill.getDueDate());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

		preparedStatement.close();
//		statement.close();
		return wasSuccessful;
	}

	public List<Bill> listBills() throws SQLException {
		String sql = "SELECT * FROM Bill;";
		List<Bill> bills = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			bills.add(Bill.builder().amount(Integer.valueOf(resultSet.getString("amount")))
					.dueDate(resultSet.getString("dueDate")).email(resultSet.getString("email"))
					.paymentStatus(resultSet.getString("paymentStatus")).raisedOn(resultSet.getString("raisedOn"))
					.settledOn(resultSet.getString("settledOn")).build());

		}

		resultSet.close();
//		statement.close();

		return bills;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists Bill; ",

				("CREATE TABLE `Bill` (\n" + "  `email` varchar(20) NOT NULL,\n" + "  `raisedOn` datetime NOT NULL,\n"
						+ "  `amount` int DEFAULT NULL,\n" + "  `settledOn` datetime DEFAULT NULL,\n"
						+ "  `paymentStatus` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `dueDate` datetime DEFAULT NULL,\n" + "  PRIMARY KEY (`email`,`raisedOn`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {
				"INSERT INTO `Bill` (`email`, `raisedOn`, `amount`, `settledOn`, `paymentStatus`, `dueDate`)\n"
						+ "VALUES\n"
						+ "	('amelia@gmail.com', '2023-01-22 00:00:02', 120, '2023-01-28 16:01:00', 'PAID', '2023-01-30 00:00:00'),\n"
						+ "	('angelo@gmail.com', '2023-01-22 00:00:02', 120, NULL, 'UNPAID', '2023-01-30 00:00:00'),\n"
						+ "	('don@gmail.com', '2023-01-22 00:00:02', 120, '2023-01-22 00:21:02', 'PAID', '2023-01-30 00:00:00'),\n"
						+ "	('jeannette@gmail.com', '2023-01-22 00:00:00', 250, '2023-01-26 16:01:00', 'PAID', '2023-01-30 00:00:00'),\n"
						+ "	('jo@gmail.com', '2023-01-22 00:00:02', 120, '2023-01-25 00:00:02', 'PAID', '2023-01-30 00:00:00'),\n"
						+ "	('jo@gmail.com', '2023-01-22 00:00:03', 120, NULL, 'UNPAID', '2023-01-30 00:00:00'),\n"
						+ "	('margarita@gmail.com', '2023-01-22 00:00:00', 250, NULL, 'UNPAID', '2023-01-30 00:00:00'),\n"
						+ "	('susie@gmail.com', '2023-01-22 00:00:00', 250, NULL, 'UNPAID', '2023-01-30 00:00:00'),\n"
						+ "	('susie@gmail.com', '2023-01-22 00:00:02', 120, NULL, 'UNPAID', '2023-01-30 00:00:00'),\n"
						+ "	('wallace@gmail.com', '2023-01-22 00:00:02', 120, NULL, 'UNPAID', '2023-01-30 00:00:00');\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
