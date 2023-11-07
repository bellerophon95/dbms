import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDAO userDAO = new userDAO();
	private QuoteDAO quoteDATO = new QuoteDAO();
	private user currentUser;
	private HttpSession session = null;
	private QuoteDAO quoteDAO;
	private QuoteRequestDAO quoteRequestDAO;
	private WorkOrderDAO WorkOrderDAO;
	private BillDAO BillDAO;
	private BillNegotiationRequestDAO BillNegotiationRequestDAO;
	private TreeDAO TreeDAO;
	private SimpleDateFormat formatter;

	public ControlServlet() {

	}

	public void init() {
		userDAO = new userDAO();
		quoteDAO = new QuoteDAO();
		quoteRequestDAO = new QuoteRequestDAO();
		WorkOrderDAO = new WorkOrderDAO();
		BillDAO = new BillDAO();
		BillNegotiationRequestDAO = new BillNegotiationRequestDAO();
		formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
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
				quoteDAO.init();
				quoteRequestDAO.init();
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
			case "/quote":
				System.out.println("The action is: list quotes");
				listQuotes(request, response);
				break;
			case "/quoteRequest":
				System.out.println("The action is: list quotes");
				listQuoteRequests(request, response);
				break;
			case "/client/quoteRequest":
				System.out.println("The action is: list quotes");
				listClientQuoteRequests(request, response);
				break;
			case "/client/quoteRequest/new":
				System.out.println("The action is: list quotes");
				listClientQuoteNewRequests(request, response);
				break;
			case "/client/quoteRequest/insert":
				System.out.println("The action is: list quotes");
				insertClientQuoteRequest(request, response);
				break;
			case "/client/quoteRequest/update":
				System.out.println("The action is: client update quote request");
				updateClientQuoteRequest(request, response);
				break;
			case "/quoteRequest/insert":
				System.out.println("The action is: list quotes");
				insertQuoteRequest(request, response);
				break;
			case "/quoteRequest/update":
				System.out.println("The action is: quote request update");
				updateQuoteRequest(request, response);
				listQuoteRequests(request, response);
				break;
			case "/quoteRequest/finalize":
				System.out.println("The action is: quote request update");
				finalizeQuoteRequest(request, response);
				break;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void finalizeQuoteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("insertQuote started: 00000000000000000000000000000000000");

		String email = request.getParameter("email");
		String createdOn = request.getParameter("createdOn");
		String description = request.getParameter("description");
		String status = request.getParameter("status");
		String currentDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
		String requestType = request.getParameter("requestType");

		quoteRequestDAO.deleteQuoteRequest(QuoteRequest.builder().email(email).createdOn(createdOn).build());

		quoteDAO.insertQuote(Quote.builder().createdTime(currentDate).email(email).description(description)
				.status("BACKLOG").modifiedTime(currentDate).requestType(requestType).build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listQuotes(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
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

	private void listQuotes(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Quote> quotes = quoteDAO.listQuotes();
		request.setAttribute("quotes", quotes);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/QuotesList.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
	}

	private void listClientQuoteNewRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<QuoteRequest> quoteRequests = quoteRequestDAO.listClientQuoteRequests(currentUser);
		request.setAttribute("quoteRequests", quoteRequests);
		request.setAttribute("client", currentUser);
//		request.setAttribute("requestType", \);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/NewClientQuoteRequest.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void listClientQuoteRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<QuoteRequest> quoteRequests = quoteRequestDAO.listClientQuoteRequests(currentUser);
		request.setAttribute("quoteRequests", quoteRequests);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ClientQuoteRequestsList.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void listQuoteRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<QuoteRequest> quoteRequests = quoteRequestDAO.listQuoteRequests();
		request.setAttribute("quoteRequests", quoteRequests);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/QuoteRequestsList.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void updateQuoteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("updateQuote started: 00000000000000000000000000000000000");
		String email = request.getParameter("email");
		String createdOn = request.getParameter("createdOn");

		request.setAttribute("email", email);
		request.setAttribute("createdOn", createdOn);
		request.setAttribute("description", request.getParameter("description"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("requestType", request.getParameter("requestType"));
		request.setAttribute("comment", request.getParameter("comment"));
		request.setAttribute("userComment", request.getParameter("userComment"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/EditQuoteRequest.jsp");
		dispatcher.forward(request, response);

		quoteRequestDAO.updateQuoteRequest(
				QuoteRequest.builder().email(request.getParameter("email")).createdOn(request.getParameter("createdOn"))
						.description(request.getParameter("description")).comment(request.getParameter("comment"))
						.status(request.getParameter("status")).requestType(request.getParameter("requestType"))
						.userComment(request.getParameter("userComment")).build());

		System.out.println("update successful finished: 111111111111111111111111111111111111");

	}

	private void updateClientQuoteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("updateQuote started: 00000000000000000000000000000000000");
		String email = request.getParameter("email");
		String createdOn = request.getParameter("createdOn");

		request.setAttribute("email", email);
		request.setAttribute("createdOn", createdOn);
		request.setAttribute("description", request.getParameter("description"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("requestType", request.getParameter("requestType"));
		request.setAttribute("comment", request.getParameter("comment"));
		request.setAttribute("userComment", request.getParameter("userComment"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/EditClientQuoteRequest.jsp");
		dispatcher.forward(request, response);

		System.out.println("USER COMMENT" + request.getParameter("userComment"));
		quoteRequestDAO.updateQuoteRequest(
				QuoteRequest.builder().email(request.getParameter("email")).createdOn(request.getParameter("createdOn"))
						.description(request.getParameter("description")).comment(request.getParameter("comment"))
						.status("PENDING").requestType(request.getParameter("requestType"))
						.userComment(request.getParameter("userComment")).build());

		System.out.println("update successful finished: 111111111111111111111111111111111111");

	}

	private void insertClientQuoteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("insertQuote started: 00000000000000000000000000000000000");
		quoteRequestDAO.insertQuoteRequest(
				QuoteRequest.builder().email(request.getParameter("email")).comment(request.getParameter("comment"))
						.createdOn(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString())
						.description(request.getParameter("description")).status("PENDING")
						.requestType(request.getParameter("requestType")).comment("NO_COMMENT_YET")
						.userComment("NO_USER_COMMENTS_YET").build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listClientQuoteRequests(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
	}

	private void insertQuoteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("insertQuote started: 00000000000000000000000000000000000");
		String comment = request.getParameter("comment");
		String commentToBeInserted = Objects.isNull(comment) ? "NO COMMENT YET" : comment;
		quoteRequestDAO.insertQuoteRequest(QuoteRequest.builder().email("root@gmail.com")
				.createdOn(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString())
				.description(request.getParameter("description")).comment(commentToBeInserted)
				.status("UNDER_CONSIDERATION").requestType(request.getParameter("requestType")).build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listQuoteRequests(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
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
