<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>All User list</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #121212;
	color: #eee;
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
	color: white;
}

table th {
	background-color: #2ecc71; /* Green color for table header */
	color: white; /* Black text color */
}

.button {
	display: inline-block;
	padding: 10px 20px;
	margin-top: 20px;
	text-decoration: none;
	color: #fff;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.button:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>
	<div class="container">
		<table>
			<caption>List of Prospective Clients</caption>
			<tr>
				<th>Email</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			<c:forEach var="prospectiveClient" items="${prospectiveClients}">
				<tr>
					<td><c:out value="${prospectiveClient.email}" /></td>
					<td><c:out value="${prospectiveClient.firstName}" /></td>
					<td><c:out value="${prospectiveClient.lastName}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="container">
		<a href="/database/quoteRequest" target="_self" class="button">Go
			to Quote Requests Home</a>
	</div>
</body>
</html>
