package com.craft;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class App {

    // 1. Single shared factory for the application
    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("studentPU");

    // 2. Helper to get an EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // --- CRUD METHODS ---

    // CREATE: Insert a new student
    public static void createStudent(Student student) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            System.out.println("Created: " + student);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // READ: Find by ID (Default JPA find)
    public static Student getStudentById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    // UPDATE: Name only
    public static void updateStudentName(String newName, int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                student.setName(newName);
                em.getTransaction().commit();
                System.out.println("Updated name for ID " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // UPDATE: Name and Tech
    public static void updateStudentNameAndTech(String newName, String newTech, int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                student.setName(newName);
                student.setTech(newTech);
                em.getTransaction().commit();
                System.out.println("Updated Name and Tech for ID " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // DELETE: Remove by ID
    public static void deleteStudentById(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
                em.getTransaction().commit();
                System.out.println("Deleted student with ID " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // --- JPA LIFECYCLE (CONTAINS METHOD) ---

    public static void testContainsMethod() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Student student = new Student();
            student.setName("Ned Stark");
            student.setTech("SQL");
            System.out.println("1. Before persist: " + em.contains(student)); // false

            em.persist(student);
            System.out.println("2. After persist: " + em.contains(student));  // true
            em.getTransaction().commit();

            em.detach(student);
            System.out.println("3. After detach: " + em.contains(student));   // false

            Student managedStudent = em.find(Student.class, student.getId());
            System.out.println("4. After find: " + em.contains(managedStudent)); // true

            em.getTransaction().begin();
            em.remove(managedStudent);
            System.out.println("5. After remove: " + em.contains(managedStudent)); // false
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    // --- JPQL QUERIES ---

    // JPQL: Select single student by ID
    public static Student getStudentByIdJPQL(int id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.id = :id",
                    Student.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // JPQL: Select all students
    public static List<Student> getAllStudents() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // --- EXECUTION RUNNER ---

    public static void main(String[] args) {
        try {
            System.out.println("=== 1. TESTING JPQL: SELECT ALL ===");
            List<Student> students = getAllStudents();
            for (Student s : students) {
                System.out.println(s);
            }

            System.out.println("\n=== 2. TESTING JPQL: SELECT BY ID ===");
            Student student = getStudentByIdJPQL(1);
            System.out.println("Fetched with JPQL: " + student);

            System.out.println("\n=== 3. TESTING CONTAINS() METHOD ===");
            testContainsMethod();

        } finally {
            // Closes database factory pool when application ends
            emf.close();
        }
    }
}