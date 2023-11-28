/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servlet;

import com.mycompany.model.Task;
import com.mycompany.util.JsonFileHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    String taskId = request.getParameter("taskId");
    String name = request.getParameter("name");
    boolean completed = Boolean.parseBoolean(request.getParameter("completed"));

    if (taskId != null && name != null) {
        List<Task> tasks = JsonFileHandler.readTasksFromFile();

        if (!taskId.equals("new")) {
            // Edición de tarea existente
            for (Task task : tasks) {
                if (task.getId().equals(taskId)) {
                    task.setName(name);
                    task.setCompleted(completed);
                    break;
                }
            }
        } else {
            // Nueva tarea
            String newTaskId = UUID.randomUUID().toString();
            Task newTask = new Task(newTaskId, name, completed);
            tasks.add(newTask);
        }

        JsonFileHandler.writeTasksToFile(tasks);
    } else {
        // Manejo de error o mensaje de validación
        request.setAttribute("error", "La identificación de la tarea o el nombre no pueden estar vacíos.");
    }

    response.sendRedirect(request.getContextPath() + "/tasks");
}

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String taskId = request.getParameter("taskId");
        String name = request.getParameter("name");
        boolean completed = Boolean.parseBoolean(request.getParameter("completed"));

        if (taskId != null && name != null) {
            List<Task> tasks = JsonFileHandler.readTasksFromFile();

            for (Task task : tasks) {
                if (task.getId().equals(taskId)) {
                    task.setName(name);
                    task.setCompleted(completed);
                    break;
                }
            }

            JsonFileHandler.writeTasksToFile(tasks);
        } else {
            // Manejo de error o mensaje de validación
            request.setAttribute("error", "La identificación de la tarea o el nombre no pueden estar vacíos.");
        }

        response.sendRedirect(request.getContextPath() + "/tasks");
    }

// Lógica para búsqueda
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String query = request.getParameter("query");
    List<Task> tasks;

    if (query != null && !query.isEmpty()) {
        tasks = searchTasks(query);
    } else {
        tasks = JsonFileHandler.readTasksFromFile();
    }

    request.setAttribute("tasks", tasks);
    request.getRequestDispatcher("/taskList.jsp").forward(request, response);
}

private List<Task> searchTasks(String query) throws IOException {
    List<Task> allTasks = JsonFileHandler.readTasksFromFile();
    List<Task> matchingTasks = new ArrayList<>();

    for (Task task : allTasks) {
        if (task.getName().toLowerCase().contains(query.toLowerCase())) {
            matchingTasks.add(task);
        }
    }

    return matchingTasks;
}

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String taskId = request.getParameter("taskId");

        if (taskId != null) {
            List<Task> tasks = JsonFileHandler.readTasksFromFile();
            tasks.removeIf(task -> task.getId().equals(taskId));
            JsonFileHandler.writeTasksToFile(tasks);
        }

        response.sendRedirect(request.getContextPath() + "/tasks");
    }

}
