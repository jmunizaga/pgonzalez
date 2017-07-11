package servlets;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Raza;

public class AdminRazas_old extends HttpServlet {


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
                ingresoRaza(request, response, em);
            }
            if (request.getParameter("accion").equals("modificar")) {
                modificarRaza(request, response, em);
            }
            if (request.getParameter("accion").equals("eliminar")) {
                eliminarRaza(request, response, em);
            }
            if (request.getParameter("accion").equals("listar")) {
                listarRazas(request, response, em);
            }
        }
        if (request.getParameterMap().containsKey("buscar")) {
            listarRazas(request, response, em);
        }
    }

    private void ingresoRaza(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Raza raza = formularioRaza(request);
        String respuesta;
        try {
            insert(raza, em);
            respuesta = "Raza Ingresada";
        } catch (Exception e) {
            respuesta = "Raza existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/razas/ingreso.jsp").forward(request, response);

    }

    private void modificarRaza(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        String respuesta = "";
        Raza raza = em.find(Raza.class, leerPrimaryKey(request));
        if (raza == null) {
            respuesta = "Raza no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Raza data = formularioRaza(request);
            if (data.getNombre().equals("")) {
                request.setAttribute("raza", raza);
            } else {
                try {
                    respuesta = "Raza Actualizada";
                    update(raza, data, em);
                } catch (Exception e) {
                    respuesta = "Error al actualizar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/razas/modificar.jsp").forward(request, response);

    }

    private void eliminarRaza(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Raza raza = em.find(Raza.class, leerPrimaryKey(request));
        if (raza == null) {
            respuesta = "Raza no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Raza data = formularioRaza(request);
            if (data.getDescripcion().equals("")) {
                request.setAttribute("raza", raza);
            } else {
                try {
                    respuesta = "Raza Eliminado";
                    delete(raza, em);
                } catch (Exception e) {
                    respuesta = "Error al eliminar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/razas/eliminar.jsp").forward(request, response);
    }

    private void listarRazas(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        TypedQuery<Raza> consultaRazas = em.createNamedQuery("Raza.findAll", Raza.class
        );
        //consultaRazas.setParameter("rut");
        List<Raza> listaRazas = consultaRazas.getResultList();

        request.setAttribute("listaRazas", listaRazas);
        request.getRequestDispatcher("/razas/listar.jsp").forward(request, response);
    }

    private Raza formularioRaza(HttpServletRequest request) {
        String nombre = request.getParameter("nombre").trim();
        String descripcion;
        if (request.getParameterMap().containsKey("descripcion")) {
            descripcion = request.getParameter("descripcion").trim();
        } else {
            descripcion = "";
        }
        return new Raza(nombre, descripcion);
    }

    private String leerPrimaryKey(HttpServletRequest request) {
        return request.getParameter("nombre").trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private void insert(Raza raza, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(raza);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Raza raza, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(raza);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void update(Raza raza, Raza data, EntityManager em) {
        try {
            em.getTransaction().begin();
            raza.setNombre(data.getNombre());
            raza.setDescripcion(data.getDescripcion());
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
