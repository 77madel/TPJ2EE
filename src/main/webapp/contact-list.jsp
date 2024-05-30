<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Contacts</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

 <div class="container">
        <h2>Formulaire de Contact</h2>
        <form action="${contact == null ? 'insert' : 'update'}" method="post">
            <input type="hidden" name="id" value="${contact.id}">
            <div class="form-group">
                <label for="prenom">Prénom</label>
                <input type="text" required placeholder="Votre Prenom" class="form-control" id="prenom" name="prenom" value="${contact.prenom}">
            </div>
            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" required placeholder="Votre Nom" class="form-control" id="nom" name="nom" value="${contact.nom}">
            </div>
            <div class="form-group">
                <label for="telephone">Téléphone</label>
                <input type="text" required placeholder="Votre Telephone" class="form-control" id="telephone" name="telephone" value="${contact.telephone}">
            </div>
            <div class="form-group">
                <label for="competence">Compétence</label>
                <select class="form-control" name="competence">
                	 <option>-- Veuillez choisir competence --</option>
                	 <option value="Developpeur">Dévéloppeur</option>
                	 <option value="designer">Designer</option>
                	 <option value="Backend">Backend</option>
                </select>
               
            </div>
            <button type="submit" class="btn btn-primary">Soumettre</button>
        </form>
       </div>


    <div class="container">
        <h2>Liste des Contacts</h2>
<!--         <a href="new" class="btn btn-success">Ajouter un Nouveau Contact</a>
 -->        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Téléphone</th>
                    <th>Compétence</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contact" items="${listContact}">
                    <tr>
                        <td>${contact.id}</td>
                        <td>${contact.prenom}</td>
                        <td>${contact.nom}</td>
                        <td>${contact.telephone}</td>
                        <td>${contact.competence}</td>
                        <td>
                            <a href="edit?id=${contact.id}" class="btn btn-info">Modifier</a>
                            <a href="delete?id=${contact.id}" class="btn btn-danger">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
