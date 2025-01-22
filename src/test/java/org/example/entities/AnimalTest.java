package org.example.entities;

import org.example.DAO.AnimalImpl;
import org.example.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void findByEspecieTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear y guardar animales de diferentes especies
        Animal animal1 = new Animal();
        animal1.setNombre("Luna");
        animal1.setEspecie(Animales.Gato);
        animal1.setEdad(2);
        animal1.setEstado(Estado.TiempoRefugio);

        Animal animal2 = new Animal();
        animal2.setNombre("Simba");
        animal2.setEspecie(Animales.Gato);
        animal2.setEdad(3);
        animal2.setEstado(Estado.RecienAbandonado);

        Animal animal3 = new Animal();
        animal3.setNombre("Max");
        animal3.setEspecie(Animales.Perro);
        animal3.setEdad(4);
        animal3.setEstado(Estado.ProximamenteAcogida);

        // Iniciar la transacción
        session.beginTransaction();

        // Guardar los animales en la base de datos
        session.persist(animal1);
        session.persist(animal2);
        session.persist(animal3);

        // Confirmar la transacción
        session.getTransaction().commit();

        // Cerrar la sesión
        session.close();

        // Llamar al método findByEspecie para buscar Gatos
        AnimalImpl animalDAO = new AnimalImpl();
        List<Animal> gatos = animalDAO.findByEspecie(Animales.Gato);

        // Verificar los resultados
        assertNotNull(gatos);
        assertFalse(gatos.isEmpty());
        assertTrue(gatos.size() >= 2);
        assertTrue(gatos.stream().allMatch(animal -> animal.getEspecie() == Animales.Gato));

        // Verificar que el contenido coincide con lo esperado
        assertEquals("Luna", gatos.get(4).getNombre());
        assertEquals("Simba", gatos.get(5).getNombre());

        // Cerrar la fábrica de sesiones
        sessionFactory.close();
    }

    @Test
    void updateAnimalEstadoTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear y guardar un animal
        Animal animal = new Animal();
        animal.setNombre("Bella");
        animal.setEspecie(Animales.Perro);
        animal.setEdad(5);
        animal.setEstado(Estado.RecienAbandonado);

        // Iniciar la transacción
        session.beginTransaction();

        // Guardar el animal en la base de datos
        session.persist(animal);

        // Confirmar la transacción
        session.getTransaction().commit();

        // Obtener el ID del animal guardado
        Integer animalId = animal.getId();

        // Cerrar la sesión inicial
        session.close();

        // Llamar al método update para cambiar el estado
        AnimalImpl animalDAO = new AnimalImpl();
        Animal updatedAnimal = animalDAO.update(Estado.TiempoRefugio, animalId);

        // Verificar que el estado se haya actualizado correctamente
        assertNotNull(updatedAnimal);
        assertEquals(animalId, updatedAnimal.getId());
        assertEquals(Estado.TiempoRefugio, updatedAnimal.getEstado());

        // Verificar que otros datos no se hayan modificado
        assertEquals("Bella", updatedAnimal.getNombre());
        assertEquals(Animales.Perro, updatedAnimal.getEspecie());
        assertEquals(5, updatedAnimal.getEdad());

        // Cerrar la fábrica de sesiones
        sessionFactory.close();
    }

    @Test
    void findByFamiliaTest() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        // Crear y guardar una familia
        Familia familia = new Familia();
        familia.setNombre("Familia Pérez");

        session.beginTransaction();
        session.persist(familia);
        session.getTransaction().commit();

        // Crear y asociar animales a la familia
        Animal animal1 = new Animal();
        animal1.setNombre("Toby");
        animal1.setEspecie(Animales.Perro);
        animal1.setEdad(2);
        animal1.setEstado(Estado.TiempoRefugio);
        animal1.setFamilia(familia);

        Animal animal2 = new Animal();
        animal2.setNombre("Luna");
        animal2.setEspecie(Animales.Gato);
        animal2.setEdad(3);
        animal2.setEstado(Estado.RecienAbandonado);
        animal2.setFamilia(familia);

        session.beginTransaction();
        session.persist(animal1);
        session.persist(animal2);
        session.getTransaction().commit();

        // Obtener el ID de la familia creada
        Integer familiaId = familia.getId();

        // Cerrar la sesión inicial
        session.close();

        // Llamar al método findByFamilia
        AnimalImpl animalDAO = new AnimalImpl();
        List<Animal> animales = animalDAO.findByFamilia(familiaId);

        // Verificar resultados
        assertNotNull(animales);
        assertEquals(2, animales.size());

        assertTrue(animales.stream().anyMatch(a -> a.getNombre().equals("Toby")));
        assertTrue(animales.stream().anyMatch(a -> a.getNombre().equals("Luna")));

        // Verificar que los animales están asociados a la familia correcta
        for (Animal animal : animales) {
            assertNotNull(animal.getFamilia());
            assertEquals(familiaId, animal.getFamilia().getId());
        }

        // Cerrar la fábrica de sesiones
        sessionFactory.close();
    }

    @Test
    void updateAnimalFamiliaTest() {
        // Crear una familia para asignar al animal
        Familia familia = new Familia();
        familia.setCiudad("Sevilla");
        familia.setEdad(30);
        familia.setNombre("Familia Pérez");

        // Crear un animal para actualizar
        Animal animal = new Animal();
        animal.setNombre("Bolt");
        animal.setEspecie(Animales.Perro);
        animal.setEdad(3);
        animal.setEstado(Estado.RecienAbandonado);

        // Guardar la familia y el animal en la base de datos
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(familia);
        session.persist(animal);
        session.getTransaction().commit();

        // Asegurarse de que el animal tiene un ID asignado
        assertNotNull(animal.getId());

        // Asignar la familia al animal
        AnimalImpl animalDAO = new AnimalImpl();
        animalDAO.update(familia, animal.getId());

        // Recargar el animal desde la base de datos para verificar la asignación
        session.beginTransaction();
        Animal updatedAnimal = session.get(Animal.class, animal.getId());
        session.getTransaction().commit();

        // Verificar que la familia ha sido asignada correctamente al animal
        assertNotNull(updatedAnimal);
        assertNotNull(updatedAnimal.getFamilia()); // Verificar que la familia no es nula
        assertEquals(familia.getId(), updatedAnimal.getFamilia().getId());

        // Cerrar sesión y fábrica de sesiones
        session.close();
        sessionFactory.close();
    }





}
