package com.mycompany.source;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManager {

    private static final javax.persistence.EntityManager ENTITY_MANAGER;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("flyings");
        ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static javax.persistence.EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }

    public static void close() {
        ENTITY_MANAGER.close();
        ENTITY_MANAGER_FACTORY.close();
    }
}