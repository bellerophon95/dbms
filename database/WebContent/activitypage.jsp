<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activity page</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

h1 {
	text-align: center;
	color: #fff;
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
	<center>
		<h1>Welcome! You have been successfully logged in</h1>
	</center>

	<center>
		<a href="login.jsp" target="_self"> logout</a><br> <br>
		<p>You can show all the transactions or other attributes here like
			balance, name of the user and others.</p>
		<a href="/database/client/quoteRequest" target="_self"> List quote
			requests</a>
	</center>
</body>
</html>
