package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import persistencia.Raza;

public class RazaDAO extends AbstractDAO {

    EntityManager em;

    public RazaDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Raza> consulta = em.createNamedQuery("Raza.findAll", Raza.class);
        List<Raza> lista = consulta.getResultList();
        return lista;
    }

    public String insert(Raza raza) {
        try {
            super.insert(raza, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    public String delete(Raza raza){
        try{
            super.delete(raza, em);
            return "registro eliminado";
        }catch(Exception e){
            return "Error al eliminar. Intente nuevamente";
        }
    }
    
    
    public Raza buscar(String primaryKey){
        return em.find(Raza.class, primaryKey);
    }
     
    public String update(Raza target, Raza data){
        try{
            update_db(target, data);
            return "registro actualizado";
        }catch(Exception e){
            return "Error al actualizado. Intente nuevamente";
        }
    }
    
    private void update_db(Raza target, Raza data) {
        try {
            em.getTransaction().begin();
            target.setNombre(data.getNombre());
            target.setDescripcion(data.getDescripcion());
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
