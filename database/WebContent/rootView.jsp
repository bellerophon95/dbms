<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
<style>
h1, h2 {
	text-align: center;
	color: #fff;
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

body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

div {
	align-items: center;
	display: flex;
	justify-content: center;
	flex-direction: column;
	padding-top: 50px;
}

form {
	width: 50%;
	margin: auto;
	text-align: center;
}

input[type="submit"] {
	width: fit-content;
	padding: 10px 20px;
	border-radius: 5px;
	border: none;
	background-color: #3498db;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"]:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>

	<div align="center">
		<form action="initialize">
			<input type="submit" value="Initialize the Database" />
		</form>
		<a href="login.jsp" target="_self"> logout</a><br> <br>
		<div align="center">
			<table border="1" cellpadding="6">
				<caption>
					<h2>List of Users</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>First name</th>
					<th>Last name</th>
					<th>Address</th>
					<th>Password</th>
					<th>Birthday</th>
					<th>cash_bal($)</th>
					<th>PPS_bal</th>
					<th>Role</th>
				</tr>
				<c:forEach var="users" items="${listUser}">
					<tr style="text-align: center">
						<td><c:out value="${users.email}" /></td>
						<td><c:out value="${users.firstName}" /></td>
						<td><c:out value="${users.lastName}" /></td>
						<td><c:out
								value="${users.adress_street_num} ${users.adress_street} ${users.adress_city} ${users.adress_state} ${users.adress_zip_code}" /></td>
						<td><c:out value="${users.password}" /></td>
						<td><c:out value="${users.birthday}" /></td>
						<td><c:out value="${users.cash_bal}" /></td>
						<td><c:out value="${users.PPS_bal}" /></td>
						<td><c:out value="${users.role}" /></td>
					</tr>
				</c:forEach>
			</table>
			<a href="/database/quoteRequest">Go to quote requests home</a>
		</div>
	</div>

</body>
</html>
