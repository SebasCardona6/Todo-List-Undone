/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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
import java.util.List;

@WebServlet("/tasks/edit")
public class EditTaskServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskId = request.getParameter("taskId");

        if (taskId != null) {
            List<Task> tasks = JsonFileHandler.readTasksFromFile();
            Task taskToEdit = tasks.stream()
                    .filter(task -> task.getId().equals(taskId))
                    .findFirst()
                    .orElse(null);

            if (taskToEdit != null) {
                request.setAttribute("taskToEdit", taskToEdit);
                request.getRequestDispatcher("/addEditTask.jsp").forward(request, response);
            } else {
                // Manejo de error o redirigir a la lista de tareas
                response.sendRedirect(request.getContextPath() + "/tasks");
            }
        } else {
            // Manejo de error o redirigir a la lista de tareas
            response.sendRedirect(request.getContextPath() + "/tasks");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
}