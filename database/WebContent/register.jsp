<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
<style>
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
	padding-top: 20px;
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
	background-color: #e74c3c;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"]:hover {
	background-color: #c0392b;
}

a {
	color: #3498db;
	text-decoration: none;
	display: block;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div>
		<p>${errorOne }</p>
		<p>${errorTwo }</p>
		<form action="register">
			<table border="1" cellpadding="5">
				<tr>
					<th>Username:</th>
					<td align="center" colspan="3"><input type="text" name="email"
						size="45" value="example@gmail.com" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td align="center" colspan="3"><input type="text"
						name="lastName" size="45" value="LastName" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Role:</th>
					<td align="center" colspan="3"><input type="text"
						name="lastName" size="45" value="role" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Birthday:</th>
					<td align="center" colspan="3"><input type="text"
						name="birthday" size="45" value="YYYY-MM-DD"
						onfocus="this.value=''"></td>

				</tr>
				<tr>
					<th>Password:</th>
					<td align="center" colspan="3"><input type="password"
						name="password" size="45" value="password" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Password Confirmation:</th>
					<td align="center" colspan="3"><input type="password"
						name="confirmation" size="45" value="password"
						onfocus="this.value=''"></td>

				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
			<a href="login.jsp" target="_self">Return to Login Page</a>
		</form>
	</div>
</body>
</html>