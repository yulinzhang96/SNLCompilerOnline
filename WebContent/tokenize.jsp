<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.resources.compiler.analyzeToken, com.resources.compiler.createToken" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./styles.css" rel="stylesheet" type="text/css">
<title>Small Nested Language Compiler</title>
<style>
.display {
	margin: 0 auto 20px;
	width: 80%;
	height: 40px;
	border-radius: 10px;
}
p {
	padding: 10px;
}
h3 {
	margin-top: 0;
}
</style>
</head>
<body>

	<% 
	String sourceCode = request.getParameter("sourceCode");
	createToken.initialize();
	boolean analyzedResult = analyzeToken.analyzeToken(sourceCode);
	String tokenizedResult = createToken.tokenDisplay.toString();
	%>
	<h1>Small Nested Language Compiler</h1>
	<div class="container">
		<div class="block">
			<div class="description">
				<h3>Small Nested Language Introduction</h3>
				<ul>
					<li>Small Nested Language (SNL) is a nested programming language and a procedural language for teaching purpose.</li>
					<li>The language has the standard data types and structures of data type.</li>
					<li>It allows recursive call, and parameters of the process can be divided into a reference parameter and a variant parameter.</li> 
					<li>The control statements of SNL are essentially the same as Pascal.</li>
					<li>Source: <a href="https://baike.baidu.com/item/SNL/10418" target="_blank">Baidu</a></li>
				</ul>
			</div>
		</div>
		
		<div class="block">
			<div class="display"></div>
			<form id="form" action="parseSyntax.jsp" method="post">
				<button class="btn" id="parse">Parse Syntax</button>
				<button class="btn" id="back">Back</button>
				<h3>Output of Lexical Analysis</h3>
				<textarea class="textArea" id="tokens" rows="15" name="tokens"><%= tokenizedResult %></textarea>
				<h3>Source Code</h3>
				<textarea class="textArea" id="input" rows="15" name="sourceCode"><%= sourceCode %></textarea>
			</form>
		</div>
	</div>
	<footer>
		<p id="copyright"></p>
  	</footer>
	<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script type="text/javascript">
		var today = new Date();
    	$("#copyright").text("© " + today.getFullYear() + " Yulin Zhang.");
    	
    	document.getElementById("tokens").readOnly = "true";
    	document.getElementById("input").readOnly = "true";
    	
    	$(document).ready(function() {
    		var result = "<%= analyzedResult %>";
        	if(result == "true") {
        		$(".display").css("background-color", "green");
        		//$("#parse").prop("disabled", false);
        		$(".display").append("<p>Lexical analysis was successful!</p>");
        	} else {
        		$(".display").css("background-color", "red");
        		//$("#parse").prop("disabled", true);
        		$(".display").append("<p>Lexical analysis has failed!</p>");
        	}
    	});
    	
    	$("#parse").click(function(event) {
    		if("<%= analyzedResult %>" == "false") {
    			alert("Lexical analysis has failed!");
    			event.preventDefault();
    		}
    	});
    	
    	$("#back").click(function(event) {
    		$("#form").attr("action", "index.jsp");
    	});
	</script>
</body>
</html>