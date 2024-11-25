import com.grades.model.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Course course = new Course("Programming II"); // Crea un nuevo curso

        while (true) {
            System.out.println("\n=== Sistema de Calificación ===");
            System.out.println("1. Añadir una lista de estudiantes");
            System.out.println("2. Añadir notas y sus ponderados");
            System.out.println("3. Calificar estudiantes");
            System.out.println("4. Ver lista de estudiantes y sus calificaciones");
            System.out.println("5. Ver el promedio del curso");
            System.out.println("6. Salir");
            System.out.print("Por favor escoge una opción: ");

            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opción invalida. Ingrese una opción del menú.");
                scanner.nextLine(); // Limpiar el buffer
                continue; // Volver al principio del bucle
            }

            try {
                switch (option) {
                    case 1:
                        addStudentsFromFile(course, scanner);
                        break;
                    case 2:
                        addGrades(course, scanner);
                        break;
                    case 3:
                        gradeStudents(course, scanner);
                        break;
                    case 4:
                        viewStudentList(course);
                        break;
                    case 5:
                        viewCourseAverage(course);
                        break;
                    case 6:
                        System.out.println("¡Gracias por usar el sistema!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción Invalida.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudentsFromFile(Course course, Scanner scanner) {
        System.out.print("Entre el path donde esta ubicado el archivo: ");
        String fileName = scanner.nextLine();
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    course.addStudent(new Student(parts[0].trim(), parts[1].trim()));
                }
            }
            fileScanner.close();
            System.out.println("Estudiantes añadidos correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Un error a ocurrido mientras se leia el archivo, intentelo de nuevo con un archivo valido " + e.getMessage());
        }
    }

    private static void addGrades(Course course, Scanner scanner) {
        List<Subject> subjects = new ArrayList<>();
        double totalPercentage = 0;

        while (true) {
            System.out.print("Ingrese el número de notas: ");
            int numGrades;
            try {
                numGrades = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (java.util.InputMismatchException e) {
                System.out.println("Debe ser un número.");
                scanner.nextLine(); // Limpiar el buffer
                continue;
            }

            for (int i = 0; i < numGrades; i++) {
                System.out.print("Ingrese el nombre de la nota: ");
                String name = scanner.nextLine();
                System.out.print("Ingrese el porcentaje de la nota: ");
                double percentage;
                try {
                    percentage = scanner.nextDouble();
                    scanner.nextLine(); // Consumir el salto de línea
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Error, ingrese un numero de 0 a 100.");
                    scanner.nextLine(); // Limpiar el buffer
                    i--; //Decrementar i para repetir la iteración
                    continue;
                }
                totalPercentage += percentage;
                subjects.add(new Subject(name, percentage));
            }

            if (totalPercentage != 100) {
                throw new IllegalArgumentException("El porcentaje total debe ser 100.");
            }
            break;
        }

        course.setSubjects(subjects);
        System.out.println("Notas añadidas correctamente.");
    }

    private static void gradeStudents(Course course, Scanner scanner) {
        if (course.getSubjects().isEmpty()) {
            throw new IllegalStateException("Debes añadir las notas antes de poder calificarlas.");
        }
        System.out.print("Ingresa el ID del estudiante: ");
        String id = scanner.nextLine();
        Student student = course.getStudentById(id);

        if (student == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }
        System.out.println("Nombre del estudiante: "+ student.getName());
        for (Subject subject : course.getSubjects()) {
            System.out.print("Ingrese nota para " + subject.getName() + " (0-5) y porcentaje "+ subject.getPercentage()+ "%: ");
            double score;
            try {
                score = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (java.util.InputMismatchException e) {
                System.out.println("Valor incorrecto, ingresa un número de 0 a 5.");
                scanner.nextLine(); // Limpiar el buffer
                return;
            }
            if (score < 0 || score > 5) {
                System.out.println("Valor incorrecto, ingresa un número de 0 a 5.");
                return;
            }
            student.addGrade(new Grade(subject, score));
        }

        System.out.println("Notas añadidas para el estudiante " + student.getName());
    }


    private static void viewStudentList(Course course) {
        List<Student> students = course.getStudents();
        if (students.isEmpty()) {
            System.out.println("No hay estudiantes registrados todavía.");
            return;
        }

        for (Student student : students) {
            System.out.println("\nEstudiante: " + student.getName() + " (ID: " + student.getId() + ")");
            List<Grade> grades = student.getGrades();
            if (grades.isEmpty()) {
                System.out.println("  Sin notas registradas.");
            } else {
                for (Grade grade : grades) {
                    System.out.printf("  %s: %.2f\n", grade.getSubject().getName(), grade.getScore());
                }
                System.out.printf("  Nota definitiva: %.2f\n", student.calculateAverage());
            }
        }
    }

    private static void viewCourseAverage(Course course) {
        double average = course.calculateCourseAverage();
        System.out.printf("Promedio del curso: %.2f\n", average);
    }
}
