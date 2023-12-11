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
	background-color: #27ae60; /* Adjusted green color for header */
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
		<a href="/database/quoteRequest" target="_self">Go to Quote
			Requests Home</a>
	</div>
</body>
</html>
