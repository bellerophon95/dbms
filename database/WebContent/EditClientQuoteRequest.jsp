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
		<form action="/database/client/quoteRequest/update">
			<input type="hidden" name="email" size="45" value="${email}"
				onfocus="this.value=''"> <input type="hidden"
				name="createdOn" size="45" value="${createdOn}"
				onfocus="this.value=''">
			<table border="1" cellpadding="5">
				<tr>
					<th>Comment:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${comment}" /> </span><input type="hidden" name="comment"
						size="45" value="${comment}" onfocus="this.value=''">
				</tr>
				<tr>
					<th>Description:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${description}" /> </span><input type="hidden"
						name="description" size="45" value="${description}"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>User Comment:</th>
					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${userComment}" /> </span><input type="text" name="userComment"
						size="45" value="${userComment}" onfocus="this.value=''"></td>
				</tr>
				<tr>
					<th>Request Type:</th>

					<td align="center" colspan="3"><span style="font-weight: 700"><c:out
								value="${requestType}" /> </span><select name="requestType"
						style="margin-left: 5rem">
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
		<a href="/database/client/quoteRequest">List Quote Requests</a>>
	</div>
</body>