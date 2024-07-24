package com.example.testservice1.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<String> indo() throws IOException, InterruptedException {

        // Параметры запроса
        Map<String, String> doctorId = new HashMap<>();
        doctorId.put("doctorId", "22");

        // Создание HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Создание запроса с заголовками
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8011/info"));

        // Добавление заголовков
        for (Map.Entry<String, String> entry : doctorId.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        HttpRequest request = requestBuilder.build();

        // Выполнение запроса и получение ответа
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Возвращение ответа
        return new ResponseEntity<>("My success response: " + response.body(), HttpStatus.OK);
    }

    @PostMapping("/info/add")
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
        try {
            // Создание HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Создание запроса с телом запроса (body)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8011/info/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(doctor.toJson())) // Метод toJson() должен преобразовать объект Doctor в JSON
                    .build();

            // Выполнение запроса и получение ответа
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Возвращение ответа
            return new ResponseEntity<>("Doctor added successfully: " + response.body(), HttpStatus.CREATED);
        } catch (IOException | InterruptedException e) {
            // Обработка ошибок
            return new ResponseEntity<>("Request failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/info/{id}")
    public ResponseEntity<String> getDoctorById(@PathVariable Long id) {
        try {
            // Создание HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Создание запроса с параметром id в строке запроса
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8011/info/" + id))
                    .build();

            // Выполнение запроса и получение ответа
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Возвращение ответа
            return new ResponseEntity<>("Doctor details: " + response.body(), HttpStatus.OK);
        } catch (IOException | InterruptedException e) {
            // Обработка ошибок
            return new ResponseEntity<>("Request failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }
