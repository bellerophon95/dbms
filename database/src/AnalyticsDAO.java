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
@WebServlet("/analyticsDAO")
public class AnalyticsDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public AnalyticsDAO() {
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

	public List<user> badClients() throws SQLException {
		String sql = "SELECT * FROM USER WHERE email IN (SELECT email FROM Bill WHERE email NOT IN (SELECT email FROM Bill WHERE settledOn < dueDate AND paymentStatus = 'PAID'))";
		List<user> users = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String email = resultSet.getString("email");
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");

			users.add(user.builder().email(email).firstName(firstName).lastName(lastName).build());

		}

		resultSet.close();
		return users;
	}

	public List<Bill> overdueBills() throws SQLException {
		String sql = "SELECT * FROM BILL WHERE paymentStatus = \"UNPAID\" AND DATEDIFF(NOW(), raisedOn) > 7;";
		List<Bill> bills = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			bills.add(Bill.builder().amount(resultSet.getInt("amount")).dueDate(resultSet.getString("dueDate"))
					.email(resultSet.getString("email")).paymentStatus(resultSet.getString("paymentStatus"))
					.settledOn(resultSet.getString("settledOn")).raisedOn(resultSet.getString("raisedOn")).build());

		}

		resultSet.close();

		return bills;
	}

	public List<user> easyClients() throws SQLException {
		String sql = "SELECT * FROM User WHERE email IN (SELECT email FROM Quote WHERE engagements = 0);";
		List<user> users = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			users.add(user.builder().email(resultSet.getString("email")).firstName(resultSet.getString("firstName"))
					.lastName(resultSet.getString("lastName")).build());

		}

		resultSet.close();

		return users;
	}

	public List<Quote> acceptedQuotesWithOneTree() throws SQLException {
		String sql = "SELECT * FROM Quote WHERE quoteRequestID IN (SELECT quoteRequestID FROM QuoteRequest GROUP BY quoteRequestID HAVING COUNT(treeID) = 1);";
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
			Integer treeID = resultSet.getInt("treeID");
			Integer quoteRequestID = resultSet.getInt("quoteRequestID");

			quotes.add(Quote.builder().email(email).createdTime(createdTime).modifiedTime(modifiedTime)
					.description(description).status(status).requestType(requestType).treeID(treeID)
					.quoteRequestID(quoteRequestID).build());

		}

		resultSet.close();

		return quotes;
	}

	public List<Quote> acceptedQuotesWithMoreThanOneTree() throws SQLException {
		String sql = "SELECT * FROM Quote WHERE quoteRequestID IN (SELECT quoteRequestID FROM QuoteRequest GROUP BY quoteRequestID HAVING COUNT(treeID) > 1);";
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
			Integer treeID = resultSet.getInt("treeID");
			Integer quoteRequestID = resultSet.getInt("quoteRequestID");

			quotes.add(Quote.builder().email(email).createdTime(createdTime).modifiedTime(modifiedTime)
					.description(description).status(status).requestType(requestType).treeID(treeID)
					.quoteRequestID(quoteRequestID).build());

		}

		resultSet.close();

		return quotes;
	}

	public List<user> prospectiveClients() throws SQLException {
		String sql = "SELECT * FROM USER WHERE email IN (SELECT email FROM Quote WHERE email NOT IN (SELECT email FROM WorkOrder));";
		List<user> users = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			users.add(user.builder().email(resultSet.getString("email")).firstName(resultSet.getString("firstName"))
					.lastName(resultSet.getString("lastName")).build());

		}

		resultSet.close();

		return users;
	}

	public List<UsersVsAmountDueGroupBy> usersVsAmountDue() throws SQLException {
		String sql = "SELECT User.email, User.firstName, User.lastName, SUM(AMOUNT) AS amountDue from Bill, User WHERE paymentStatus = \"UNPAID\" AND User.email=Bill.email GROUP BY email;";
		List<UsersVsAmountDueGroupBy> usersVsAmountDueGroupByItems = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			usersVsAmountDueGroupByItems.add(UsersVsAmountDueGroupBy.builder().email(resultSet.getString("email"))
					.firstName(resultSet.getString("firstName")).lastName(resultSet.getString("lastName"))
					.amountDue(resultSet.getInt("amountDue")).build());

		}

		resultSet.close();

		return usersVsAmountDueGroupByItems;
	}

	public List<UsersVsAmountPaidGroupBy> usersVsAmountPaid() throws SQLException {
		String sql = "SELECT User.email, User.firstName, User.lastName, SUM(AMOUNT) AS amountPaid from Bill, User WHERE paymentStatus = \"PAID\" AND User.email = Bill.email GROUP BY email;";
		List<UsersVsAmountPaidGroupBy> usersVsAmountPaidGroupByItems = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			usersVsAmountPaidGroupByItems.add(UsersVsAmountPaidGroupBy.builder().email(resultSet.getString("email"))
					.firstName(resultSet.getString("firstName")).lastName(resultSet.getString("lastName"))
					.amountPaid(resultSet.getInt("amountPaid")).build());

		}

		resultSet.close();

		return usersVsAmountPaidGroupByItems;
	}

	public List<UsersVsTreesAlreadyCutGroupBy> usersVsTreesAlreadyCut() throws SQLException {
		String sql = "SELECT User.email, User.firstName, User.lastName, COUNT(Tree.treeID) as treesAlreadyCut FROM Tree, WorkOrder, User WHERE orderStatus=\"COMPLETE\" and Tree.treeID = WorkOrder.treeID AND User.email= WorkOrder.email GROUP BY email;";
		List<UsersVsTreesAlreadyCutGroupBy> usersVsTreesAlreadyCutGroupByItems = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			usersVsTreesAlreadyCutGroupByItems
					.add(UsersVsTreesAlreadyCutGroupBy.builder().email(resultSet.getString("email"))
							.firstName(resultSet.getString("firstName")).lastName(resultSet.getString("lastName"))
							.treesAlreadyCut(resultSet.getInt("treesAlreadyCut")).build());

		}

		resultSet.close();

		return usersVsTreesAlreadyCutGroupByItems;
	}

	public List<UsersVsTreeCutCompleteDateGroupBy> usersVsTreesCompletelyCutDate() throws SQLException {
		String sql = "SELECT User.email, User.firstName, User.lastName, treeID, completedOn FROM WorkOrder, User WHERE orderStatus=\"COMPLETE\" AND WorkOrder.email = User.email;";
		List<UsersVsTreeCutCompleteDateGroupBy> usersVsTreesAlreadyCutGroupByItems = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			usersVsTreesAlreadyCutGroupByItems.add(UsersVsTreeCutCompleteDateGroupBy.builder()
					.email(resultSet.getString("email")).firstName(resultSet.getString("firstName"))
					.lastName(resultSet.getString("lastName")).completedOn(resultSet.getString("completedOn"))
					.treeID(resultSet.getInt("treeID")).build());

		}

		resultSet.close();

		return usersVsTreesAlreadyCutGroupByItems;
	}

	public List<user> goodClients() throws SQLException {
		String sql = "SELECT * FROM USER WHERE email IN (SELECT email FROM Bill where email NOT IN (\n"
				+ "SELECT email FROM Bill WHERE HOUR(TIMEDIFF(settledOn, raisedOn))> 24 OR HOUR(TIMEDIFF(settledOn, raisedOn)) IS NULL\n"
				+ "));";
		List<user> users = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			users.add(user.builder().email(resultSet.getString("email")).firstName(resultSet.getString("firstName"))
					.lastName(resultSet.getString("lastName")).build());

		}

		resultSet.close();

		return users;
	}

	public List<UserVsMaxTreeCountGroupBy> bigClients() throws SQLException {

		connect_func();
		
		((PreparedStatement) connect.prepareStatement(
				"CREATE VIEW v3 AS  SELECT email, COUNT(treeID) as maxTreeCount FROM WorkOrder GROUP BY email;"))
				.executeUpdate();
		((PreparedStatement) connect
				.prepareStatement("CREATE VIEW v4 AS SELECT maxTreeCount FROM v3 ORDER BY maxTreeCount DESC LIMIT 1;"))
				.executeUpdate();

		String sql = "SELECT User.email, firstName, lastName, maxTreeCount FROM v3, User WHERE maxTreeCount IN (SELECT * FROM v4) AND v3.email=User.email;";
		List<UserVsMaxTreeCountGroupBy> userVsMaxTreeCountGroupByItems = new ArrayList<>();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			userVsMaxTreeCountGroupByItems.add(UserVsMaxTreeCountGroupBy.builder().email(resultSet.getString("email"))
					.firstName(resultSet.getString("firstName")).lastName(resultSet.getString("lastName"))
					.maxTreeCount(resultSet.getInt("maxTreeCount")).build());

		}

		((PreparedStatement) connect.prepareStatement("DROP VIEW v3;")).executeUpdate();
		((PreparedStatement) connect.prepareStatement("DROP VIEW v4;")).executeUpdate();

		resultSet.close();

		return userVsMaxTreeCountGroupByItems;
	}

	public List<Tree> cutTreesOfMaxHeight() throws SQLException {
		String sql = "	SELECT * FROM Tree, WorkOrder WHERE orderStatus=\"COMPLETE\" and Tree.treeID = WorkOrder.treeID and height IN (\n"
				+ "SELECT MAX(height) FROM Tree, WorkOrder WHERE orderStatus=\"COMPLETE\" and Tree.treeID = WorkOrder.treeID\n"
				+ ");";
		List<Tree> trees = new ArrayList<>();

		connect_func();

		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			trees.add(Tree.builder().height(resultSet.getInt("height")).address(resultSet.getString("address"))
					.distToHouse(resultSet.getInt("distToHouse")).treeID(resultSet.getInt("treeID")).build());

		}

		resultSet.close();

		return trees;
	}

}
