<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
<style>
/* Existing styles */
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

/* New styles for the form */
form {
	margin-top: 20px;
	text-align: center;
}

table {
	border-collapse: collapse;
	width: 50%; /* Adjust the width as needed */
	margin: 20px auto;
}

th, td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ddd;
}

th {
	background-color: #27ae60; /* Green color for table headers */
	color: #fff;
}

input[type="text"], select {
	width: calc(100% - 20px); /* Adjust input width */
	padding: 8px;
	margin: 4px 0;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type="submit"] {
	display: block;
	margin: 20px auto;
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

a {
	display: block;
	margin-top: 20px;
	text-decoration: none;
	color: #3498db;
	transition: color 0.3s;
}

a:hover {
	color: #2980b9;
}

.blueButton {
	display: inline-block;
	margin-top: 20px;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #3498db; /* Blue shade */
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.blueButton:hover {
	background-color: #2980b9;
}

.redButton {
	display: block;
	margin: 20px auto;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #e74c3c !important;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.redButton:hover {
	background-color: #c0392b;
}
</style>
</head>
<body>
	<div align="center">
		<p>${errorOne }</p>
		<form action="/database/quoteRequest/update">
			<input type="hidden" name="email" size="45" value="${email}"
				onfocus="this.value=''"> <input type="hidden"
				name="createdOn" size="45" value="${createdOn}"
				onfocus="this.value=''">
			<table border="1" cellpadding="5">
				<tr>
					<th>Description:</th>
					<td colspan="3"><input type="text" name="description"
						size="45" value="${description}" onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Comment:</th>
					<td colspan="3"><input type="text" name="comment" size="45"
						value="${comment}" onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>User Comment:</th>
					<td colspan="3"><span style="font-weight: 700"><c:out
								value="${userComment}" /> </span><input type="hidden"
						name="userComment" size="45" value="${userComment}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Status:</th>
					<td colspan="3"><span style="font-weight: 700"><c:out
								value="${status}" /> </span> <select name="status">
							<option value="REJECTED">REJECTED</option>
							<option value="UNDER_CONSIDERATION">UNDER_CONSIDERATION</option>
							<option value="UNDER_DISCUSSION">UNDER_DISCUSSION</option>
							<option value="REFILE">REFILE</option>
							<option value="PENDING">PENDING</option>
					</select></td>
				</tr>
				<tr>
					<th>Request Type:</th>
					<td colspan="3"><span style="font-weight: 700"><c:out
								value="${requestType}" /> </span> <input type="hidden"
						name="requestType" size="45" value="${requestType}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						class="redButton" value="Register" /></td>
				</tr>
			</table>
		</form>
		<a href="/database/quoteRequest" class="blueButton">List Quote
			Requests</a>
	</div>
</body>
</html>
