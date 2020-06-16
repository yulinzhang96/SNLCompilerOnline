<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./styles.css" rel="stylesheet" type="text/css">
<title>Small Nested Language Compiler</title>
<style>
.btn {
	margin-top: 20px;
	margin-bottom: 5px;
}
</style>
</head>
<body>
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
			<a href="samples.jsp" target="_blank">SNL Program Samples</a>
		</div>
		
		<div class="block">
			<form action="tokenize.jsp" method="post">
				<button class="btn">Tokenize</button>
				<button class="btn" id="clear">Clear</button>
				<h3>Source Code Editor</h3>
				<textarea class="textArea" id="input" rows="15" name="sourceCode" autofocus required placeholder="Type your source code here..."></textarea>
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
    	
    	$("#clear").click(function() {
    		$("#input").val("");
    	});
	</script>
</body>
</html>