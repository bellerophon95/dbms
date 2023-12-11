<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All User list</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #1f1f1f;
	color: #f2f2f2;
	margin: 0;
	padding: 0;
}

div {
	margin: 20px auto;
	text-align: center;
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
	border: 1px solid #444;
}

table caption {
	font-size: 1.5em;
	margin-bottom: 10px;
}

th, td {
	padding: 8px;
	border: 1px solid #444;
}

th {
	background-color: #333;
}

tr:nth-child(even) {
	background-color: #333;
}
</style>

</head>
<body>
	<div align="center">
		<form action="/database/quoteRequest/update">
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Easy Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				<c:forEach var="easyClient" items="${easyClients}">
					<tr style="text-align: center">
						<td><c:out value="${easyClient.email}" /></td>
						<td><c:out value="${easyClient.firstName}" /></td>
						<td><c:out value="${easyClient.lastName}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Bad Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				</tr>
				<c:forEach var="badClient" items="${badClients}">
					<tr style="text-align: center">
						<td><c:out value="${badClient.email}" /></td>
						<td><c:out value="${badClient.firstName}" /></td>
						<td><c:out value="${badClient.lastName}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Accepted Quotes With One Tree</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>Created Time</th>
					<th>Description</th>
					<th>Status</th>
					<th>Request Type</th>
					<th>Tree ID</th>
					<th>Quote Request ID</th>
				</tr>
				<c:forEach var="quote" items="${acceptedQuotesWithOneTree}">
					<tr style="text-align: center">
						<td><c:out value="${quote.email}" /></td>
						<td><c:out value="${quote.createdTime}" /></td>
						<td><c:out value="${quote.description}" /></td>
						<td><c:out value="${quote.status}" /></td>
						<td><c:out value="${quote.requestType}" /></td>
						<td><c:out value="${quote.treeID}" /></td>
						<td><c:out value="${quote.quoteRequestID}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Accepted Quotes With More Than One Tree</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>Created Time</th>
					<th>Description</th>
					<th>Status</th>
					<th>Request Type</th>
					<th>Tree ID</th>
					<th>Quote Request ID</th>
				</tr>
				<c:forEach var="quote" items="${acceptedQuotesWithMoreThanOneTree}">
					<tr style="text-align: center">
						<td><c:out value="${quote.email}" /></td>
						<td><c:out value="${quote.createdTime}" /></td>
						<td><c:out value="${quote.description}" /></td>
						<td><c:out value="${quote.status}" /></td>
						<td><c:out value="${quote.requestType}" /></td>
						<td><c:out value="${quote.treeID}" /></td>
						<td><c:out value="${quote.quoteRequestID}" /></td>
					</tr>
				</c:forEach>
			</table>

			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Overdue Bills</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>Raised On</th>
					<th>Amount</th>
					<th>Settled On</th>
					<th>Payment Status</th>
					<th>Due Date</th>

				</tr>
				<c:forEach var="overdueBill" items="${overdueBills}">
					<tr style="text-align: center">
						<td><c:out value="${overdueBill.email}" /></td>
						<td><c:out value="${overdueBill.raisedOn}" /></td>
						<td><c:out value="${overdueBill.amount}" /></td>
						<td><c:out value="${overdueBill.settledOn}" /></td>
						<td><c:out value="${overdueBill.paymentStatus}" /></td>
						<td><c:out value="${overdueBill.dueDate}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Prospective Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				<c:forEach var="prospectiveClient" items="${prospectiveClients}">
					<tr style="text-align: center">
						<td><c:out value="${prospectiveClient.email}" /></td>
						<td><c:out value="${prospectiveClient.firstName}" /></td>
						<td><c:out value="${prospectiveClient.lastName}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Good Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
				</tr>
				<c:forEach var="goodClient" items="${goodClients}">
					<tr style="text-align: center">
						<td><c:out value="${goodClient.email}" /></td>
						<td><c:out value="${goodClient.firstName}" /></td>
						<td><c:out value="${goodClient.lastName}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Cut Trees of Max Height</h2>
				</caption>
				<tr>
					<th>Tree ID</th>
					<th>Height</th>
					<th>Address</th>
					<th>Distance To House</th>
				</tr>
				<c:forEach var="cutTreeOfMaxHeight" items="${cutTreesOfMaxHeight}">
					<tr style="text-align: center">
						<td><c:out value="${cutTreeOfMaxHeight.treeID}" /></td>
						<td><c:out value="${cutTreeOfMaxHeight.height}" /></td>
						<td><c:out value="${cutTreeOfMaxHeight.address}" /></td>
						<td><c:out value="${cutTreeOfMaxHeight.distToHouse}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Big Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Max Tree Count</th>
				</tr>
				<c:forEach var="userVsMaxTreeCountGroupByItem"
					items="${userVsMaxTreeCountGroupByItems}">
					<tr style="text-align: center">
						<td><c:out value="${userVsMaxTreeCountGroupByItem.email}" /></td>
						<td><c:out value="${userVsMaxTreeCountGroupByItem.firstName}" /></td>
						<td><c:out value="${userVsMaxTreeCountGroupByItem.lastName}" /></td>
						<td><c:out
								value="${userVsMaxTreeCountGroupByItem.maxTreeCount}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Big Clients</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Max Tree Count</th>
				</tr>
				<c:forEach var="userVsMaxTreeCountGroupByItem"
					items="${userVsMaxTreeCountGroupByItems}">
					<tr style="text-align: center">
						<td><c:out value="${userVsMaxTreeCountGroupByItem.email}" /></td>
						<td><c:out value="${userVsMaxTreeCountGroupByItem.firstName}" /></td>
						<td><c:out value="${userVsMaxTreeCountGroupByItem.lastName}" /></td>
						<td><c:out
								value="${userVsMaxTreeCountGroupByItem.maxTreeCount}" /></td>
					</tr>
				</c:forEach>
			</table>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>Users Vs Amount Due</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Max Tree Count</th>
				</tr>
				<c:forEach var="usersVsAmountDueItem"
					items="${usersVsAmountDueItems}">
					<tr style="text-align: center">
						<td><c:out value="${usersVsAmountDueItem.email}" /></td>
						<td><c:out value="${usersVsAmountDueItem.firstName}" /></td>
						<td><c:out value="${usersVsAmountDueItem.lastName}" /></td>
						<td><c:out value="${usersVsAmountDueItem.amountDue}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>Users Vs Amount Paid</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Max Tree Count</th>
				</tr>
				<c:forEach var="usersVsAmountPaidItem"
					items="${usersVsAmountPaidItems}">
					<tr style="text-align: center">
						<td><c:out value="${usersVsAmountPaidItem.email}" /></td>
						<td><c:out value="${usersVsAmountPaidItem.firstName}" /></td>
						<td><c:out value="${usersVsAmountPaidItem.lastName}" /></td>
						<td><c:out value="${usersVsAmountPaidItem.amountPaid}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>Users Vs Trees Already Cut</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Trees Already Cut</th>
				</tr>
				<c:forEach var="usersVsTreesAlreadyCutItem"
					items="${usersVsTreesAlreadyCutItems}">
					<tr style="text-align: center">
						<td><c:out value="${usersVsTreesAlreadyCutItem.email}" /></td>
						<td><c:out value="${usersVsTreesAlreadyCutItem.firstName}" /></td>
						<td><c:out value="${usersVsTreesAlreadyCutItem.lastName}" /></td>
						<td><c:out
								value="${usersVsTreesAlreadyCutItem.treesAlreadyCut}" /></td>
					</tr>
				</c:forEach>
			</table>
			<table border="1" cellpadding="5">
				<caption>
					<h2>Users Vs Trees Cut Complete Date</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Tree ID</th>
					<th>Completed On</th>
				</tr>
				<c:forEach var="usersVsTreeCutCompleteDateItem"
					items="${usersVsTreeCutCompleteDateItems}">
					<tr style="text-align: center">
						<td><c:out value="${usersVsTreeCutCompleteDateItem.email}" /></td>
						<td><c:out
								value="${usersVsTreeCutCompleteDateItem.firstName}" /></td>
						<td><c:out value="${usersVsTreeCutCompleteDateItem.lastName}" /></td>
						<td><c:out value="${usersVsTreeCutCompleteDateItem.treeID}" /></td>
						<td><c:out
								value="${usersVsTreeCutCompleteDateItem.completedOn}" /></td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<!-- 		<a href="/database/NewQuoteRequest.jsp" target="_self">Raise new
			request</a> -->
		<a href="/database/quote" target="_self">Go to quotes</a>
	</div>
</body>
</html>