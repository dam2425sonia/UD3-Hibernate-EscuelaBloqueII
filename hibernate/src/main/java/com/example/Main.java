package com.example;
import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Crear una configuración de Hibernate
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Alumno.class);
        configuration.addAnnotatedClass(Curso.class);
        // Crear una fábrica de sesiones
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // Obtener una sesión
        Session session = sessionFactory.openSession();
        // Iniciar una transacción
        Transaction transaction = session.beginTransaction();
        try {
            // Crear un nuevo curso
            Curso curso = new Curso("Matemáticas");
            // Crear nuevos alumnos
            Alumno alumno1 = new Alumno("Juan", "Pérez", 20, curso);
            Alumno alumno2 = new Alumno("María", "García", 22, curso);
            
            // Lista de nombres de ejemplo
            curso.setAlumnos(Arrays.asList(alumno1, alumno2));

            // Guardar el curso y los alumnos en la base de datos
            session.save(curso);
            //session.save(alumno1);
            //session.save(alumno2);
            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // Si hay un error, revertir la transacción
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar la sesión
            session.close();
            sessionFactory.close();
        }
    }
}