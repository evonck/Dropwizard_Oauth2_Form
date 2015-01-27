//------------------------------------------------------
//Login
//------------------------------------------------------

function submitFormLoginUser(){
	if ($( "#LoginUserNav" ).valid()) // check if form is valid
    {
		var jsonData = form2js('LoginUserNav', '.', true);
		$.ajax({
			url: '/login',
			type: 'POST',
			data : JSON.stringify(jsonData),
			processData : false,
			dataType: 'JSON',
			contentType: 'application/json',
			success: function(retour) {
				
			}
		})
    }
}
//------------------------------------------------------
//Switch navbar active
//------------------------------------------------------

function GetCurrentPageName() { 
	//method to get Current page name from url. 
	var PageURL = document.location.href; 
	var PageName = PageURL.substring(PageURL.lastIndexOf('/') + 1); 
	 
	return PageName.toLowerCase() ;
	}
	$(document).on('click', '.dropdown-menu', function (e) {
		 e.stopPropagation(); // This replace if conditional.
	}); 
	
	$(document).ready(function(){
	var CurrPage = GetCurrentPageName();
	switch(CurrPage){
	case '':
	 $('#li_home').addClass('active') ;
	 break;
	case 'days':
	 $('#li_days').addClass('active') ;
	 break;
	
	}
	
	//------------------------------------------------------
	//Validate the add Login form
	//------------------------------------------------------

	
	$('#LoginUserNav').validate({ // initialize the plugin
	    rules: {
	        pass:{
	        	required:true,
	        	},
	        email:{
	        	required:true
	        }
	    },
	    messages:{
	    	pass:{
	    		required:"Ce champs est obligatoire",
	    		},
	    	email:{
	    		required:"Ce champs est obligatoire"
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

});