package org.example.entities;

import org.example.DAO.AnimalImpl;
import org.example.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void getId() {
        Animal animal = new Animal();
        animal.setId(1);
        assertEquals(1, animal.getId());
    }

    @Test
    void getNombre() {
        Animal animal = new Animal();
        animal.setNombre("Rex");
        assertEquals("Rex", animal.getNombre());
    }

    @Test
    void getEspecie() {
        Animal animal = new Animal();
        animal.setEspecie(Animales.Perro);
        assertEquals(Animales.Perro, animal.getEspecie());
    }

    @Test
    void getEdad() {
        Animal animal = new Animal();
        animal.setEdad(5);
        assertEquals(5, animal.getEdad());
    }

    @Test
    void getDescripcionPerdida() {
        Animal animal = new Animal();
        animal.setDescripcionPerdida("Se perdió en el parque");
        assertEquals("Se perdió en el parque", animal.getDescripcionPerdida());
    }

    @Test
    void getEstado() {
        Animal animal = new Animal();
        animal.setEstado(Estado.RecienAbandonado);
        assertEquals(Estado.RecienAbandonado, animal.getEstado());
    }

    @Test
    void getFamilia() {
        Animal animal = new Animal();
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNombre("Familia Pérez");
        animal.setFamilia(familia);
        assertEquals(familia, animal.getFamilia());
    }

    @Test
    void setId() {
        Animal animal = new Animal();
        animal.setId(10);
        assertEquals(10, animal.getId());
    }

    @Test
    void setNombre() {
        Animal animal = new Animal();
        animal.setNombre("Toby");
        assertEquals("Toby", animal.getNombre());
    }

    @Test
    void setEdad() {
        Animal animal = new Animal();
        animal.setEdad(3);
        assertEquals(3, animal.getEdad());
    }

    @Test
    void setEspecie() {
        Animal animal = new Animal();
        animal.setEspecie(Animales.Gato);
        assertEquals(Animales.Gato, animal.getEspecie());
    }

    @Test
    void setDescripcionPerdida() {
        Animal animal = new Animal();
        animal.setDescripcionPerdida("Desapareció en el bosque");
        assertEquals("Desapareció en el bosque", animal.getDescripcionPerdida());
    }

    @Test
    void setEstado() {
        Animal animal = new Animal();
        animal.setEstado(Estado.ProximamenteAcogida);
        assertEquals(Estado.ProximamenteAcogida, animal.getEstado());
    }

    @Test
    void setFamilia() {
        Animal animal = new Animal();
        Familia familia = new Familia();
        familia.setId(2);
        familia.setNombre("Familia Gómez");
        animal.setFamilia(familia);
        assertEquals(familia, animal.getFamilia());
    }

    @Test
    void createAnimalTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear un nuevo animal
        Animal animal = new Animal();
        animal.setNombre("Max");
        animal.setEspecie(Animales.Perro);
        animal.setEdad(3);
        animal.setEstado(Estado.RecienAbandonado);

        // Iniciar la transacción
        session.beginTransaction();

        // Guardar el animal en la base de datos
        session.persist(animal);

        // Confirmar la transacción
        session.getTransaction().commit();

        // Verificar que el animal tiene un ID asignado
        assertNotNull(animal.getId());
        assertEquals("Max", animal.getNombre());

        // Cerrar sesión y fábrica de sesiones
        session.close();
        sessionFactory.close();
    }


}
