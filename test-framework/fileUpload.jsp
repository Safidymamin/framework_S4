<%@ page import="models.Emp" %>
<%@ page import="etu2033.framework.FileUpload" %>
<%
    Emp emp = (models.Emp) request.getAttribute("models.Emp");
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Upload</title>
</head>
<body>
    <h2>Sprint 9: FileUpload</h2>
    <p>FileName: <%= emp.getFile().getName() %></p>
</body>
</html>