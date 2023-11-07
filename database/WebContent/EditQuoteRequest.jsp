<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
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
					<td align="center" colspan="3"><input type="text"
						name="description" size="45" value="${description}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Comment:</th>
					<td align="center" colspan="3"><input type="text"
						name="comment" size="45" value="${comment}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>User Comment:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${userComment}" /> </span><input type="hidden" name="userComment"
						size="45" value="${userComment}" onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Status:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${status}" /> </span> <select name="status"
						style="margin-left: 5rem">
							<option value="REJECTED">REJECTED</option>
							<option value="UNDER_CONSIDERATION">UNDER_CONSIDERATION</option>
							<option value="UNDER_DISCUSSION">UNDER_DISCUSSION</option>
							<option value="REFILE">REFILE</option>
							<option value="PENDING">PENDING</option>
					</select></td>
				</tr>
				<tr>
					<th>Request Type:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${requestType}" /> </span> <input type="hidden"
						name="requestType" size="45" value="${requestType}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</form>
		<a href="/database/quoteRequest">List Quote Requests</a>>
	</div>
</body>