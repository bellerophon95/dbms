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
@WebServlet("/TreeDAO")
public class TreeDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public TreeDAO() {
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

	public Integer insert(Tree tree) throws SQLException {
//		connect_func("root", "pass1234");
		connect_func();
		String sql = "INSERT INTO `Tree` (`height`, `address`, `distToHouse`) VALUES (?, ?, ?);";
		ResultSet resultSet;
		int insertStatus;

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, tree.getHeight());
		preparedStatement.setString(2, tree.getAddress());
		preparedStatement.setInt(3, tree.getDistToHouse());

		insertStatus = preparedStatement.executeUpdate();

		if (insertStatus > 0) {
			resultSet = ((PreparedStatement) connect.prepareStatement("SELECT MAX(treeID) as maxTreeID FROM Tree;"))
					.executeQuery();

			return resultSet.next() ? resultSet.getInt("maxTreeID") : 0;

		}
		preparedStatement.close();

		return insertStatus;
	}

	public void init() throws SQLException, FileNotFoundException, IOException {
		connect_func();
		statement = (Statement) connect.createStatement();

		String[] INITIAL = { "drop table if exists Tree; ",

				("CREATE TABLE `Tree` (\n" + "  `height` int DEFAULT NULL,\n"
						+ "  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n"
						+ "  `distToHouse` int NOT NULL,\n"
						+ "  `treeID` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,\n"
						+ "  PRIMARY KEY (`treeID`)\n"
						+ ") ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;") };

		String[] TUPLES = { "INSERT INTO `Tree` (`height`, `address`, `distToHouse`, `treeID`)\n" + "VALUES\n"
				+ "	(130, '23 Gavline Ave, Brookeford, MI', 1002, 0000000012),\n"
				+ "	(145, '23 Langdoh Ave, Brookeford, MI', 1003, 0000000013),\n"
				+ "	(170, '3445 Gutentag German Lager Factory, Pritzger, MN', 2001, 0000000014),\n"
				+ "	(160, '3445 Irish Avenue, Twin Cities, MN', 2001, 0000000015),\n"
				+ "	(186, '3445 Italian Avenue, Bronx, NY', 3002, 0000000016),\n"
				+ "	(120, '345 Chinatown Noodles, Brookeford, FL', 4011, 0000000017),\n"
				+ "	(190, '45 Chief drive', 1021, 0000000018),\n"
				+ "	(190, '45 Hick Town, Scoville, MN', 2001, 0000000019),\n"
				+ "	(140, '456 Little India Curry House, MN', 2221, 0000000020),\n"
				+ "	(130, '456 San Avocado Mexicantown Eatery, MN', 1001, 0000000021),\n"
				+ "	(190, 'MLK Street, Compton, CA', 3002, 0000000022);\n" + "" };
		// for loop to put these in database
		for (int i = 0; i < INITIAL.length; i++)
			statement.execute(INITIAL[i]);
		for (int i = 0; i < TUPLES.length; i++)
			statement.execute(TUPLES[i]);
		disconnect();
	}

}
