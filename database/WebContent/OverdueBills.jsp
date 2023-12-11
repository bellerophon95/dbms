<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>List of Overdue Bills</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

table {
	margin: 20px auto;
	border-collapse: collapse;
	width: 80%;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 8px;
	text-align: left;
}

th {
	background-color: #2ecc71; /* Updated green color for table header */
	color: #fff;
}

tr:nth-child(even) {
	background-color: #212121;
}

a {
	display: block;
	width: fit-content;
	margin: 20px auto;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	text-align: center;
	transition: background-color 0.3s, color 0.3s;
}

a:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>
	<div align="center">
		<table>
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
		<a href="/database/quoteRequest" target="_self">Go to Quote
			Requests Home</a>
	</div>
</body>
</html>
