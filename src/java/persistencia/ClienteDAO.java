package persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class ClienteDAO extends AbstractDAO {

    EntityManager em;

    public ClienteDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Cliente> consulta = em.createNamedQuery("Cliente.findAll", Cliente.class);
        List<Cliente> lista = consulta.getResultList();
        return lista;
    }

    public String insert(Cliente cliente) {
        try {
            super.insert(cliente, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    public String delete(Cliente cliente){
        try{
            super.delete(cliente, em);
            return "registro eliminado";
        }catch(Exception e){
            return "Error al eliminar. Intente nuevamente";
        }
    }
    
    
    public Cliente buscar(String primaryKey){
        return em.find(Cliente.class, primaryKey);
    }
     
    public String update(Cliente target, Cliente data){
        try{
            update_db(target, data);
            return "registro actualizado";
        }catch(Exception e){
            return "Error al actualizado. Intente nuevamente";
        }
    }
    
    private void update_db(Cliente target, Cliente data) {
        try {
            em.getTransaction().begin();
            target.setNombre(data.getNombre());
            target.setDireccion(data.getDireccion());
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
