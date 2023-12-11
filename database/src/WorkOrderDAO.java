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
import java.util.Objects;

/**
 * Servlet implementation class Connect
 */
@WebServlet("/wDAO")
public class WorkOrderDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public WorkOrderDAO() {
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

	public boolean insertWorkOrder(WorkOrder workOrder) throws SQLException {

		String sql = "INSERT INTO `WorkOrder` (`email`, `createdOn`, `treeID`, `dueDate`, `description`, `orderStatus`, `completedOn`)\n"
				+ "VALUES\n" + "	(?, ?, ?, ?, ?, ?, ?);\n" + "";
		connect_func();
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		preparedStatement.setString(1, workOrder.getEmail());
		preparedStatement.setString(2, workOrder.getCreatedOn());
		preparedStatement.setInt(3, workOrder.getTreeID());
		preparedStatement.setString(4, workOrder.getDueDate());
		preparedStatement.setString(5, workOrder.getDescription());
		preparedStatement.setString(6, workOrder.getOrderStatus());
		preparedStatement.setString(7, workOrder.getCompletedOn());

		Boolean wasSuccessful = preparedStatement.executeUpdate() > 0;

		preparedStatement.close();
//		statement.close();
		return wasSuccessful;
	}

	public List<WorkOrder> listWorkOrders() throws SQLException {
		String sql = "SELECT * FROM WorkOrder;";
		List<WorkOrder> workOrders = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			workOrders.add(WorkOrder.builder().email(resultSet.getString("email"))
					.completedOn(resultSet.getString("completedOn")).createdOn(resultSet.getString("createdOn"))
					.dueDate(resultSet.getString("dueDate")).description(resultSet.getString("description"))
					.orderStatus(resultSet.getString("orderStatus"))
					.treeID(Integer.valueOf(resultSet.getString("treeID"))).build());

		}

		resultSet.close();

		return workOrders;
	}

	public boolean delete(String email, String createdOn) throws SQLException {
		String sql = "DELETE FROM `WorkOrder` WHERE (`createdOn` = ? AND `email` = ?);\n" + "";
		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, createdOn);
		preparedStatement.setString(2, email);

		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		return rowDeleted;
	}

	/**
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists WorkOrder; ",

				("CREATE TABLE `WorkOrder` (\n"
						+ "  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n"
						+ "  `createdOn` datetime NOT NULL,\n" + "  `treeID` int DEFAULT NULL,\n"
						+ "  `dueDate` datetime DEFAULT NULL,\n" + "  `description` varchar(50) DEFAULT NULL,\n"
						+ "  `orderStatus` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n"
						+ "  `completedOn` datetime DEFAULT NULL,\n" + "  PRIMARY KEY (`email`,`createdOn`)\n"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = {
				"INSERT INTO `WorkOrder` (`email`, `createdOn`, `treeID`, `dueDate`, `description`, `orderStatus`, `completedOn`)\n"
						+ "VALUES\n"
						+ "	('amelia@gmail.com', '2023-02-01 00:00:00', 6, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('amelia@gmail.com', '2023-02-01 00:00:01', 6, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('angelo@gmail.com', '2023-02-01 00:00:00', 5, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('don@gmail.com', '2023-02-01 00:00:00', 18, '2023-02-01 00:00:00', 'Clear approach', 'COMPLETE', '2023-02-01 00:00:00'),\n"
						+ "	('jeannette@gmail.com', '2023-02-01 00:00:00', 5, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('jeannette@gmail.com', '2023-02-01 00:00:43', 4, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('margarita@gmail.com', '2023-02-01 00:00:00', 19, '2023-02-01 00:00:00', 'Clear approach', 'COMPLETE', '2023-02-01 00:00:00'),\n"
						+ "	('rudy@gmail.com', '2023-02-01 00:00:00', 2, '2023-02-01 00:00:00', 'Clear approach', 'COMPLETE', '2023-02-01 00:00:00'),\n"
						+ "	('rudy@gmail.com', '2023-02-01 00:00:01', 8, '2023-02-01 00:00:02', 'Clear approach', 'COMPLETE', '2023-02-01 00:00:00'),\n"
						+ "	('susie@gmail.com', '2023-02-01 00:00:00', 7, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL),\n"
						+ "	('wallace@gmail.com', '2023-02-01 00:00:00', 4, '2023-02-01 00:00:00', 'Clear approach', 'ON_SCHEDULE', NULL);\n"
						+ "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
