<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<form class="form-signin" method="post" action="/wwm/login">
			<h2 class="form-signin-heading">Please sign in</h2>
			<p>
				<label for="username" class="sr-only">Username</label> <input
					type="text" id="username" name="username" class="form-control"
					placeholder="Username" required autofocus>
			</p>
			<p>
				<label for="password" class="sr-only">Password</label> <input
					type="password" id="password" name="password" class="form-control"
					placeholder="Password" required>
			</p>
			<input name="_csrf" type="hidden"
				value="f714c7c6-f096-4584-afc4-2c97ad98d24a" />
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>
		<div>gkgk</div>
	</div>
</body>
</html>