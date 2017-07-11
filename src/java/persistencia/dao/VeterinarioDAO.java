package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import persistencia.Veterinario;

public class VeterinarioDAO extends AbstractDAO {

    EntityManager em;

    public VeterinarioDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Veterinario> consulta = em.createNamedQuery("Veterinario.findAll", Veterinario.class);
        List<Veterinario> lista = consulta.getResultList();
        return lista;
    }

    public String insert(Veterinario veterinario) {
        try {
            super.insert(veterinario, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    public String delete(Veterinario veterinario){
        try{
            super.delete(veterinario, em);
            return "registro eliminado";
        }catch(Exception e){
            return "Error al eliminar. Intente nuevamente";
        }
    }
    
    
    public Veterinario buscar(String primaryKey){
        return em.find(Veterinario.class, primaryKey);
    }
     
    public String update(Veterinario target, Veterinario data){
        try{
            update_db(target, data);
            return "registro actualizado";
        }catch(Exception e){
            return "Error al actualizado. Intente nuevamente";
        }
    }
    
    private void update_db(Veterinario target, Veterinario data) {
        try {
            em.getTransaction().begin();
            target.setNombre(data.getNombre());
            target.setFono(data.getFono());
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
