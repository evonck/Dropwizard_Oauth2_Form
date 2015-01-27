<!doctype html>
<html lang="fr">

<head>
	<#include "head.ftl">
</head>

<body id="bodyhome">
	<#include "navbar.ftl">
	<div class="formUsertemplate">
		<div class="register">
			<h2>Inscription</h2>
		</div>
		<form role="form" class="formUser" id="FormAddUser" >
	    		<div class="form-field">
	      			<input type="text" class="form-input" id="username" name="username" placeholder="Username">
	    			<div class="ImageForm" data-toggle="modal" data-target="#AddDay">
						<img src="/img/username.png" >
					</div>
	    		</div>
	    		<div class="form-field">
	      			<input type="email" class="form-input" id="email" name="email" placeholder="Email">
	  		  		<div class="ImageForm">
						<img src="/img/email.png" >
					</div>
	    		</div>
	    		<div class="form-field">
	      			
	      			<input type="password" class="form-input" id="pass" name="pass" placeholder="Password">
	      			<div class="ImageForm" >
						<img src="/img/password.png" >
					</div>
	    		</div>
	    		<div class="form-field">
	      			<input type="password" class="form-input" id="pass2" name="pass2" placeholder="Confirm Password">
	      			<div class="ImageForm">
						<img src="/img/password.png" >
					</div>
	    		</div>	
	    		
					<button type="button" onclick="submitFormAddUser();" class="submit-form">Submit</button>
				
	  		</form>
	  	</div> 	     	
		
</body>
 <!-- Placed at the end of the document so the pages load faster -->
   	 <script src="/js/home.js"></script>
    <script src="/js/form2js.js"></script>
    <script src="/js/navbar.js"></script>
    
    
</html>
