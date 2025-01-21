package org.example.DAO;

import org.example.Util.HibernateUtil;
import org.example.entities.Familia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class FamiliaImpl implements FamiliaDAO {

    private final SessionFactory sessionFactory;

    public FamiliaImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Crea una nueva familia en la base de datos.
     *
     * @param familia Objeto Familia a registrar.
     * @return La Familia creada.
     */
    @Override
    public Familia create(Familia familia) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(familia);
            transaction.commit();
            return familia;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al crear la familia.");
        }
    }

    /**
     * Elimina una familia de la base de datos.
     *
     * @param familia Objeto Familia a eliminar.
     * @return La Familia eliminada.
     */
    @Override
    public Familia delete(Familia familia) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Familia familiaToDelete = session.get(Familia.class, familia.getId());
            if (familiaToDelete != null) {
                session.delete(familiaToDelete);
                transaction.commit();
            } else {
                throw new RuntimeException("La familia con ID " + familia.getId() + " no existe.");
            }
            return familia;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la familia.");
        }
    }

    /**
     *
     * @return
     */
    //Mostrar las familias
    @Override
    public List<Familia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Familia";
            return session.createQuery(hql, Familia.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las familias.");
        }
    }
}
