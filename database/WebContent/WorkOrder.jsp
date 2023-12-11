<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<title>Quote Requests</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212;
	color: #eee;
	margin: 0;
	padding: 0;
}

.actionButton {
	display: inline-block;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	background-color: #e74c3c; /* Red color for the button */
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.actionButton:hover {
	background-color: #c0392b; /* Darker shade for hover effect */
}

.actionButton a {
	color: inherit;
	text-decoration: none;
}

.button {
	margin: 5px;
	display: inline-block;
	background-color: #3498db;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
	transition: background-color 0.3s, color 0.3s;
}

.button:hover {
	background-color: #2980b9;
}

.container {
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 20px;
}

table {
	border-collapse: collapse;
	width: 80%;
	margin-top: 20px;
	border: 1px solid #444;
}

table caption {
	font-size: 1.5em;
	text-align: center;
	margin-bottom: 10px;
}

table th, table td {
	border: 1px solid #444;
	padding: 8px;
	text-align: center;
	color: #fff;
}

table th {
	background-color: #2ecc71; /* Green color for table header */
	color: white; /* Black text color */
}

.button:hover {
	background-color: #2980b9;
}

/* Centering Quote Requests */
.quoteRequests {
	display: block;
	text-align: center;
	margin-top: 20px;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<table>
			<caption>List of Work Orders</caption>
			<tr>
				<th>Email</th>
				<th>Created On</th>
				<th>Tree ID</th>
				<th>Due Date</th>
				<th>Description</th>
				<th>Order Status</th>
				<th>Completed On</th>
			</tr>
			<c:forEach var="workOrder" items="${workOrders}">
				<tr>
					<td><c:out value="${workOrder.email}" /></td>
					<td><c:out value="${workOrder.createdOn}" /></td>
					<td><c:out value="${workOrder.treeID}" /></td>
					<td><c:out value="${workOrder.dueDate}" /></td>
					<td><c:out value="${workOrder.description}" /></td>
					<td><c:out value="${workOrder.orderStatus}" /></td>
					<td><c:out value="${workOrder.completedOn}" /></td>


					<td><c:if test="${not empty workOrder.completedOn}">
							<a class="actionButton"
								href="/database/bill/new?email=${workOrder.email}
						&createdOn=${workOrder.createdOn}&treeID=${workOrder.treeID}&dueDate=${workOrder.dueDate}&orderStatus=${workOrder.orderStatus}&description=${workOrder.description}&completedOn=${workOrder.completedOn}">Create
								Bill</a>
						</c:if></td>

				</tr>
			</c:forEach>
		</table>
	</div>
	<a class="button quoteRequests" href="/database/quoteRequest"
		target="_self">Back to Quote requests Home</a>
</body>
</html>
