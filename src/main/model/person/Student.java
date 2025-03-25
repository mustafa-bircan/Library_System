package main.model.person;

import main.model.person.enums.ReaderLimit;

public class Student extends Reader {
    private String studentId;
    private String department;
    private int year;

    public Student(String name) {
        super(name, ReaderLimit.STANDART);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci ID boş veya null olamaz");
        }
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş veya null olamaz");
        }
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1 || year > 4) {
            throw new IllegalArgumentException("Sınıf 1 ile 4 arasında olmalıdır");
        }
        this.year = year;
    }

    @Override
    public String whoyouare() {
        return String.format("Ben %s bölümünden %d. sınıf öğrencisi %s, öğrenci numaram: %s",
                getDepartment(), getYear(), getName(), getStudentId());
    }

    @Override
    public String toString() {
        return String.format("Öğrenci{id='%s', ad='%s', bölüm='%s', sınıf=%d}",
                getReaderId(), getName(), department, year);
    }
}