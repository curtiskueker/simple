package org.curtis.database;

import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBSessionFactory {
    private static DBSessionFactory sessionFactory;

    // The actual database name
    private String databaseName;

    private String persistenceUnitName;

    private EntityManagerFactory emf;

    private static final ThreadLocal<EntityManager> entityManager = new ThreadLocal<>();

    // Holds the JpaTransaction for the thread
    private static final ThreadLocal<DBTransaction> transaction = new ThreadLocal<>();

    private DBSessionFactory() throws DBException {
        databaseName = "curtis";
        persistenceUnitName = "curtis";

        instantiateSessionFactory();
    }

    private void instantiateSessionFactory() throws DBException {
        try {
            String name = "mysql";
            String password = "mysql";
            String dbServer = "localhost";

            String url = "jdbc:mysql://" + dbServer + "/" + databaseName;

            Properties jpaProperties = new Properties();
            jpaProperties.put("hibernate.connection.url", url);
            jpaProperties.put("hibernate.connection.username", name);
            jpaProperties.put("hibernate.connection.password", password);

            jpaProperties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            jpaProperties.put("hibernate.show_sql", true);
            jpaProperties.put("hibernate.format_sql", true);

            emf = Persistence.createEntityManagerFactory(persistenceUnitName, jpaProperties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public static synchronized DBSessionFactory getInstance() throws DBException {
        if (sessionFactory == null) {
            setupSessionFactory();
        }

        return sessionFactory;
    }

    private static void setupSessionFactory() throws DBException {
        try {
            sessionFactory = new DBSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public synchronized DBTransaction getTransaction() throws DBException {
        DBTransaction dbTransaction = transaction.get();

        if (dbTransaction == null) {
            dbTransaction = createTransaction();
            transaction.set(dbTransaction);
        }
        return dbTransaction;
    }

    public synchronized void closeTransaction() throws DBException {
        EntityManager em = entityManager.get();
        if(em != null) {
            em.close();
            entityManager.set(null);
        }

        DBTransaction dbTransaction = transaction.get();
        if(dbTransaction != null) {
            transaction.set(null);
        }
    }

    /**
     * Commits the current transaction.
     *
     * @throws DBException when an exception is encountered.
     */
    public void commitTransaction() throws DBException {
        DBTransaction dbTransaction = getTransaction();

        if (dbTransaction != null && dbTransaction.isActive()) {
            dbTransaction.commit();
        }
    }

    private DBTransaction createTransaction() throws DBException {
        DBTransaction dbTransaction = new DBTransaction(getEntityManager());
        dbTransaction.begin();
        return dbTransaction;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public EntityManager getEntityManager() throws DBException {
        EntityManager em = entityManager.get();
        if (em == null) {
            em = createEntityManager();
            entityManager.set(em);
        }
        return em;
    }

    private EntityManager createEntityManager() throws DBException {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
}
