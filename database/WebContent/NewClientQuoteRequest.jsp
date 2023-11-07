<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
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
				</tr>
				<tr>
					<th>Description:</th>
					<td align="center" colspan="3"><input type="text"
						name="description" size="45" value="Example Description"
						onfocus="this.value=''"></td>
				</tr>
				<tr>
				<tr>
					<th>Request Type:</th>
					<td align="center" colspan="3"><select name="requestType"
						style="margin-left: 5rem">
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