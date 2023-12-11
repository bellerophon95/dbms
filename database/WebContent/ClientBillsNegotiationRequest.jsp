<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>List of Bad Clients</title>
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
	background-color: #2ecc71; /* Specified green color for table headers */
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
		<table border="1" cellpadding="5">
			<caption>
				<h2>My Bill Negotiation Requests</h2>
			</caption>
			<tr>
				<th>Email</th>
				<th>Created On</th>
				<th>Description</th>
				<th>Status</th>
				<th>Request Type</th>
				<th>Quote Request ID</th>
			</tr>
			<c:forEach var="clientBillNegotiationRequest"
				items="${clientBillNegotiationRequests}">
				<tr style="text-align: center">
					<td><c:out value="${clientBillNegotiationRequest.email}" /></td>
					<td><c:out value="${clientBillNegotiationRequest.createdOn}" /></td>
					<td><c:out value="${clientBillNegotiationRequest.description}" /></td>
					<td><c:out value="${clientBillNegotiationRequest.status}" /></td>
					<td><c:out value="${clientBillNegotiationRequest.requestType}" /></td>
					<td><c:out
							value="${clientBillNegotiationRequest.quoteRequestID}" /></td>
				</tr>
			</c:forEach>
		</table>
		<a href="/database/client/quoteRequest" target="_self">Go to Quote
			Requests Home</a>
	</div>
</body>
</html>
