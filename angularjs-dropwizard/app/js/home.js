//------------------------------------------------------
//Add User
//------------------------------------------------------

function submitFormAddUser(){
	if ($( "#FormAddUser" ).valid()) // check if form is valid
	{
		var jsonData = form2js('FormAddUser', '.', true);
		var key = "pass2";
		delete jsonData[key]; 
		$.ajax({
			url: '/users',
			type: 'POST',
			data : JSON.stringify(jsonData),
			processData : false,
			dataType: 'JSON',
			contentType: 'application/json',
			success: function(retour) {
				saveTokens(retour);
				document.location.href="/users/user_Id"
			}
		})
	}
}
function test(){
	document.location.href="/users/user_Id" 
}

$(document).ready(function(){
	var test= document.cookie;
	//------------------------------------------------------
	//Validate the add Day form
	//------------------------------------------------------
	
	$('#FormAddUser').validate({ // initialize the plugin
		rules: {
			username: {
				required: true,
				minlength: 3	
			},
			pass:{
				required:true,
				minlength: 6,
				passCheck:true
			},
			email:{
				required:true
			},
			pass2:{
				required:true,
				equalTo:"#pass"
			}
		},
		messages:{
			username:{
				required:"Ce champs est obligatoire",
				minlength:"Le username doit faire au moins 3 caractere"
			},
			pass:{
				required:"Ce champs est obligatoire",
				minlength:"Le mot de passe doit faire au moins 6 caractere"
			},
			email:{
				required:"Ce champs est obligatoire"
			},
			pass2:{
				required:"Ce champs est obligatoire",
				equalTo:"Les mots de passes ne correspondent pas"
			}
			
		},
		highlight: function(element) {
			$(element).closest('.form-input').addClass('error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-input').removeClass('error');
		},
		errorElement: 'span',
		errorClass: 'help-block',
		errorPlacement: function(error, element) {
			if(element.parent().attr("class") == 'form-field') {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
		
	});
	
	//------------------------------------------------------
	//Password strenght
	//------------------------------------------------------
	
	$.validator.addMethod("passCheck",
			function(value, element) {
				return /^[A-Za-z0-9!@#$%^&*()_]+$/.test(value)&& /\d/.test(value);
			},
			"Le mot de passe doit contenir au moins une lettre et un caractere");

})
	