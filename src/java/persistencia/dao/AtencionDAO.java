package persistencia.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import persistencia.Atencion;

public class AtencionDAO extends AbstractDAO {

    EntityManager em;

    public AtencionDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public List selectAll() {
        TypedQuery<Atencion> consulta = em.createNamedQuery("Atencion.findAll", Atencion.class);
        List<Atencion> lista = consulta.getResultList();
        return lista;
    }
    
    public String insert(Atencion atencion) {
        try {
            super.insert(atencion, em);
            return "Registro ingresado";
        } catch (Exception e) {
            return "Registro existente o error al ingresar. Intente nuevamente";
        }
    }
    
    
    public Atencion buscar(String primaryKey){
        return em.find(Atencion.class, Integer.parseInt(primaryKey));
    }
}
