package com.university.student_management.service;

import com.university.student_management.model.Student;
import com.university.student_management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 1. Create a new student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // 2. Retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 3. Retrieve a single student by ID (returns null if not found)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // 4. Update an existing student by ID (returns null if not found)
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());

        return studentRepository.save(existingStudent);
    }

    // 5. Delete a student by ID
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
    }
}