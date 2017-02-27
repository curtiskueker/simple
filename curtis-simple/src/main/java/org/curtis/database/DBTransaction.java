package org.curtis.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DBTransaction {
    private EntityManager em;
    private EntityTransaction transaction;

    protected DBTransaction() {
    }

    public DBTransaction(EntityManager em) {
        this.em = em;
        this.transaction = em.getTransaction();
    }

    public void begin() throws DBException {
        checkDatabase();
    }

    public void commit() throws DBException {
        checkDatabase();

        try {
            transaction.commit();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void rollback() throws DBException {
        checkDatabase();

        try {
            transaction.rollback();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public boolean isActive() {
        return (transaction != null && transaction.isActive());
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public boolean isClosed() {
        return (em == null || !em.isOpen());
    }

    public void clear() throws DBException {
        checkDatabase();

        try {
            em.clear();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void create(Object object) throws DBException {
        checkDatabase();

        try {
            em.persist(object);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void update(Object object) throws DBException {
        checkDatabase();

        try {
            if (!em.contains(object)) em.refresh(object);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void delete(Object object) throws DBException {
        checkDatabase();

        try {
            em.remove(object);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public <T> T refresh(T object) throws DBException {
        try {
            em.refresh(object);
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }
        return object;
    }

    public <T> T getObjectById(Class<T> classObject, int id) throws DBException {
        checkDatabase();

        try {
            return em.find(classObject, id);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public <T> T load(Class<T> classObject, int id) throws DBException {
        return getObjectById(classObject, id);
    }

    // Private helper method that checks that the database is in a valid
    // state to execute against.  Throws an exception if it is not.
    protected void checkDatabase() throws DBException {
        if (em == null || !em.isOpen()) {
            throw new DBException("Database is closed");
        }
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }
}
