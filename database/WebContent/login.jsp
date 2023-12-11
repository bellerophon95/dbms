<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login to Database</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

center {
	text-align: center;
}

h1 {
	margin-top: 50px;
	color: #27ae60; /* Adjusted green color for the header */
}

div {
	align-items: center;
	display: flex;
	justify-content: center;
	flex-direction: column;
	padding-top: 20px;
}

p {
	color: #e74c3c; /* Red color for error messages */
	font-weight: bold;
}

form {
	width: 50%;
	margin: auto;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	padding: 8px;
	text-align: center;
}

th {
	background-color: #27ae60;
	color: #fff;
}

input[type="text"], input[type="password"], input[type="submit"] {
	width: calc(100% - 20px);
	padding: 10px;
	margin: 5px 0;
	border-radius: 5px;
	border: none;
}

input[type="submit"] {
	background-color: #3498db;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"]:hover {
	background-color: #2980b9;
}

a {
	display: inline-block;
	margin-top: 20px;
	padding: 10px 20px;
	color: #fff;
	background-color: #3498db;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color 0.3s, color 0.3s;
}

a:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>
	<center>
		<h1>Welcome to ppswap Login page</h1>
	</center>
	<div align="center">
		<p>${loginFailedStr}</p>
		<form action="login" method="post">
			<table border="1" cellpadding="5">
				<tr>
					<th>Username:</th>
					<td><input type="text" name="email" size="45" autofocus>
					</td>
				</tr>
				<tr>
					<th>Password:</th>
					<td><input type="password" name="password" size="45">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Login" /></td>
				</tr>
			</table>
			<a href="register.jsp" target="_self">Register Here</a>
		</form>
	</div>
</body>
</html>
