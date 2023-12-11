<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
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

p {
	color: #f44336;
	font-weight: bold;
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
	border: 1px solid #ddd;
}

th, td {
	padding: 8px;
	border: 1px solid #ddd;
}

th {
	background-color: #27ae60;
	color: #fff;
}

td[colspan="3"] {
	text-align: center;
	font-weight: 700;
}

select {
	margin-left: 5rem;
	background-color: white;
	color: black;
	padding: 8px;
	border: 1px solid #ddd;
}

input[type="submit"] {
	display: block;
	margin: 0 auto;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	background-color: #3498db;
	color: #fff;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"]:hover {
	background-color: #2980b9;
}

a {
	display: block;
	width: fit-content;
	margin: 20px auto;
	padding: 10px 20px;
	text-decoration: none;
	color: #1ca6fc;
	transition: color 0.3s;
}

a:hover {
	color: #00bcd4;
}
</style>
</head>
<body>
	<div align="center">
		<p>${errorOne}</p>
		<form action="/database/client/quoteRequest/update">
			<input type="hidden" name="email" size="45" value="${email}"
				onfocus="this.value=''" /> <input type="hidden" name="createdOn"
				size="45" value="${createdOn}" onfocus="this.value=''" />
			<table border="1" cellpadding="5">
				<tr>
					<th>Comment:</th>
					<td align="center" colspan="3"><c:out value="${comment}" /></td>
					<input type="hidden" name="comment" size="45" value="${comment}"
						onfocus="this.value=''" />
				</tr>
				<tr>
					<th>Description:</th>
					<td align="center" colspan="3"><c:out value="${description}" /></td>
					<input type="hidden" name="description" size="45"
						value="${description}" onfocus="this.value=''" />
				</tr>
				<tr>
					<th>User Comment:</th>
					<td align="center" colspan="3"><input type="text"
						name="userComment" size="45" value="${userComment}"
						onfocus="this.value=''" /></td>
				</tr>
				<tr>
					<th>Request Type:</th>
					<td align="center" colspan="3"><select name="requestType">
							<option value="RESCHEDULE">RESCHEDULE</option>
							<option value="RELOCATE">RELOCATE</option>
							<option value="NEGOTIATE_PRICE">NEGOTIATE_PRICE</option>
							<option value="NEW_REQUEST">NEW_REQUEST</option>
							<option value="USER_ACCEPTS_QUOTE">USER_ACCEPTS_QUOTE</option>
					</select></td>
				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</form>
		<a href="/database/client/quoteRequest">List Quote Requests</a>
	</div>
</body>
</html>
