package config;

import config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Hibernate session opened successfully!");

            Transaction tx = session.beginTransaction();

            System.out.println("Transaction started successfully!");

            tx.commit();

            System.out.println("Transaction committed successfully!");

        } catch (Exception e) {
            System.out.println("ERROR: Hibernate failed to start");
            e.printStackTrace();
        }

        HibernateUtil.getSessionFactory().close();
    }
}