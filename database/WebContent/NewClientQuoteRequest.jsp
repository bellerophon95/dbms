<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

/* ... other existing styles ... */

/* New styles for the form */
form {
	margin-top: 20px;
	text-align: center;
}

table {
	border-collapse: collapse;
	width: 80%;
	margin: 20px auto;
	border: 1px solid #ddd;
}

th, td {
	padding: 8px;
	text-align: left;
	border: 1px solid #ddd;
}

th {
	background-color: #27ae60; /* Green color for table headers */
	color: #fff;
}

input[type="text"], select {
	width: calc(100% - 18px); /* Adjusted width to accommodate the border */
	padding: 8px;
	margin: 4px 0;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

select {
	margin-left: 0; /* Removed inline margin */
}

input[type="submit"] {
	display: block;
	width: fit-content;
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
</style>
</head>
<body>
	<div align="center">
		<p>${errorOne }</p>
		<p>${errorTwo }</p>
		<form action="/database/client/quoteRequest/insert">
			<table border="1" cellpadding="5">
				<tr>
					<th>Email:</th>
					<td><input type="hidden" name="email" size="45"
						value="${client.email}" onfocus="this.value=''"> <c:out
							value="${client.email}" /></td>
					<td><input type="hidden" name="email" size="45"
						value="${client.email}" onfocus="this.value=''"> <c:out
							value="${client.email}" /></td>
				</tr>
				<tr>
					<th>Distance of Tree from House:</th>
					<td align="center" colspan="3"><input type="text"
						name="distOfTreeToHouse" size="45" value="Distance in Meters"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Height of Tree:</th>
					<td align="center" colspan="3"><input type="text"
						name="treeHeight" size="45" value="Height of Tree in Meters"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Proposed Price:</th>
					<td align="center" colspan="3"><input type="text"
						name="proposedPrice" size="45" value="Height of Tree in Meters"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Tree location:</th>
					<td align="center" colspan="3"><input type="text"
						name="treeLocation" size="45" value="Enter Address"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Description:</th>
					<td align="center" colspan="3"><input type="text"
						name="description" size="45" value="Example Description"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Request Type:</th>
					<td align="center" colspan="3"><select name="requestType">
							<option value="RESCHEDULE">RESCHEDULE</option>
							<option value="RELOCATE">RELOCATE</option>
							<option value="NEGOTIATE_PRICE">NEGOTIATE_PRICE</option>
							<option value="NEW_REQUEST">NEW_REQUEST</option>
					</select></td>
				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						value="Submit" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>