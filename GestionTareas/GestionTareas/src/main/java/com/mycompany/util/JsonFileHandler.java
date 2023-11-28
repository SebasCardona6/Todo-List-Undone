/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.util;

import com.mycompany.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class JsonFileHandler {

    private static final String FILE_PATH = "tasks.json";

    public static List<Task> readTasksFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = JsonFileHandler.class.getClassLoader().getResourceAsStream(FILE_PATH)) {
            if (inputStream != null) {
                return mapper.readValue(inputStream, new TypeReference<List<Task>>() {});
            } else {
                throw new IOException("No se encontró el archivo JSON");
            }
        }
    }

    public static void writeTasksToFile(List<Task> tasks) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            mapper.writeValue(outputStream, tasks);
        }
    }
}