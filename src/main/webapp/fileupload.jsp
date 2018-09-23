
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Servlet File Upload/Download</title>
	    
	    <link rel="stylesheet" href="main.css" />

	</head>
	<body bgcolor="#f5f5dc">
	<%
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("u")) userName = cookie.getValue();
				if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();

			}
		}
		if(userName == null) response.sendRedirect("login.html");
	%>
	<h3>Welcome <%=userName %>, login successful.Your Session ID=<%=sessionID %></h3>
	<br>

	<div class="panel">
	        <h1>File Upload</h1>
	        <h3>Press 'CTRL' Key+Click On File To Select Multiple Files in Open Dialog</h3>
	        <form id="fileUploadForm" method="post" action="UploadServlet" enctype="multipart/form-data">
	            <div class="form_group">
	                <label>Upload File</label><span id="colon">: </span><input id="fileAttachment" type="file" name="fileUpload" multiple="multiple" />
	                <span id="fileUploadErr">Please Upload A File!</span>
	            </div>
	            <button id="uploadBtn" type="submit" class="btn btn_primary">Upload</button>
	        </form>
	    </div>

		<!-- List All Uploaded Files -->
		<div class="panel">
			<a id="allFiles" class="hyperLink" href="<%=request.getContextPath()%>/uploadedFilesServlet">List all uploaded files</a>
		</div>


		<!-- User logged -->
		<%--<div class="panel">--%>
			<%--<a id="User" class="hyperLink" href="<%=request.getAttribute("user")%>/login">User logged</a>--%>
		<%--</div>--%>

		<%--<td>Username: </td>--%>
		<%--<td><input type="text" value="<% String username = (String)request.getSession().getAttribute(display); %>" /></td>--%>






		<p>
			<a href="logout">Logout</a>
		</p>

	</body>
</html>