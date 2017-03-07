package org.curtis.database;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * DatabaseItemManager adds an abstraction layer on top of DBSession to create a simple
 * interface for managing the persistence of all DatabaseItems without having to
 * think about databases connections, transaction, etc. Its core methods are get, save
 * and remove and by default it should be used as a Singleton by calling getInstance().
 * This instance uses the DBTransaction from DBSessionFactory.getDBTransaction() that
 * associates one transaction per request.
 * <p/>
 * DatabaseItemManager can also be instantiated to use DBSessionFactory.getDBSession()
 * via getSessionInstance(), or a constructor that accepts a DBSession or DBTransaction
 * as an argument, which overrides the default DBTransaction. In this situation the
 * DBTransaction must be handled outside the DatabaseItemManager. These should only be used in
 * special occasions where an atomic action or small sequence of actions needs to be
 * executed and committed in a self contained method.
 *
 * @author curtis
 */
public class DatabaseItemManager {
    private static DatabaseItemManager instance = null;

    /**
     * Defaults to using the standard DBTransaction from
     * DBSessionFactory.getTransaction()
     *
     * @return instance of DatabaseItemManager
     * @throws DBException if DBSessionFactory cannot be accessed
     */
    public static synchronized DatabaseItemManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DatabaseItemManager();
        }

        return instance;
    }

    /**
     * Load a single Object instance of Class T from persistence by its unique id.
     *
     * @param classType Class type to find
     * @param id        unique integer identity
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, int id) throws DBException {
        return getEntityManager().find(classType, id);
    }

    /**
     * Load a List of Object instances of Class T from persistence.
     *
     * @param classType Class type to find
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        criteria.orderBy(cb.asc(root.get("id")));
        Query query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    /**
     * Load a List of Object instances of Class T from persistence with (offset,limit).
     *
     * @param classType Class type to find
     * @param offset    starting offset
     * @param limit     max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, int offset, int limit) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        criteria.orderBy(cb.asc(root.get("id")));
        Query query = entityManager.createQuery(criteria);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    /**
     * Load a single Object instance of Class T from persistence by a field
     * and value pair, if there are multiple results on the first is returned.
     *
     * @param classType Class type to find
     * @param field     field name to search on
     * @param value     value of field name to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, String field, Object value) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        criteria.where(cb.equal(root.get(field), value));
        Query query = entityManager.createQuery(criteria);

        return (T)query.getSingleResult();
    }

    /**
     * Load a single Object instance of DatabaseItem Class T from
     * persistence using a field and value pair, if there are multiple results
     * on the first is returned.
     *
     * @param classType Class type to find
     * @param fields    list of field names to search on
     * @param values    list of values of field names to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, List<String> fields, List values) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        for (int i = 0; i <= fields.size(); i++) {
            criteria.where(cb.equal(root.get(fields.get(i)), values.get(i)));
        }
        Query query = entityManager.createQuery(criteria);

        return (T)query.getSingleResult();
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param field     field name to search on
     * @param value     value of field name to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, String field, Object value) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        criteria.where(cb.equal(root.get(field), value));
        Query query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param fields    list of field names to search on
     * @param values    list of values of field names to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, List<String> fields, List values) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        for (int i = 0; i <= fields.size(); i++) {
            criteria.where(cb.equal(root.get(fields.get(i)), values.get(i)));
        }
        Query query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param offset starting offset
     * @param limit  max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, List<String> fields, List values, int offset, int limit) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        for (int i = 0; i <= fields.size(); i++) {
            criteria.where(cb.equal(root.get(fields.get(i)), values.get(i)));
        }
        Query query = entityManager.createQuery(criteria);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param offset starting offset
     * @param limit  max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, String field, Object value, int offset, int limit) throws DBException {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(classType);
        Root<T> root = criteria.from(classType);
        criteria.where(cb.equal(root.get(field), value));
        Query query = entityManager.createQuery(criteria);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public DBTransaction getDatabase() throws DBException {
        return DBSessionFactory.getInstance().getTransaction();
    }

    private EntityManager getEntityManager() throws DBException {
        return getDatabase().getEntityManager();
    }
}
