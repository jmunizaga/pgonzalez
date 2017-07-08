package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Ficha;
import persistencia.Mascota;

public class AdminFichas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        if (request.getParameterMap().containsKey("accion")) {
            if (request.getParameter("accion").equals("ingreso")) {
                ingresoFicha(request, response, em);
            }
            if (request.getParameter("accion").equals("modificar")) {
                modificarFicha(request, response, em);
            }
            if (request.getParameter("accion").equals("eliminar")) {
                eliminarFicha(request, response, em);
            }
            if (request.getParameter("accion").equals("listar")) {
                listarFichas(request, response, em);
            }
            if (request.getParameter("accion").equals("buscar")) {
                buscarFichas(request, response, em);
            }
            if (request.getParameter("accion").equals("obtenerMascotas")) {
                obtenerMascotas(request, response, em);
            }
        }
    }

    private void ingresoFicha(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Ficha ficha = formularioFicha(request);
        String respuesta;
        try {
            insert(ficha, em);
            respuesta = "Ficha Ingresado";
        } catch (Exception e) {
            respuesta = "Ficha existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/fichas/ingreso.jsp").forward(request, response);

    }

    private void modificarFicha(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        String respuesta = "";
        Ficha ficha = em.find(Ficha.class, leerPrimaryKey(request));
        if (ficha == null) {
            respuesta = "Ficha no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Ficha data = formularioFicha(request);
            if (data.getTamaño() == -1) {
                request.setAttribute("ficha", ficha);
            } else {
                try {
                    respuesta = "Ficha Actualizado";
                    update(ficha, data, em);
                } catch (Exception e) {
                    respuesta = "Error al actualizar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/fichas/modificar.jsp").forward(request, response);

    }

    private void eliminarFicha(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Ficha ficha = em.find(Ficha.class, leerPrimaryKey(request));
        if (ficha == null) {
            respuesta = "Ficha no Existe";
            request.setAttribute("respuesta", respuesta);
        } else if (!request.getParameterMap().containsKey("peso")) {
            request.setAttribute("ficha", ficha);
        } else {
            try {
                respuesta = "Ficha Eliminado";
                delete(ficha, em);
            } catch (Exception e) {
                respuesta = "Error al eliminar. Intente nuevamente";
            } finally {
                request.setAttribute("respuesta", respuesta);
            }
        }
        request.getRequestDispatcher("/fichas/eliminar.jsp").forward(request, response);
    }

    private void obtenerMascotas(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        TypedQuery<Mascota> consultaMascota = em.createNamedQuery("Mascota.findAll", Mascota.class
        );
        //consultaFichas.setParameter("rut");
        List<Mascota> listaMascotas = consultaMascota.getResultList();

        request.setAttribute("listaMascotas", listaMascotas);
        request.getRequestDispatcher("/fichas/ingreso.jsp").forward(request, response);
    }
    
    private void listarFichas(HttpServletRequest request, HttpServletResponse response, EntityManager em) 
            throws ServletException, IOException {
        TypedQuery<Ficha> consultaFichas = em.createNamedQuery("Ficha.findAll", Ficha.class);
        List<Ficha> listaFichas = consultaFichas.getResultList();
        request.setAttribute("listaFichas", listaFichas);
        request.getRequestDispatcher("/fichas/listar.jsp").forward(request, response);
    }

    private void buscarFichas(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        
        String respuesta = "";
        Ficha ficha = em.find(Ficha.class, leerPrimaryKey(request));
        if (ficha == null) {
            respuesta = "Ficha no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
             request.setAttribute("ficha", ficha);
        }

        
        request.getRequestDispatcher("/fichas/busqueda.jsp").forward(request, response);
    }

    private Ficha formularioFicha(HttpServletRequest request) {
        Date fechaCreacion;
        Float peso;
        int tamaño;
        int mascota_id_FK;
        if (request.getParameterMap().containsKey("peso")) {
            //TODO devolver valor de fecha ajustado
            fechaCreacion = Date.valueOf(request.getParameter("fecha_creacion").trim());
            peso = Float.parseFloat(request.getParameter("peso").trim());
            tamaño = Integer.parseInt(request.getParameter("tamano").trim());
            mascota_id_FK = Integer.parseInt(request.getParameter("mascota_id_FK").trim());
        } else {
            fechaCreacion = null;
            peso = -1f;
            tamaño = -1;
            mascota_id_FK = -1;
        }
        Mascota mascotaFK = new Mascota(mascota_id_FK);
        Ficha ficha = new Ficha(fechaCreacion, peso, tamaño);
        ficha.setMascotaidFK(mascotaFK);
        return ficha;
    }

    private int leerPrimaryKey(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id").trim());
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private void insert(Ficha ficha, EntityManager em) {
        Mascota mascotaFK = ficha.getMascotaidFK();
        ficha.setMascotaidFK(em.find(Mascota.class, mascotaFK.getId()));
        try {
            em.getTransaction().begin();
            em.persist(ficha);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Ficha ficha, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(ficha);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void update(Ficha ficha, Ficha data, EntityManager em) {
        try {
            em.getTransaction().begin();
            ficha.setFechaCreacion(data.getFechaCreacion());
            ficha.setPeso(data.getPeso());
            ficha.setTamaño(data.getTamaño());
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
