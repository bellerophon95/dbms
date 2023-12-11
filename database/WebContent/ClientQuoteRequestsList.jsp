<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

div {
	margin: 20px auto;
	text-align: center;
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
	border: 1px solid #ddd;
}

table caption {
	font-size: 1.5em;
	margin-bottom: 10px;
	color: white;
}

th, td {
	padding: 8px;
	border: 1px solid #ddd;
}

th {
	background-color: #27ae60;
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
		<a style="background-color: #ff6347"
			href="/database/client/quoteRequest/new" target="_self">Raise new
			request</a>
		<form action="/database/client/quoteRequest/update">
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Quote Requests</h2>
				</caption>
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
					<th>Update</th>
				</tr>
				<c:forEach var="quoteRequest" items="${quoteRequests}">
					<tr style="text-align: center">
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
						<td><a
							href="/database/client/quoteRequest/update?createdOn=${quoteRequest.createdOn}
							&email=${quoteRequest.email}&description=${quoteRequest.description}&requestType=${quoteRequest.requestType}
							&status=${quoteRequest.status}&comment=${quoteRequest.comment}&userComment=${quoteRequest.userComment}">Update</a></td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<div style="display: flex;">
			<a href="/database/client/bill" target="_self">View My Bills</a>
			<a href="/database/client/billNegotiationRequest" target="_self">View My Bill Negotiation Requests</a>
		</div>
	</div>
</body>
</html>
