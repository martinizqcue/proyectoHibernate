package org.example.DAO;

import org.example.Util.HibernateUtil;
import org.example.entities.Animal;
import org.example.entities.Animales;
import org.example.entities.Estado;
import org.example.entities.Familia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AnimalImpl implements AnimalDAO {

    private final SessionFactory sessionFactory;

    public AnimalImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Crea un nuevo animal en la base de datos.
     *
     * @param animal Objeto Animal a registrar.
     * @return El Animal creado.
     */
    @Override
    public Animal create(Animal animal) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(animal);
            transaction.commit();
            return animal;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al crear el animal.");
        }
    }

    /**
     * Busca animales por su especie.
     *
     * @param especie Especie del animal a buscar.
     * @return Lista de animales de la especie proporcionada.
     */
    @Override
    public List<Animal> findByEspecie(Animales especie) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Animal WHERE especie = :especie";
            return session.createQuery(hql, Animal.class)
                    .setParameter("especie", especie)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar animales por especie.");
        }
    }

    /**
     * Actualiza el estado de un animal.
     *
     * @param estado Nuevo estado del animal.
     * @param id     ID del animal a actualizar.
     * @return El Animal actualizado.
     */
    @Override
    public Animal update(Estado estado, Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            if (animal != null) {
                animal.setEstado(estado);
                session.update(animal);
                transaction.commit();
            }
            return animal;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el estado del animal.");
        }
    }

    /**
     * Busca animales por el ID de la familia que los acoge.
     *
     * @param id ID de la familia.
     * @return Lista de animales acogidos por la familia.
     */
    @Override
    public List<Animal> findByFamilia(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Animal WHERE familia.id = :familiaId";
            return session.createQuery(hql, Animal.class)
                    .setParameter("familiaId", id)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar animales por familia.");
        }
    }


    /**
     *
     * @param familia
     * @param id
     * @return
     */
    @Override
    public Animal update(Familia familia, Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();


            Animal animal = session.get(Animal.class, id);
            if (animal != null) {

                Familia familiaCompleta = session.get(Familia.class, familia.getId());
                if (familiaCompleta == null) {
                    throw new RuntimeException("La familia con ID " + familia.getId() + " no existe.");
                }


                animal.setFamilia(familiaCompleta);


                session.update(animal);
                session.flush(); //Asegura que los cambios se escriban inmediatamente
                transaction.commit();
            }

            return animal;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al asignar una familia al animal.");
        }
    }

}
