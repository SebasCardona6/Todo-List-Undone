<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar/Editar Tarea</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Agregar/Editar Tarea</h2>

    <%-- Muestra mensajes de error --%>
    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger">${requestScope.error}</div>
    </c:if>

    <%-- Formulario para agregar/editar tarea --%>
    <form method="post" action="tasks">
        <c:if test="${not empty param.taskId}">
            <input type="hidden" name="taskId" value="${param.taskId}">
            <input type="hidden" name="_method" value="PUT">
        </c:if>

        <div class="form-group">
            <label for="name">Nombre de la Tarea:</label>
            <input type="text" class="form-control" id="name" name="name" required
                   value="${not empty param.name ? param.name : ''}">
        </div>

        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="completed" name="completed"
                   ${param.completed == 'true' ? 'checked' : ''}>
            <label class="form-check-label" for="completed">Completada</label>
        </div>

        <button type="submit" class="btn btn-primary">Guardar</button>
        <a href="tasks" class="btn btn-secondary">Cancelar</a>
    </form>
</div>

</body>
</html>