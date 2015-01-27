<nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Dropwizard_Oauth2_Form</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li id="li_home"><a href="/">Home</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
        	<li class="dropdown">
          <a  class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Login <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
             <form role="form" id="LoginUserNav" >
		    		<div class="form-field">
		      			<input type="email" class="form-input" id="email" name="email" placeholder="Email">
		  		  		<div class="ImageForm" data-toggle="modal" data-target="#AddDay">
							<img src="/img/email.png" >
						</div>
		    		</div>
		    		<div class="form-field">	
		      			<input type="password" class="form-input" id="pass_nav" name="pass_nav" placeholder="Password">
		      			<div class="ImageForm" data-toggle="modal" data-target="#AddDay">
							<img src="/img/password.png" >
						</div>
		    		</div>
		  		</form>
            <li class="divider"></li>
            <li>
            	<button type="button" onclick="test();" class="submit-form-nav">Submit</button>
			</li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
