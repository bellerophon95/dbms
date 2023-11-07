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
		<form action="/database/quoteRequest/update">
			<table border="1" cellpadding="5">
				<caption>
					<h2>List of Quote Requests</h2>
				</caption>
				<tr>
					<th>Email</th>
					<th>Created On</th>
					<th>Description</th>
					<th>Comment</th>
					<th>User Comment</th>
					<th>Request Type</th>
					<th>Status</th>

				</tr>
				<c:forEach var="quoteRequest" items="${quoteRequests}">
					<tr style="text-align: center">
						<td><c:out value="${quoteRequest.email}" /></td>
						<td><c:out value="${quoteRequest.createdOn}" /></td>
						<td><c:out value="${quoteRequest.description}" /></td>
						<td><c:out value="${quoteRequest.comment}" /></td>
						<td><c:out value="${quoteRequest.userComment}" /></td>
						<td><c:out value="${quoteRequest.requestType}" /></td>
						<td><c:out value="${quoteRequest.status}" /></td>
						<td><a
							href="/database/quoteRequest/update?createdOn=${quoteRequest.createdOn}
						&email=${quoteRequest.email}&description=${quoteRequest.description}&requestType=${quoteRequest.requestType}&status=${quoteRequest.status}&comment=${quoteRequest.comment}&userComment=${quoteRequest.userComment}">Update</a></td>
						<td><c:if
								test="${quoteRequest.status!='REJECTED' && quoteRequest.status!='REFILE'}">
								<a
									href="/database/quoteRequest/finalize?createdOn=${quoteRequest.createdOn}
						&email=${quoteRequest.email}&description=${quoteRequest.description}&requestType=${quoteRequest.requestType}&status=${quoteRequest.status}&comment=${quoteRequest.comment}&userComment=${quoteRequest.userComment}">Finalize
									Quote</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<!-- 		<a href="/database/NewQuoteRequest.jsp" target="_self">Raise new
			request</a> -->
		<a href="/database/quote" target="_self">Go to quotes</a>
	</div>
</body>
</html>