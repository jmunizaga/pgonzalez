package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import persistencia.Ficha;

public class FichaDAO extends AbstractDAO {

    EntityManager em;

    public FichaDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Ficha> consulta = em.createNamedQuery("Ficha.findAll", Ficha.class);
        List<Ficha> lista = consulta.getResultList();
        return lista;
    }

    public String insert(Ficha ficha) {
        try {
            super.insert(ficha, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    public String delete(Ficha ficha){
        try{
            super.delete(ficha, em);
            return "registro eliminado";
        }catch(Exception e){
            return "Error al eliminar. Intente nuevamente";
        }
    }
    
    
    public Ficha buscar(String primaryKey){
        
        return em.find(Ficha.class,Integer.parseInt(primaryKey));
    }
     
    public String update(Ficha target, Ficha data){
        try{
            update_db(target, data);
            return "registro actualizado";
        }catch(Exception e){
            return "Error al actualizado. Intente nuevamente";
        }
    }
    
    private void update_db(Ficha target, Ficha data) {
    try {
            em.getTransaction().begin();
            target.setFechaCreacion(data.getFechaCreacion());
            target.setPeso(data.getPeso());
            target.setTamaño(data.getTamaño());
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
