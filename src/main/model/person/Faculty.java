package main.model.person;

import main.model.person.enums.ReaderLimit;

public class Faculty extends Reader {
    private String facultyId;
    private String department;
    private String title;

    public Faculty(String name) {
        super(name, ReaderLimit.VIP);
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen ID boş veya null olamaz");
        }
        this.facultyId = facultyId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Unvan boş veya null olamaz");
        }
        this.title = title;
    }

    @Override
    public String whoyouare() {
        return String.format("Ben %s bölümünden %s %s, akademisyen ID: %s",
                getDepartment(), getTitle(), getName(), getFacultyId());
    }

    @Override
    public String toString() {
        return String.format("Akademisyen{id='%s', unvan='%s', ad='%s', bölüm='%s'}",
                getReaderId(), title, getName(), department);
    }
}