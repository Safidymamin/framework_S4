<%@ page import="models.Emp" %>
<%
    Emp emp = (models.Emp) request.getAttribute("models.Emp");
    String nom = (String) request.getAttribute("nom");
    String prenom = (String) request.getAttribute("prenom");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>Sprint 7: Input - Recuperation de donnees</h2>
    <p><%= nom %> <%= prenom %></p>
    <p>Classe: <%= emp.getNom() %></p>
</body>
</html>