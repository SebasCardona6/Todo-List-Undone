<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Tareas</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Lista de Tareas</h2>

    <%-- Muestra mensajes de error --%>
    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger">${requestScope.error}</div>
    </c:if>

    <%-- Muestra la lista de tareas --%>
    <c:if test="${not empty requestScope.tasks}">
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Completada</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${requestScope.tasks}">
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.name}</td>
                        <td>${task.completed ? 'Sí' : 'No'}</td>
                        <td>
                            <a href="tasks/edit?taskId=${task.id}">Editar</a>
                            <form method="post" action="tasks" style="display: inline;">
                                <input type="hidden" name="taskId" value="${task.id}">
                                <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" class="btn-link">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div>
        <a href="addEditTask.jsp">Agregar Tarea</a>
    </div>
</div>

</body>
</html>