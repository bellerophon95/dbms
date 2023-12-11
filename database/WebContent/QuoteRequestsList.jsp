<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Quote Requests</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

.actionButton {
	display: inline-block;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #e74c3c; /* Updated color to the shade of red */
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.actionButton:hover {
	background-color: #c0392b; /* Darker shade for hover effect */
}

.actionButton a {
	color: inherit;
	text-decoration: none;
}

.button {
	margin: 5px;
	display: inline-block;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	transition: background-color 0.3s, color 0.3s;
}

.button:hover {
	background-color: #2980b9;
}

.container {
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 20px;
}

table {
	border-collapse: collapse;
	width: 80%;
	margin-top: 20px;
	border: 1px solid #444;
}

table caption {
	font-size: 1.5em;
	text-align: center;
	margin-bottom: 10px;
}

table th, table td {
	border: 1px solid #444;
	padding: 8px;
	text-align: center;
	color: #fff;
}

table th {
	background-color: #2ecc71; /* Green color for table header */
	color: white; /* Black text color */
}

.button:hover {
	background-color: #2980b9;
}

.button1 {
	display: inline-block;
	padding: 15px 25px;
	text-decoration: none;
	color: #fff;
	background-color: #3498db;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
	margin: 10px; /* Add margin between buttons */
	font-size: 16px; /* Increase font size */
	font-weight: bold; /* Add bold font weight */
	text-align: center; /* Center text */
	text-transform: uppercase; /* Uppercase text */
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
}

.button1:hover {
	background-color: #2980b9;
}

/* Additional styles for container */
.button-container {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
	/* Adjust button size and grid responsiveness */
	gap: 15px;
	margin: 20px auto; /* Center the grid */
	max-width: 1200px; /* Set a maximum width for the container */
}
</style>
</head>
<body>
	<div class="container">
		<table>
			<caption>List of Quote Requests</caption>
			<tr>
				<th>Quote Request ID</th>
				<th>Tree ID</th>
				<th>Email</th>
				<th>Created On</th>
				<th>Description</th>
				<th>Comment</th>
				<th>User Comment</th>
				<th>Request Type</th>
				<th>Status</th>
				<th>Proposed Price</th>
			</tr>
			<c:forEach var="quoteRequest" items="${quoteRequests}">
				<tr>
					<td><c:out value="${quoteRequest.quoteRequestID}" /></td>
					<td><c:out value="${quoteRequest.treeID}" /></td>
					<td><c:out value="${quoteRequest.email}" /></td>
					<td><c:out value="${quoteRequest.createdOn}" /></td>
					<td><c:out value="${quoteRequest.description}" /></td>
					<td><c:out value="${quoteRequest.comment}" /></td>
					<td><c:out value="${quoteRequest.userComment}" /></td>
					<td><c:out value="${quoteRequest.requestType}" /></td>
					<td><c:out value="${quoteRequest.status}" /></td>
					<td><c:out value="${quoteRequest.proposedPrice}" /></td>
					<td><a class="actionButton"
						href="/database/quoteRequest/update?createdOn=${quoteRequest.createdOn}
						&email=${quoteRequest.email}&description=${quoteRequest.description}&requestType=${quoteRequest.requestType}&status=${quoteRequest.status}&comment=${quoteRequest.comment}&userComment=${quoteRequest.userComment}">Update</a></td>
					<td><c:if
							test="${quoteRequest.status!='REJECTED' && quoteRequest.status!='REFILE'}">
							<a class="actionButton"
								href="/database/quoteRequest/finalize?createdOn=${quoteRequest.createdOn}
						&email=${quoteRequest.email}&description=${quoteRequest.description}&requestType=${quoteRequest.requestType}&status=${quoteRequest.status}&comment=${quoteRequest.comment}&userComment=${quoteRequest.userComment}">Finalize
								Quote</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="container">
		<div class="button-container">
			<a href="/database/quote" target="_self" class="button1">Go to
				quotes</a> <a href="/database/workOrder" target="_self" class="button1">Go
				to Work Orders</a> <a href="/database/bill" target="_self"
				class="button1">Go to Bills</a> <a
				href="/database/analytics/easyClients" target="_self"
				class="button1">Easy Clients</a> <a
				href="/database/analytics/badClients" target="_self" class="button1">Bad
				Clients</a> <a href="/database/analytics/goodClients" target="_self"
				class="button1">Good Clients</a> <a
				href="/database/analytics/overdueBills" target="_self"
				class="button1">Overdue Bills</a> <a
				href="/database/analytics/prospectiveClients" target="_self"
				class="button1">Prospective Clients</a> <a
				href="/database/analytics/acceptedQuotesWithOneTree" target="_self"
				class="button1">Accepted Quotes With One Tree</a> <a
				href="/database/analytics/cutTreesOfMaxHeight" target="_self"
				class="button1">Cut Trees of Max Height</a> <a
				href="/database/analytics/usersVsTreesCompletelyCutDate"
				target="_self" class="button1">Users Vs Trees Complete Cut Date</a>
			<a href="/database/analytics/usersVsTreesAlreadyCut" target="_self"
				class="button1">Users vs Trees Already Cut</a> <a
				href="/database/analytics/usersVsAmountPaidItems" target="_self"
				class="button1">Users vs Amount Paid</a> <a
				href="/database/analytics/usersVsAmountDueItems" target="_self"
				class="button1">Users vs Amount Due</a><a
				href="/database/analytics/bigClients" target="_self"
				class="button1">Big Clients</a>
				

		</div>

	</div>
</body>
</html>
