<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #1f1f1f;
	color: #f2f2f2;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

form {
	width: 50%;
	background-color: #333;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

th, td {
	padding: 10px;
	text-align: left;
	border-bottom: 1px solid #444;
}

th {
	background-color: #27ae60;
	color: #fff;
}

input[type="text"], select {
	width: calc(100% - 20px);
	padding: 8px;
	margin: 4px 0;
	border: 1px solid #555;
	border-radius: 4px;
	box-sizing: border-box;
	color: #333;
}

input[type="submit"] {
	width: 100%;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"]:hover {
	background-color: #2980b9;
}
</style>
</head>
<body>
	<form action="/database/bill/insert">
		<table border="1" cellpadding="5">
			<tr>
				<th>Email:</th>
				<td>${email}<input type="hidden" name="email" size="45"
					value="${email}" onfocus="this.value=''" /></td>
			</tr>
			<tr>
				<th>Amount:</th>
				<td><input type="text" name="amount" size="45"
					value="${treeID}" onfocus="this.value=''" /></td>
			</tr>
			<tr>

				<th>Due Date:</th>
				<td><input type="datetime-local" name="dueDate" size="45"
					value="Email" onfocus="this.value=''" /></td>
			</tr>
			<tr>

				<th>Created On:</th>
				<td>${createdTime}<input type="hidden" name="createdTime"
					size="45" value="${createdTime}" onfocus="this.value=''" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Register" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
