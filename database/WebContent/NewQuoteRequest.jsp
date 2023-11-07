<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>New Quote Request</title>
</head>
<body>
	<div align="center">
		<p>${errorOne }</p>
		<p>${errorTwo }</p>
		<form action="/database/quoteRequest/insert">
			<table border="1" cellpadding="5">
				<tr>
					<th>Email:</th>
					<td align="center" colspan="3"><input type="text" name="email"
						size="45" value="Email" onfocus="this.value=''"></td>
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
							<option value="NEW_QUOTE">NEW_QUOTE</option>
							<option value="RESPOND_TO_EXISTING_QUOTE">RESPOND_TO_EXISTING_QUOTE</option>
					</select></td>
				</tr>
				<tr>
					<td align="center" colspan="5"><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>