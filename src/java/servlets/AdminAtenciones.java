package servlets;

import java.io.IOException;
import java.sql.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Atencion;
import persistencia.Ficha;
import persistencia.Veterinario;
import persistencia.dao.AtencionDAO;
import persistencia.dao.FichaDAO;
import persistencia.dao.VeterinarioDAO;

public class AdminAtenciones extends HttpServlet {

    AtencionDAO atencionDAO;
    FichaDAO fichaDAO;
    VeterinarioDAO vetDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        atencionDAO = new AtencionDAO(emf);
        fichaDAO = new FichaDAO(emf);
        vetDAO = new VeterinarioDAO(emf);

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoAtencion(request, response);
            }
            if (user_action.equals("obtenerDatos")) {
                obtenerDatos(request, response);

            }
        }
    }

    private void ingresoAtencion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Atencion atencion = getAtencionFormulario(request);
        atencion.setVeterinariorutFK(vetDAO.buscar(atencion.getVeterinariorutFK().getRut()));
        atencion.setFichaidFK(fichaDAO.buscar(atencion.getFichaidFK().getId().toString()));
        request.setAttribute("respuesta", atencionDAO.insert(atencion));
        request.getRequestDispatcher("/atenciones/ingreso.jsp").forward(request, response);

    }

    private void obtenerDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaFichas", fichaDAO.selectAll());
        request.setAttribute("listaVeterinarios", vetDAO.selectAll());
        request.getRequestDispatcher("/atenciones/ingreso.jsp").forward(request, response);

    }

    private Atencion getAtencionFormulario(HttpServletRequest request) {
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
        Atencion atencion = new Atencion(fechaAtencion, diagnostico, tratamiento);
        atencion.setObservacion(observacion);
        atencion.setVeterinariorutFK(veterinarioFK);
        atencion.setFichaidFK(fichaFK);
        return atencion;
    }
    
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
