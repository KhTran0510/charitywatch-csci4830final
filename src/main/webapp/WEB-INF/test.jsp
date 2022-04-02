<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>

	Foundation Name: <input type="text" name="first"> 
	<input type="submit" value="Search"/>
	<br/>

	<input type="radio" id="donor" name="acctype" value=${first} checked>
		<label for=${first}> ${first} </label>
		<br>
	<input type="radio" id="foundation" name="acctype" value=${last}>
 		<label for=${last}>${last}</label>
 		<br>


	
</body>
</html>