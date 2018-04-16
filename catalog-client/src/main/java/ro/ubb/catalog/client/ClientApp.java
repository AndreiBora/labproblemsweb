package ro.ubb.catalog.client;

import javafx.util.Pair;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.web.dto.StudentDto;
import ro.ubb.catalog.web.dto.StudentsDto;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.catalog.client.config");
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        Console console = context.getBean(Console.class);
        console.run();
    }

}
