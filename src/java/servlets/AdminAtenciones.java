/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import persistencia.Atencion;
import persistencia.Cliente;
import persistencia.Ficha;
import persistencia.Mascota;
import persistencia.Raza;
import persistencia.Veterinario;

public class AdminAtenciones extends HttpServlet {

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
                ingresoAtencion(request, response, em);
            }
            if (request.getParameter("accion").equals("obtenerDatos")) {
                obtenerDatos(request, response, em);

            }
        }
    }

    private void ingresoAtencion(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Atencion atencion = formularioAtencion(request);
        String respuesta;
        try {
            insert(atencion, em);
            respuesta = "Atencion Ingresada";
        } catch (Exception e) {
            respuesta = "Atencion existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/atenciones/ingreso.jsp").forward(request, response);

    }
   

    private void obtenerDatos(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        obtenerFichas(request, em);
        obtenerVeterinarios(request, em);
        request.getRequestDispatcher("/atenciones/ingreso.jsp").forward(request, response);

    }

    private void obtenerFichas(HttpServletRequest request, EntityManager em) {
        TypedQuery<Ficha> consultaFicha = em.createNamedQuery("Ficha.findAll", Ficha.class);
        List<Ficha> listaFichas = consultaFicha.getResultList();
        request.setAttribute("listaFichas", listaFichas);
    }

    private void obtenerVeterinarios(HttpServletRequest request, EntityManager em) {
        TypedQuery<Veterinario> consultaVeterinario = em.createNamedQuery("Veterinario.findAll", Veterinario.class);
        List<Veterinario> listaVeterinarios = consultaVeterinario.getResultList();
        request.setAttribute("listaVeterinarios", listaVeterinarios);
    }


    private Atencion formularioAtencion(HttpServletRequest request) {
        Date fechaAtencion;
        String diagnostico;
        String tratamiento;
        String observacion;
        String veterinario_rut_FK;
        int ficha_id_FK;
        if (request.getParameterMap().containsKey("tratamiento")) {
            //TODO devolver valor de fecha ajustado
            fechaAtencion = Date.valueOf(request.getParameter("fecha_atencion").trim());
            diagnostico = request.getParameter("diagnostico").trim();
            tratamiento = request.getParameter("tratamiento").trim();
            observacion = request.getParameter("observacion").trim();
            veterinario_rut_FK = request.getParameter("veterinario_rut_FK").trim();
            ficha_id_FK = Integer.parseInt(request.getParameter("ficha_id_FK").trim());
        } else {
            fechaAtencion = null;
            diagnostico = null;
            tratamiento = null;
            observacion = null;
            veterinario_rut_FK = null;
            ficha_id_FK = -1;
        }
        Veterinario veterinarioFK = new Veterinario(veterinario_rut_FK);
        Ficha fichaFK = new Ficha(ficha_id_FK);
        Atencion atencion = new Atencion(fechaAtencion,diagnostico,tratamiento);
        atencion.setObservacion(observacion);
        atencion.setVeterinariorutFK(veterinarioFK);
        atencion.setFichaidFK(fichaFK);
        return atencion;
    }

    private int leerPrimaryKey(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id").trim());
    }

    //<editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    
    private void insert(Atencion atencion, EntityManager em) {
        Veterinario veterinarioFK = atencion.getVeterinariorutFK();
        Ficha fichaFK = atencion.getFichaidFK();
        atencion.setVeterinariorutFK(em.find(Veterinario.class, veterinarioFK.getRut()));
        atencion.setFichaidFK(em.find(Ficha.class, fichaFK.getId()));
        try {
            em.getTransaction().begin();
            em.persist(atencion);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }// </editor-fold>
    
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
