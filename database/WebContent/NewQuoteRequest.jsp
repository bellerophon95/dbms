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
}

/* Center the form */
.container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

/* Form styles */
table {
	width: 50%;
	border-collapse: collapse;
	margin: 20px auto;
	background-color: #333;
	color: #f2f2f2;
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
	<div class="container">
		<form action="/database/quoteRequest/insert">
			<table border="1" cellpadding="5">
				<tr>
					<th>Email:</th>
					<td colspan="3"><input type="hidden" name="email" size="45"
						value="${email}" /> <input type="hidden" name="createdOn"
						size="45" value="${createdOn}" /></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td colspan="3"><input type="text" name="email" size="45"
						value="Email" onfocus="this.value=''" /></td>
				</tr>
				<tr>
					<th>Description:</th>
					<td colspan="3"><input type="text" name="description"
						size="45" value="Example Description" onfocus="this.value=''" /></td>
				</tr>
				<tr>
					<th>Request Type:</th>
					<td colspan="3"><select name="requestType">
							<option value="NEW_QUOTE">NEW_QUOTE</option>
							<option value="RESPOND_TO_EXISTING_QUOTE">RESPOND_TO_EXISTING_QUOTE</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
