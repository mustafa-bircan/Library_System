package main.model.person.builder;

import main.model.person.Faculty;

import java.util.ArrayList;
import java.util.List;

public class FacultyBuilder {
    private String name;
    private String facultyId;
    private String department;
    private String title;

    public FacultyBuilder setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen adı boş veya null olamaz");
        }
        this.name = name;
        return this;
    }

    public FacultyBuilder setFacultyId(String facultyId) {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen ID boş veya null olamaz");
        }
       this.facultyId = facultyId;
       return this;
   }

    public FacultyBuilder setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş veya null olamaz");
        }
        this.department = department;
        return this;
    }

    public FacultyBuilder setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Unvan boş veya null olamaz");
        }
        this.title = title;
        return this;
    }

    public Faculty build() {
        validateFacultyFields();
        Faculty faculty = new Faculty(name);
        faculty.setFacultyId(facultyId);
        faculty.setDepartment(department);
        faculty.setTitle(title);
        return faculty;
    }

    private void validateFacultyFields() {
        List<String> missingFields = new ArrayList<>();

        if (name == null) missingFields.add("isim");
        if (facultyId == null) missingFields.add("akademisyen ID");
        if (department == null) missingFields.add("bölüm");
        if (title == null) missingFields.add("unvan");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Akademisyen oluşturulamıyor. Gerekli alanlar eksik: " +
                            String.join(", ", missingFields)
            );
        }
    }
}
