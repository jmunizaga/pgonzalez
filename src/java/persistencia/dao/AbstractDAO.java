package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;

public abstract class AbstractDAO {

    abstract List selectAll();
   
    protected void insert(Object registro, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(registro);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
    protected void delete(Object registro, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(registro);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    
    
}
