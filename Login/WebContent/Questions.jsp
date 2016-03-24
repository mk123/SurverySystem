<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" "http://www.w3.org/TR/html5/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questions for Survey.</title>
</head>
<body>

	<form method="post" action="SaveQuestions" name="form1">
		<table>
			<tr>
				<td>Question Number: <%
					out.println(session.getAttribute("QuestionNumber"));
				%></td>
			</tr>
			<tr>
				<td><textarea placeholder="Enter Your Question Here...."
						rows="4" cols="100%" name="question"></textarea>
			</tr>
			<tr>
				<td>Enter the Options Here:</td>
			</tr>
			<tr>
				<td><input type="text" name="option_1"
					placeholder="Enter first Option"><b>*</b></td>
			</tr>
			<tr>
				<td><input type="text" name="option_2"
					placeholder="Enter second Option"><b>*</b></td>
			</tr>
			<tr>
				<td><input type="text" name="option_3"
					placeholder="Enter third Option"></td>
			</tr>
			<tr>
				<td><input type="text" name="option_4"
					placeholder="Enter fourth Option"></td>
			</tr>
			<tr>
				<td><input type="text" name="option_5"
					placeholder="Enter fifth Option"></td>
			</tr>
			
		</table>
		<table>
		<tr>
			<td ><input type="submit" value="Add" name="Add"></td>
			<td ><input type="submit" value="Finish" name="Finish"></td>
		</tr>
		</table>
	</form>
</body>
</html>