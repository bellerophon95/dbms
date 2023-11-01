import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDAO userDAO = new userDAO();
	private user currentUser;
	private HttpSession session = null;
	private QuoteDAO QuoteDAO;
	private QuoteRequestDAO QuoteRequestDAO;
	private WorkOrderDAO WorkOrderDAO;
	private BillDAO BillDAO;
	private BillNegotiationRequestDAO BillNegotiationRequestDAO;
	private TreeDAO TreeDAO;

	public ControlServlet() {

	}

	public void init() {
		userDAO = new userDAO();
		QuoteDAO = new QuoteDAO();
		QuoteRequestDAO = new QuoteRequestDAO();
		WorkOrderDAO = new WorkOrderDAO();
		BillDAO = new BillDAO();
		BillNegotiationRequestDAO = new BillNegotiationRequestDAO();
		TreeDAO = new TreeDAO();
		currentUser = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		try {
			switch (action) {
			case "/login":
				login(request, response);
				break;
			case "/register":
				register(request, response);
				break;
			case "/initialize":
				userDAO.init();
				QuoteDAO.init();
				QuoteRequestDAO.init();
				WorkOrderDAO.init();
				BillDAO.init();
				BillNegotiationRequestDAO.init();
				TreeDAO.init();
				System.out.println("Database successfully initialized!");
				rootPage(request, response, "");
				break;
			case "/root":
				rootPage(request, response, "");
				break;
			case "/logout":
				logout(request, response);
				break;
			case "/list":
				System.out.println("The action is: list");
				listUser(request, response);
				break;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listUser started: 00000000000000000000000000000000000");

		List<user> listUser = userDAO.listAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
		dispatcher.forward(request, response);

		System.out.println("listPeople finished: 111111111111111111111111111111111111");
	}

	private void rootPage(HttpServletRequest request, HttpServletResponse response, String view)
			throws ServletException, IOException, SQLException {
		System.out.println("root view");
		request.setAttribute("listUser", userDAO.listAllUsers());
		request.getRequestDispatcher("rootView.jsp").forward(request, response);
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email.equals("root") && password.equals("pass1234")) {
			System.out.println("Login Successful! Redirecting to root");
			session = request.getSession();
			session.setAttribute("username", email);
			rootPage(request, response, "");
		} else if (userDAO.isValid(email, password)) {

			currentUser = userDAO.getUser(email);
			System.out.println("Login Successful! Redirecting");
			request.getRequestDispatcher("activitypage.jsp").forward(request, response);

		} else {
			request.setAttribute("loginStr", "Login Failed: Please check your credentials.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String birthday = request.getParameter("birthday");
		String adress_street_num = request.getParameter("adress_street_num");
		String adress_street = request.getParameter("adress_street");
		String adress_city = request.getParameter("adress_city");
		String adress_state = request.getParameter("adress_state");
		String adress_zip_code = request.getParameter("adress_zip_code");
		String confirm = request.getParameter("confirmation");
		String role = request.getParameter("role");

		Boolean isEmailAvailable = !userDAO.checkEmail(email);

		if (password.equals(confirm)) {
			if (isEmailAvailable || !userDAO.checkAddress(adress_street_num, adress_street, adress_city, adress_state,
					adress_zip_code)) {
				System.out.println("Registration Successful! Added to database");
				user users = user.builder().email(email).firstName(firstName).lastName(lastName).password(password)
						.birthday(birthday).adress_street_num(adress_street_num).adress_street(adress_street)
						.adress_city(adress_city).adress_state(adress_state).adress_zip_code(adress_zip_code)
						.cash_bal(1000).PPS_bal(0).role(role).build();
//				user users = new user(email, firstName, lastName, password, birthday, adress_street_num, adress_street,
//						adress_city, adress_state, adress_zip_code, 1000, 0, role);
				userDAO.insert(users);
				response.sendRedirect("login.jsp");
			} else if (!isEmailAvailable) {
				System.out.println("Username taken, please enter new username");
				request.setAttribute("errorOne", "Registration failed: Username taken, please enter a new username.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			} else {
				System.out.println("Address taken, please enter new username");
				request.setAttribute("errorOne", "Registration failed: Address taken, please enter a new address.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		} else {
			System.out.println("Password and Password Confirmation do not match");
			request.setAttribute("errorTwo", "Registration failed: Password and Password Confirmation do not match.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		currentUser = null;
		response.sendRedirect("login.jsp");
	}

}
