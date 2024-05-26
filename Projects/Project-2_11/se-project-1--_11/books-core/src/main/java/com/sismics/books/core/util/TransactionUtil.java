package com.sismics.books.core.util;

import com.sismics.util.context.ThreadLocalContext;
import com.sismics.util.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Database transaction utils.
 *
 * @author jtremeaux 
 */
 
 
public class TransactionUtil {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(TransactionUtil.class);
	/**
     * Encapsulate a process into a transactionnal context.
     * 
     * @param runnable
     */
    public static void handle(Runnable runnable) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        
        if (em != null) {
            runnable.run();
            return;
        }
        
        em = createEntityManager();
        if (em == null) {
            return; // Early exit if entity manager could not be created
        }
        
        ThreadLocalContext context = ThreadLocalContext.get();
        context.setEntityManager(em);
        beginTransaction(em);
        
        try {
            runnable.run();
        } catch (Exception e) {
            rollbackTransaction(em, e);
            return; // Early exit after rollback
        } finally {
            commitOrClose(em);
        }
    }

    private static EntityManager createEntityManager() {
        try {
            return EMF.get().createEntityManager();
        } catch (Exception e) {
            log.error("Cannot create entity manager", e);
            return null;
        }
    }

    private static void beginTransaction(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
    }

    private static void rollbackTransaction(EntityManager em, Exception e) {
        ThreadLocalContext.cleanup();
        log.error("An exception occurred, rolling back current transaction", e);
        if (em.isOpen()) {
            EntityTransaction tx = em.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    private static void commitOrClose(EntityManager em) {
        if (em.isOpen()) {
            EntityTransaction tx = em.getTransaction();
            if (tx.isActive()) {
                tx.commit();
            }
            closeEntityManager(em);
        }
    }

    private static void closeEntityManager(EntityManager em) {
        try {
            em.close();
        } catch (Exception e) {
            log.error("Error closing entity manager", e);
        }
    }
}
