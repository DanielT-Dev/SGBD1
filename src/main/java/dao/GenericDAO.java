package dao;

import config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GenericDAO<T> {

    private final Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    // CREATE
    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
    }

    // UPDATE (FIX YOU WERE MISSING)
    public void update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.merge(entity);

        tx.commit();
        session.close();
    }

    // DELETE
    public void delete(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(session.contains(entity) ? entity : session.merge(entity));

        tx.commit();
        session.close();
    }

    // READ by ID
    public T findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    // READ all
    public List<T> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<T> list = session
                .createQuery("from " + type.getSimpleName(), type)
                .getResultList();

        session.close();
        return list;
    }
}