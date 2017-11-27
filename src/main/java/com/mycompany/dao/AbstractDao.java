package com.mycompany.dao;

import com.mycompany.source.EntityManager;

import java.util.List;

public abstract class AbstractDao<T> {

    private Class entity;
    private String selectObjects;
    javax.persistence.EntityManager entityManager;

    AbstractDao(Class entity) {
        this.entity = entity;
        selectObjects = "select ob" + " from " + entity.getSimpleName() + " ob " + "where id between  ";
        entityManager = EntityManager.getEntityManager();
    }

    public void save(T ob) {
        entityManager.getTransaction().begin();
        entityManager.persist(ob);
        entityManager.getTransaction().commit();
    }

    public List<T> findAll(long firstPositionId, long lastPositionId) {
        String selectObjectsWithInterval = selectObjects + Long.toString(firstPositionId) + " AND " + Long.toString(lastPositionId);
        return entityManager.createQuery(selectObjectsWithInterval).getResultList();
    }

    public void update(T ob) {
        entityManager.getTransaction().begin();
        entityManager.merge(ob);
        entityManager.getTransaction().commit();
    }

    public T findById(long id) {
        return (T) entityManager.find(entity, id);
    }

    public void delete(T ob) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(ob) ? ob : entityManager.merge(ob));
        entityManager.getTransaction().commit();
    }

}
