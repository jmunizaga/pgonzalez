package servlets;

import java.io.IOException;
import java.sql.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Ficha;
import persistencia.Mascota;
import persistencia.dao.MascotaDAO;
import persistencia.dao.FichaDAO;

public class AdminFichas extends HttpServlet {

    FichaDAO fichaDAO;
    MascotaDAO mascotaDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        mascotaDAO = new MascotaDAO(emf);
        fichaDAO = new FichaDAO(emf);

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoFicha(request, response);
            }
            if (user_action.equals("modificar")) {
                modificarFicha(request, response);
            }
            if (user_action.equals("eliminar")) {
                eliminarFicha(request, response);
            }
            if (user_action.equals("listar")) {
                listarFichas(request, response);
            }
            if (user_action.equals("buscar")) {
                buscarFichas(request, response);
            }
            if (user_action.equals("obtenerMascotas")) {
                obtenerMascotas(request, response);
            }
        }
    }

    private void ingresoFicha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Ficha ficha = getFichaFormulario(request);
        ficha.setMascotaidFK(mascotaDAO.buscar(ficha.getMascotaidFK().getId().toString()));
        request.setAttribute("respuesta", fichaDAO.insert(ficha));
        request.getRequestDispatcher("/fichas/ingreso.jsp").forward(request, response);

    }

    private void modificarFicha(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Ficha target = fichaDAO.buscar(request.getParameter("id"));
        if (target == null) {
            request.setAttribute("respuesta", "Ficha no existe");
        } else if (request.getParameterMap().containsKey("tamano")) {
            request.setAttribute("respuesta", fichaDAO.update(target, getFichaFormulario(request)));
        } else {
            request.setAttribute("ficha", target);
        }
        request.getRequestDispatcher("/fichas/modificar.jsp").forward(request, response);

    }

    private void eliminarFicha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         Ficha ficha = fichaDAO.buscar(request.getParameter("id"));
        if (ficha == null) {
            request.setAttribute("respuesta", "Ficha no Existe");
        } else if (request.getParameterMap().containsKey("peso")) {
            request.setAttribute("respuesta", fichaDAO.delete(ficha));
        } else {
            request.setAttribute("ficha", ficha);
        }
        request.getRequestDispatcher("/fichas/eliminar.jsp").forward(request, response);
    }

    private void obtenerMascotas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("listaMascotas",  mascotaDAO.selectAll());
        request.getRequestDispatcher("/fichas/ingreso.jsp").forward(request, response);
    }

    private void listarFichas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaFichas", fichaDAO.selectAll());
        request.getRequestDispatcher("/fichas/listar.jsp").forward(request, response);
    }

    private void buscarFichas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Ficha ficha = fichaDAO.buscar(request.getParameter("id"));
        if (ficha == null) {
            request.setAttribute("respuesta", "Ficha no existe");
        } else {
            request.setAttribute("ficha",ficha);
        }
        request.getRequestDispatcher("/fichas/busqueda.jsp").forward(request, response);
    }

    private Ficha getFichaFormulario(HttpServletRequest request) {
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
