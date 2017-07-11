package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import persistencia.Mascota;

public class MascotaDAO extends AbstractDAO {

    EntityManager em;

    public MascotaDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Mascota> consulta = em.createNamedQuery("Mascota.findAll", Mascota.class);
        List<Mascota> lista = consulta.getResultList();
        return lista;
    }

    public String insert(Mascota mascota) {
        try {
            super.insert(mascota, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    public String delete(Mascota mascota){
        try{
            super.delete(mascota, em);
            return "registro eliminado";
        }catch(Exception e){
            return "Error al eliminar. Intente nuevamente";
        }
    }
    
    
    public Mascota buscar(String primaryKey){
        return em.find(Mascota.class, Integer.parseInt(primaryKey));
    }
     
    public String update(Mascota target, Mascota data){
        try{
            update_db(target, data);
            return "registro actualizado";
        }catch(Exception e){
            return "Error al actualizado. Intente nuevamente";
        }
    }
    
    private void update_db(Mascota target, Mascota data) {
//        try {
//            em.getTransaction().begin();
//            target.setNombre(data.getNombre());
//            target.setDireccion(data.getDireccion());
//            target.setFono(data.getFono());
//            em.getTransaction().commit();
//        } finally {
//            // Cerrar la conexion
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            em.close();
//        }
    }
}
