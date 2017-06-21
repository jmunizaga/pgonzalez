/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import persistencia.Veterinario;

public class AdminVeterinarios extends HttpServlet {

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
                ingresoVeterinario(request, response, em);
            }
            if (request.getParameter("accion").equals("modificar")) {
                modificarVeterinario(request, response, em);
            }
            if (request.getParameter("accion").equals("eliminar")) {
                eliminarVeterinario(request, response, em);
            }
            if (request.getParameter("accion").equals("listar")) {
                listarVeterinarios(request, response, em);
            }
            if (request.getParameter("accion").equals("buscar")) {
                buscarVeterinarios(request, response, em);
            }
        }
        if (request.getParameterMap().containsKey("buscar")) {
            listarVeterinarios(request, response, em);
        }
    }
    private void buscarVeterinarios(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Veterinario veterinario = em.find(Veterinario.class, leerPrimaryKey(request));
        if (veterinario == null) {
            respuesta = "Veterinario no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            request.setAttribute("veterinario", veterinario);
        }

        request.getRequestDispatcher("/veterinarios/busqueda.jsp").forward(request, response);
    }

    private void ingresoVeterinario(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Veterinario veterinario = formularioVeterinario(request);
        String respuesta;
        try {
            insert(veterinario, em);
            respuesta = "Veterinario Ingresado";
        } catch (Exception e) {
            respuesta = "Veterinario existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/veterinarios/ingreso.jsp").forward(request, response);

    }

    private void modificarVeterinario(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        String respuesta = "";
        Veterinario veterinario = em.find(Veterinario.class, leerPrimaryKey(request));
        if (veterinario == null) {
            respuesta = "Veterinario no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Veterinario data = formularioVeterinario(request);
            if (data.getNombre().equals("")) {
                request.setAttribute("veterinario", veterinario);
            } else {
                try {
                    respuesta = "Veterinario Actualizado";
                    update(veterinario, data, em);
                } catch (Exception e) {
                    respuesta = "Error al actualizar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/veterinarios/modificar.jsp").forward(request, response);

    }

    private void eliminarVeterinario(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Veterinario veterinario = em.find(Veterinario.class, leerPrimaryKey(request));
        if (veterinario == null) {
            respuesta = "Veterinario no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Veterinario data = formularioVeterinario(request);
            if (data.getNombre().equals("")) {
                request.setAttribute("veterinario", veterinario);
            } else {
                try {
                    respuesta = "Veterinario Eliminado";
                    delete(veterinario, em);
                } catch (Exception e) {
                    respuesta = "Error al eliminar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/veterinarios/eliminar.jsp").forward(request, response);
    }

    private void listarVeterinarios(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        TypedQuery<Veterinario> consultaVeterinarios = em.createNamedQuery("Veterinario.findAll", Veterinario.class
        );
        //consultaVeterinarios.setParameter("rut");
        List<Veterinario> listaVeterinarios = consultaVeterinarios.getResultList();

        request.setAttribute("listaVeterinarios", listaVeterinarios);
        request.getRequestDispatcher("/veterinarios/listar.jsp").forward(request, response);
    }

    private Veterinario formularioVeterinario(HttpServletRequest request) {
        String rut = request.getParameter("rut").trim();
        String nombre;
        String fono;
        if (request.getParameterMap().containsKey("nombre")) {
            nombre = request.getParameter("nombre").trim();
            fono = request.getParameter("fono").trim();
        } else {
            nombre = "";
            fono = "";
        }
        return new Veterinario(rut, nombre, fono);
    }

    private String leerPrimaryKey(HttpServletRequest request) {
        return request.getParameter("rut").trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private void insert(Veterinario veterinario, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(veterinario);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Veterinario veterinario, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(veterinario);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void update(Veterinario veterinario, Veterinario data, EntityManager em) {
        try {
            em.getTransaction().begin();
            veterinario.setNombre(data.getNombre());
            veterinario.setFono(data.getFono());
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
