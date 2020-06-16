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
			<textarea class="textArea" rows="15">
program p 
type t = integer; 
var t v1; 
char v2; 
begin 
read(v1); 
v1:=v1+10; 
write(v1) 
end. 
			</textarea>
		</div>
		
				<div class="block">
			<textarea class="textArea" rows="15">
program bubble 
var integer i,j,num; 
	array [1..20] of integer a; 
procedure q(integer num); 
var integer i,j,k; 
	integer t; 
begin 
	i:=1; 
	while i < num do 
	j:=num-i+1; 
	k:=1; 
		while k < j do 
			if a[k+1] < a[k]   
			then    
				t:=a[k]; 
				a[k]:=a[k+1]; 
				a[k+1]:=t 
			else temp:=0 
			fi;    
		k:=k+1 
		endwh; 
	i:=i+1 
  	endwh;
end 
begin 
	read(num); 
	i:=1; 
	while i<(num+1) do 
		read(j); 
		a[i]:=j; 
		i:=i+1 
	endwh; 
	q(num); 
	i:=1; 
	while i<(num+1) do  
		write(a[i]); 
		i:=i+1 
	endwh;
end.
			</textarea>
		</div>
		
		<div class="block">
			<textarea class="textArea" rows="15">
program p 
type t = integer; 
var t v1; 
char v2; 
begin 
read(v1); 
v1:=v1+10; 
write(v1) 
end. 
			</textarea>
		</div>

		<div class="block">
			<textarea class="textArea" rows="15">
program _p
type t=integer;
var t v1;
	char v2;
begin
	read(v1);
	v1:=v1*10;
	v1:="d";
	v2:='a';
	write(v1);
end.
			</textarea>
		</div>
		
		<div class="block">
			<textarea class="textArea" rows="15">
program p 
type t = integer ; 
var t v1; }
	char v2; 
begin 
read(v1); 
	v1:=v1+10; 
	write(v1) 
end. 
			</textarea>
		</div>
		
		<div class="block">
			<textarea class="textArea" rows="15">
program p 
type t = integer ; 
var t v1;
	char v2:='a';
begin
read(v1); 
	v1:=v1+10; 
	write(v1) 
end. 
			</textarea>
		</div>
		
		<div class="block">
			<textarea class="textArea" rows="15">
program p 
type t = integer ; 
var t v1;
	char v2; 
begin
read(v1); 
	v1:=v1+10; 
	write(v1) 
end..
			</textarea>
		</div>
		
		<div class="block">
			<textarea class="textArea" rows="15">
program p 
type t = integer ; 
var t v1;
var v1;
	char v2; 
begin
read(v1); 
	v1:=v1+10; 
	write(v1) 
end. 
			</textarea>
		</div>

	</div>
	<footer>
		<p id="copyright"></p>
  	</footer>
	<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
	<script type="text/javascript">
		var today = new Date();
    	$("#copyright").text("© " + today.getFullYear() + " Yulin Zhang.");
	</script>
</body>
</html>