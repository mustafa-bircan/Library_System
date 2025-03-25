package main.model.person.builder;

import main.model.person.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBuilder {
    private String name;
    private String studentId;
    private String department;
    private int year;
/*
    private ReaderLimit readerLimit;
*/

    public StudentBuilder setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci adı boş veya null olamaz");
        }
        this.name = name;
        return this;
    }

    public StudentBuilder setStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci ID boş veya null olamaz");
        }
        this.studentId = studentId;
        return this;
    }

    public StudentBuilder setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş veya null olamaz");
        }
        this.department = department;
        return this;
    }

    public StudentBuilder setYear(int year) {
        if (year < 1 || year > 4) {
            throw new IllegalArgumentException("Sınıf 1 ile 4 arasında olmalıdır");
        }
        this.year = year;
        return this;
    }

    public Student build() {
        validateStudentFields();
        Student student = new Student(name);
        student.setStudentId(studentId);
        student.setDepartment(department);
        student.setYear(year);
        return student;
    }

    private void validateStudentFields() {
        List<String> missingFields = new ArrayList<>();

        if (name == null) missingFields.add("isim");
        if (studentId == null) missingFields.add("öğrenci numarası");
        if (department == null) missingFields.add("bölüm");
        if (year < 1 || year > 4) missingFields.add("sınıf");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Öğrenci oluşturulamıyor. Gerekli alanlar eksik: " +
                            String.join(", ", missingFields)
            );
        }
    }
}