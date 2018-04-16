package ro.ubb.catalog.client;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.client.validator.LabProblemException;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.validators.LabProblemsException;
import ro.ubb.catalog.web.dto.*;

import java.util.*;

@Component
public class Console {

    @Autowired
    RestTemplate restTemplate;
    /*
     * Display Menu
     */
    private static void showMenu() {
        System.out.println("1. Add a student");
        System.out.println("2. Display all students");
        System.out.println("3. Delete a student");
        System.out.println("4. Update a student");
        System.out.println("5. Add a problem");
        System.out.println("6. Display all the lab problems");
        System.out.println("7. Delete a problem");
        System.out.println("8. Update a problem");
        System.out.println("9. Assign problem to student");
        System.out.println("10. Display all the grades/problem student assignments");
        System.out.println("11. Disassociate student and problem");
        System.out.println("0. Exit");
        System.out.println();
    }

    /*
     * Read input from the keyboard until an long is entered
     */
    private static Long readLong(Scanner scanner) {
        Long id;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            throw new InputException("You must enter only digits");
        }
        //Read the remaining \n symbol
        scanner.nextLine();
        return id;
    }

    /**
     * Read input from the keyboard until an integer is entered
     */
    private static int readInt(Scanner scanner) {
        Integer id;
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new InputException("You must enter only digits");
        }
        //Read the remaining \n symbol
        scanner.nextLine();
        return id;
    }

    /*
     *Read student's information
     */
    private Optional<Student> readStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id (only digits):");
        Long id = readLong(scanner);
        System.out.println("Enter the serial number: ");
        String serialNumber = scanner.nextLine();
        System.out.println("Enter name of the student");
        String name = scanner.nextLine();
        System.out.println("Enter group nr (only digits)");
        int group = readInt(scanner);


        Student student = new Student(serialNumber, name, group);
        student.setId(id);

        Optional<Student> s = Optional.of(student);
        return s;
    }

    private Optional<Problem> readProblem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id (only digits):");
        Long id = readLong(scanner);
        System.out.println("Type the statement of the problem");
        String statement = scanner.nextLine();
        System.out.println("Enter the level of dificulty (High |Medium |Low)");
        String difficulty = scanner.nextLine();

        Problem problem = new Problem(statement, difficulty);
        problem.setId(id);

        Optional<Problem> p = Optional.of(problem);
        return p;
    }

    /*
     *Read student's grade information
     */
    private Optional<Grade> readGrade() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the student");
        Long studentId = readLong(scanner);
        System.out.println("Enter the id of the lab problem");
        Long problemId = readLong(scanner);
        System.out.println("Enter the value of the grade");
        int value = readInt(scanner);

        Grade grade = new Grade(studentId, problemId, null);
        grade.setId(new Pair<>(studentId, problemId));
        Optional<Grade> g = Optional.of(grade);
        return g;
    }

    private Optional<Grade> readGradeWithoutValue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the student");
        Long studentId = readLong(scanner);
        System.out.println("Enter the id of the lab problem");
        Long problemId = readLong(scanner);

        Grade grade = new Grade(studentId, problemId, null);
        grade.setId(new Pair<>(studentId, problemId));
        Optional<Grade> g = Optional.of(grade);
        return g;
    }


    /*
     * Add a student into the application
     */
    private void addStudent() {
        try {
            Student s = readStudent().get();
            StudentDto sDto = new StudentDto(s.getSerialNumber(),s.getName(),s.getGroup());
            sDto.setId(s.getId());

            StudentDto response = restTemplate
            .postForObject("http://localhost:8080/api/students",
                    sDto,
                    StudentDto.class);

        } catch (InputException |HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Add a problem
     */
    private void addProblem() {
        try {
            Problem problem = readProblem().get();
            ProblemDto problemDto = new ProblemDto(problem.getStatement(),problem.getDifficulty());
            problemDto.setId(problem.getId());

            ProblemDto response = this.restTemplate
                    .postForObject("http://localhost:8080/api/problems",problemDto,ProblemDto.class);

        } catch (InputException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Remove a student from the app
     */
    private void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id of the student that you want to delete");

        try {
            long id = readLong(sc);
            this.restTemplate.delete("http://localhost:8080/api/students/{studentId}",id);
        } catch (InputException| HttpClientErrorException  e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Remove a problem from the app
     */
    private void deleteProblem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id of the problem you want to delete");
        try {
            long id = readLong(sc);
            this.restTemplate
                    .delete("http://localhost:8080/api/problems/{problemId}",id);
        } catch (IllegalArgumentException | InputException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Display all the students
     */
    private void printAllStudents() {
        StudentsDto students = this.restTemplate
                .getForObject("http://localhost:8080/api/students", StudentsDto.class);
        try {
            students.getStudents().stream().findAny().orElseThrow(() -> new LabProblemException("There are no students in the application"));
        } catch (LabProblemException e) {
            System.out.println(e.getMessage());
        }
        students.getStudents().forEach(System.out::println);
    }

    /*
     *Display all the problems from the app
     */
    private void printAllProblems() {
        ProblemsDto problems = this.restTemplate.getForObject("http://localhost:8080/api/problems",ProblemsDto.class);

        try {
            problems.getProblems().stream().findAny().orElseThrow(() -> new LabProblemsException("There are no problems in the application"));
        } catch (LabProblemsException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
        problems.getProblems().forEach(System.out::println);
    }


    /*
     * Update info of a student
     */
    private void updateStudent() {
        try {
            Student s = readStudent().get();
            StudentDto sDto = new StudentDto(s.getSerialNumber(),s.getName(),s.getGroup());
            sDto.setId(s.getId());

            this.restTemplate.put("http://localhost:8080/api/students/{studentId}",sDto,sDto.getId());
        } catch (InputException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Update info of a problem
     */
    private void updateProblem() {
        try {
            Problem problem = readProblem().get();
            ProblemDto problemDto = new ProblemDto(problem.getStatement(),problem.getDifficulty());
            problemDto.setId(problem.getId());
            this.restTemplate
                    .put("http://localhost:8080/api/problems/{problemId}",problemDto,problemDto.getId());
        } catch (InputException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }


//    /*
//     * Remove a grade from catalog
//     */
//    private void deleteGrade() {
//        try {
//            readGradeWithoutValue().ifPresent((grade) -> {
//                this.gradeService.deleteGrade(grade);
//            });
//        } catch (InputException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    /*
     * Display all the grades from the app
     */
    private void displayAllGrades() {
        GradesDto grades = this.restTemplate.getForObject("http://localhost:8080/api/grades", GradesDto.class);

        try {
            grades.getGrades().stream().findAny().orElseThrow(() -> new LabProblemsException("There are no grades in the application"));
        } catch (LabProblemsException e) {
            System.out.println(e.getMessage());
        }
        grades.getGrades().forEach(System.out::println);
    }

    /*
     * Assign a lab problem to a student
     */
    private void assignProblem() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter student id");
            Long studentId = readLong(scanner);
            System.out.println("Enter problem id");
            Long problemId = readLong(scanner);
            //TODO
//            this.gradeService.assignProblem(studentId,problemId);

        } catch (InputException |IllegalArgumentException  e) {
            System.out.println(e.getMessage());
        }

    }
//
//    /*
//     * Remove a problem that was assign to a student
//     */
//    private void dissociateProblem() {
//        Scanner scanner = new Scanner(System.in);
//        try {
//            System.out.println("Enter student id");
//            Long studentId = readLong(scanner);
//            System.out.println("Enter problem id");
//            Long problemId = readLong(scanner);
//            this.gradeService.dissociateProblem(studentId,problemId);
//
//        } catch (InputException |IllegalArgumentException  e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void gradeStudent() {
//        Scanner scanner = new Scanner(System.in);
//        try {
//            System.out.println("Enter student id");
//            Long studentId = readLong(scanner);
//            System.out.println("Enter problem id");
//            Long problemId = readLong(scanner);
//            System.out.println("Enter the grade");
//            Integer value = readInt(scanner);
//            this.gradeService.assignGrade(studentId,problemId,value);
//
//        } catch (InputException |IllegalArgumentException  e) {
//            System.out.println(e.getMessage());
//        }
//    }

    /*
     *  Entry point of the application
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        exit:
        while (true) {
            showMenu();
            System.out.println("Input: ");
            int x = 0;
            try {
                x = readInt(scanner);
            }catch (InputException e){
                e.getMessage();
            }
            switch (x) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    printAllStudents();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    addProblem();
                    break;
                case 6:
                    printAllProblems();
                    break;
                case 7:
                    deleteProblem();
                    break;
                case 8:
                    updateProblem();
                    break;
                case 9:
                    assignProblem();
                    break;
                case 10:
                    displayAllGrades();
                    break;
//                case 11:
//                    dissociateProblem();
//                    break;
//                case 12:
//                    gradeStudent();
//                    break;
                case 0:
                    break exit;
                default:
                    System.out.println("Invalid");
            }
        }
    }
}

