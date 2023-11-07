<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All User list</title>
</head>
<body>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h2>List of Quotes</h2>
			</caption>
			<tr>
				<th>Email</th>
				<th>Created Time</th>
				<th>Description</th>
				<th>Status</th>
				<th>Request Type</th>

			</tr>
			<c:forEach var="quote" items="${quotes}">
				<tr style="text-align: center">
					<td><c:out value="${quote.email}" /></td>
					<td><c:out value="${quote.createdTime}" /></td>
					<td><c:out value="${quote.description}" /></td>
					<td><c:out value="${quote.status}" /></td>
					<td><c:out value="${quote.requestType}" /></td>
				</tr>
			</c:forEach>
		</table>
		<!-- <a href="/database/NewQuoteRequest.jsp" target="_self">Raise new request</a> -->
		<a href="/database/quoteRequest" target="_self">Quote requests</a>
	</div>
</body>
</html>