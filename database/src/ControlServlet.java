import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private AnalyticsDAO analyticsDAO;
	private TreeDAO treeDAO;

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
		analyticsDAO = new AnalyticsDAO();
		treeDAO = new TreeDAO();
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
			case "/workOrder":
				System.out.println("The action is: list quotes");
				listWorkOrders(request, response);
			case "/workOrder/new":
				System.out.println("The action is: list quotes");
				newWorkOrderRequests(request, response);
				break;
			case "/workOrder/insert":
				System.out.println("The action is: list quotes");
				insertWorkOrder(request, response);
				break;
			case "/quoteRequest":
				System.out.println("The action is: list quotes");
				listQuoteRequests(request, response);
				break;
			case "/bill":
				System.out.println("The action is: list quotes");
				listBills(request, response);
				break;
			case "/bill/new":
				System.out.println("The action is: list quotes");
				newBill(request, response);
				break;
			case "/bill/insert":
				System.out.println("The action is: list quotes");
				insertBill(request, response);
				break;
			case "/analytics":
				System.out.println("The action is: list quotes");
				analytics(request, response);
				break;
			case "/analytics/easyClients":
				System.out.println("The action is: list quotes");
				easyClients(request, response);
				break;
			case "/analytics/badClients":
				System.out.println("The action is: list quotes");
				badClients(request, response);
				break;
			case "/analytics/goodClients":
				System.out.println("The action is: list quotes");
				goodClients(request, response);
				break;
			case "/analytics/cutTreesOfMaxHeight":
				System.out.println("The action is: list quotes");
				cutTreesOfMaxHeight(request, response);
				break;
			case "/analytics/overdueBills":
				System.out.println("The action is: list quotes");
				overdueBills(request, response);
				break;
			case "/analytics/usersVsAmountDueItems":
				System.out.println("The action is: list quotes");
				usersVsAmountDueItems(request, response);
				break;
			case "/analytics/acceptedQuotesWithOneTree":
				System.out.println("The action is: list quotes");
				acceptedQuotesWithOneTree(request, response);
				break;
			case "/analytics/usersVsTreesCompletelyCutDate":
				System.out.println("The action is: list quotes");
				usersVsTreesCompletelyCutDate(request, response);
				break;
			case "/analytics/usersVsTreesAlreadyCut":
				System.out.println("The action is: list quotes");
				usersVsTreesAlreadyCut(request, response);
				break;
			case "/analytics/usersVsAmountPaidItems":
				System.out.println("The action is: list quotes");
				usersVsAmountPaidItems(request, response);
				break;
			case "/analytics/bigClients":
				System.out.println("The action is: list quotes");
				bigClients(request, response);
				break;
			case "/analytics/prospectiveClients":
				System.out.println("The action is: list quotes");
				prospectiveClients(request, response);
				break;
			case "/client/quoteRequest":
				System.out.println("The action is: list quotes");
				listClientQuoteRequests(request, response);
				break;
			case "/client/bill":
				System.out.println("The action is: list quotes");
				listClientBills(request, response);
				break;
			case "/client/billNegotiationRequest":
				System.out.println("The action is: list quotes");
				listClientBillNegotiationRequests(request, response);
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

	private void listWorkOrders(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<WorkOrder> workOrders = WorkOrderDAO.listWorkOrders();
		request.setAttribute("workOrders", workOrders);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WorkOrder.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
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

	private void listClientBillNegotiationRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<BillNegotiationRequest> clientBillNegotiationRequests = BillNegotiationRequestDAO
				.listClientBillNegotiationRequests(currentUser);
		request.setAttribute("clientBillNegotiationRequests", clientBillNegotiationRequests);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ClientBillsNegotiationRequest.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void listBills(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Bill> bills = BillDAO.listBills();
		request.setAttribute("bills", bills);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Bills.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void listClientBills(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Bill> clientBills = BillDAO.listClientBills(currentUser);
		request.setAttribute("clientBills", clientBills);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ClientBills.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void newBill(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("createdTime", request.getParameter("createdOn"));
		request.setAttribute("treeID", request.getParameter("treeID"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/NewBill.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
	}

	private void newWorkOrderRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");
	
		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("createdTime", request.getParameter("quoteCreatedTime"));
		request.setAttribute("treeID", request.getParameter("treeID"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/NewWorkOrder.jsp");
		dispatcher.forward(request, response);

		System.out.println("quotesRequestList finished: 111111111111111111111111111111111111");
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

	private void analytics(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<user> easyClients = analyticsDAO.easyClients();
		List<Quote> acceptedQuotesWithOneTree = analyticsDAO.acceptedQuotesWithOneTree();
		List<Quote> acceptedQuotesWithMoreThanOneTree = analyticsDAO.acceptedQuotesWithMoreThanOneTree();
		List<user> badClients = analyticsDAO.badClients();
		List<user> goodClients = analyticsDAO.goodClients();
		List<user> prospectiveClients = analyticsDAO.prospectiveClients();
		List<Bill> overdueBills = analyticsDAO.overdueBills();
		List<Tree> cutTreesOfMaxHeight = analyticsDAO.cutTreesOfMaxHeight();
		List<UserVsMaxTreeCountGroupBy> userVsMaxTreeCountGroupByItems = analyticsDAO.bigClients();
		List<UsersVsAmountDueGroupBy> usersVsAmountDueItems = analyticsDAO.usersVsAmountDue();
		List<UsersVsAmountPaidGroupBy> usersVsAmountPaidItems = analyticsDAO.usersVsAmountPaid();
		List<UsersVsTreesAlreadyCutGroupBy> usersVsTreesAlreadyCutItems = analyticsDAO.usersVsTreesAlreadyCut();
		List<UsersVsTreeCutCompleteDateGroupBy> usersVsTreeCutCompleteDateItems = analyticsDAO
				.usersVsTreesCompletelyCutDate();

		request.setAttribute("easyClients", easyClients);
		request.setAttribute("badClients", badClients);
		request.setAttribute("prospectiveClients", prospectiveClients);
		request.setAttribute("acceptedQuotesWithOneTree", acceptedQuotesWithOneTree);
		request.setAttribute("acceptedQuotesWithMoreThanOneTree", acceptedQuotesWithMoreThanOneTree);
		request.setAttribute("overdueBills", overdueBills);
		request.setAttribute("goodClients", goodClients);
		request.setAttribute("cutTreesOfMaxHeight", cutTreesOfMaxHeight);
		request.setAttribute("userVsMaxTreeCountGroupByItems", userVsMaxTreeCountGroupByItems);
		request.setAttribute("usersVsAmountDueItems", usersVsAmountDueItems);
		request.setAttribute("usersVsAmountPaidItems", usersVsAmountPaidItems);
		request.setAttribute("usersVsTreesAlreadyCutItems", usersVsTreesAlreadyCutItems);
		request.setAttribute("usersVsTreeCutCompleteDateItems", usersVsTreeCutCompleteDateItems);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/Analytics.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void usersVsAmountDueItems(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<UsersVsAmountDueGroupBy> usersVsAmountDueItems = analyticsDAO.usersVsAmountDue();

		request.setAttribute("usersVsAmountDueItems", usersVsAmountDueItems);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersVsAmountDue.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void usersVsAmountPaidItems(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<UsersVsAmountPaidGroupBy> usersVsAmountPaidItems = analyticsDAO.usersVsAmountPaid();

		request.setAttribute("usersVsAmountPaidItems", usersVsAmountPaidItems);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersVsAmountPaid.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void usersVsTreesAlreadyCut(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<UsersVsTreesAlreadyCutGroupBy> usersVsTreesAlreadyCutItems = analyticsDAO.usersVsTreesAlreadyCut();

		request.setAttribute("usersVsTreesAlreadyCutItems", usersVsTreesAlreadyCutItems);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersVsTreesAlreadyCut.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void usersVsTreesCompletelyCutDate(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<UsersVsTreeCutCompleteDateGroupBy> usersVsTreeCutCompleteDateItems = analyticsDAO
				.usersVsTreesCompletelyCutDate();

		request.setAttribute("usersVsTreeCutCompleteDateItems", usersVsTreeCutCompleteDateItems);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersVsTreeCutCompleteDate.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void bigClients(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<UserVsMaxTreeCountGroupBy> bigClients = analyticsDAO.bigClients();

		request.setAttribute("bigClients", bigClients);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/BigClients.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void cutTreesOfMaxHeight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Tree> cutTreesOfMaxHeight = analyticsDAO.cutTreesOfMaxHeight();

		request.setAttribute("cutTreesOfMaxHeight", cutTreesOfMaxHeight);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/CutTreesOfMaxHeight.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void acceptedQuotesWithOneTree(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Quote> acceptedQuotesWithOneTree = analyticsDAO.acceptedQuotesWithOneTree();

		request.setAttribute("acceptedQuotesWithOneTree", acceptedQuotesWithOneTree);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/AcceptedQuotesWithOneTree.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void prospectiveClients(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<user> prospectiveClients = analyticsDAO.prospectiveClients();

		request.setAttribute("prospectiveClients", prospectiveClients);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/ProspectiveClients.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void overdueBills(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<Bill> overdueBills = analyticsDAO.overdueBills();

		request.setAttribute("overdueBills", overdueBills);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/OverdueBills.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void goodClients(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<user> goodClients = analyticsDAO.goodClients();

		request.setAttribute("goodClients", goodClients);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/GoodClients.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void badClients(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<user> badClients = analyticsDAO.badClients();

		request.setAttribute("badClients", badClients);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/BadClients.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
	}

	private void easyClients(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		System.out.println("listQuoteRequest started: 00000000000000000000000000000000000");

		List<user> easyClients = analyticsDAO.easyClients();

		request.setAttribute("easyClients", easyClients);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/EasyClients.jsp");
		dispatcher.forward(request, response);

		System.out.println("analytics finished: 111111111111111111111111111111111111");
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

		List<String> treeDistances = List.of(request.getParameter("distOfTreeToHouse").split(","));
		List<String> treeHeights = List.of(request.getParameter("treeHeight").split(","));

		int treeCount = Math.min(treeDistances.size(), treeHeights.size());

		List<Integer> insertedTreeIDs = IntStream.range(0, treeCount).mapToObj(i -> {
			Integer insertedTreeID = -1;
			try {
				insertedTreeID = treeDAO.insert(Tree.builder().distToHouse(Integer.valueOf(treeDistances.get(i)))
						.address(request.getParameter("treeLocation")).height(Integer.valueOf(treeHeights.get(i)))
						.build());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return insertedTreeID;
		}).collect(Collectors.toList());

		Integer currentQuoteRequestsID = quoteRequestDAO.getMaxQuoteRequestID() + 1;

		for (Integer insertedTreeID : insertedTreeIDs) {
			quoteRequestDAO.insertQuoteRequest(QuoteRequest.builder().quoteRequestID(currentQuoteRequestsID)
					.email(request.getParameter("email")).comment(request.getParameter("comment"))
					.createdOn(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString())
					.description(request.getParameter("description")).status("PENDING")
					.requestType(request.getParameter("requestType")).comment("NO_COMMENT_YET")
					.userComment("NO_USER_COMMENTS_YET").treeID(insertedTreeID)
					.proposedPrice(Integer.valueOf(request.getParameter("proposedPrice"))).build());
		}

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listClientQuoteRequests(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
	}

	private String rfc3339ToSQLDateTime(String rfc3339DateTime) throws UnsupportedEncodingException {

		String decodedString = URLDecoder.decode(rfc3339DateTime, "UTF-8");
		DateTimeFormatter rfcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(decodedString, rfcFormatter);

		DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTime.format(sqlFormatter);
	}

	private void insertWorkOrder(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("insertQuote started: 00000000000000000000000000000000000");
		String email = request.getParameter("email");
		String createdOn = request.getParameter("quoteCreatedTime");
		Integer treeID = Integer.valueOf(request.getParameter("treeID"));
		String dueDate = rfc3339ToSQLDateTime(request.getParameter("dueDate"));
		String description = request.getParameter("description");
		String orderStatus = "PENDING";

		quoteDAO.delete(email, createdOn);

		WorkOrderDAO.insertWorkOrder(WorkOrder.builder().email(email).createdOn(createdOn).treeID(treeID)
				.dueDate(dueDate).description(description).orderStatus(orderStatus).build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listWorkOrders(request, response);

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
				.status("UNDER_CONSIDERATION").requestType(request.getParameter("requestType"))
				.proposedPrice(Integer.valueOf(request.getParameter("proposedPrice"))).build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listQuoteRequests(request, response);

		System.out.println("quotesList finished: 111111111111111111111111111111111111");
	}

	private void insertBill(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ParseException {
		System.out.println("insertQuote started: 00000000000000000000000000000000000");
		
		String createdOn = request.getParameter("createdTime");
		WorkOrderDAO.delete(request.getParameter("email"), createdOn);
		
		BillDAO.insertBill(Bill.builder().email(request.getParameter("email"))
				.raisedOn((new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis())).toString())
				.amount(Integer.valueOf(request.getParameter("amount"))).dueDate(request.getParameter("dueDate"))
				.settledOn(null).paymentStatus("UNPAID").build());

		System.out.println("insert successful finished: 111111111111111111111111111111111111");
		this.listBills(request, response);

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
