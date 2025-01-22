package org.example.entities;

import org.example.DAO.FamiliaImpl;
import org.example.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamiliaTest {

    @Test
    void getId() {
        Familia familia = new Familia();
        familia.setId(1);
        assertEquals(1, familia.getId());
    }

    @Test
    void setId() {
        Familia familia = new Familia();
        familia.setId(10);
        assertEquals(10, familia.getId());
    }

    @Test
    void getNombre() {
        Familia familia = new Familia();
        familia.setNombre("Familia Pérez");
        assertEquals("Familia Pérez", familia.getNombre());
    }

    @Test
    void setNombre() {
        Familia familia = new Familia();
        familia.setNombre("Familia Gómez");
        assertEquals("Familia Gómez", familia.getNombre());
    }

    @Test
    void getEdad() {
        Familia familia = new Familia();
        familia.setEdad(30);
        assertEquals(30, familia.getEdad());
    }

    @Test
    void setEdad() {
        Familia familia = new Familia();
        familia.setEdad(40);
        assertEquals(40, familia.getEdad());
    }

    @Test
    void getCiudad() {
        Familia familia = new Familia();
        familia.setCiudad("Sevilla");
        assertEquals("Sevilla", familia.getCiudad());
    }

    @Test
    void setCiudad() {
        Familia familia = new Familia();
        familia.setCiudad("Madrid");
        assertEquals("Madrid", familia.getCiudad());
    }


    @Test
    void createFamiliaTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear una nueva familia
        Familia familia = new Familia();
        familia.setNombre("Familia López");
        familia.setEdad(35);
        familia.setCiudad("Valencia");

        // Iniciar la transacción
        session.beginTransaction();

        // Guardar la familia en la base de datos
        session.persist(familia);

        // Confirmar la transacción
        session.getTransaction().commit();

        // Verificar que la familia tiene un ID asignado
        assertNotNull(familia.getId());
        assertEquals("Familia López", familia.getNombre());

        // Cerrar sesión y fábrica de sesiones
        session.close();
        sessionFactory.close();
    }

    @Test
    void findAllFamiliasTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear y guardar algunas familias
        Familia familia1 = new Familia();
        familia1.setNombre("Familia Pérez");
        familia1.setEdad(30);
        familia1.setCiudad("Sevilla");

        Familia familia2 = new Familia();
        familia2.setNombre("Familia Gómez");
        familia2.setEdad(40);
        familia2.setCiudad("Madrid");

        session.beginTransaction();
        session.persist(familia1);
        session.persist(familia2);
        session.getTransaction().commit();

        // Llamar al método findAll para obtener todas las familias
        FamiliaImpl familiaDAO = new FamiliaImpl();
        List<Familia> familias = familiaDAO.findAll();

        // Verificar que las familias han sido recuperadas
        assertNotNull(familias);
        assertFalse(familias.isEmpty());
        assertTrue(familias.size() >= 2);

        // Verificar que las familias contienen los datos esperados
        assertEquals("Familia Pérez", familias.get(20).getNombre());
        assertEquals("Familia Gómez", familias.get(21).getNombre());

        // Cerrar sesión y fábrica de sesiones
        session.close();
        sessionFactory.close();
    }

    @Test
    void deleteFamiliaTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear una familia
        Familia familia = new Familia();
        familia.setNombre("Gomez");
        familia.setEdad(40);
        familia.setCiudad("Madrid");

        // Iniciar la transacción
        session.beginTransaction();

        // Guardar la familia en la base de datos
        session.persist(familia);

        // Confirmar la transacción
        session.getTransaction().commit();

        // Paso 2: Asegurarse de que la entidad ha sido guardada correctamente.
        Integer familiaId = familia.getId();
        Familia familiaFromDb = session.get(Familia.class, familiaId);
        assertNotNull(familiaFromDb, "La familia debería estar guardada en la base de datos");

        // Paso 3: Eliminar la entidad de la base de datos.
        session.beginTransaction();
        session.delete(familiaFromDb);
        session.getTransaction().commit();

        // Paso 4: Verificar que la entidad ha sido eliminada correctamente.
        Familia deletedFamilia = session.get(Familia.class, familiaId);
        assertNull(deletedFamilia, "La familia debería haber sido eliminada de la base de datos");
    }
}
