package servlets;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Raza;
import persistencia.dao.RazaDAO;

public class AdminRazas extends HttpServlet {

    RazaDAO razaDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        razaDAO = new RazaDAO((EntityManagerFactory) getServletContext().getAttribute("emf"));

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoRaza(request, response);
            }
            if (user_action.equals("modificar")) {
                modificarRaza(request, response);
            }
            if (user_action.equals("eliminar")) {
                eliminarRaza(request, response);
            }
            if (user_action.equals("listar")) {
                listarRazas(request, response);
            }
        }
        if (request.getParameterMap().containsKey("buscar")) {
            listarRazas(request, response);
        }
    }

    private void ingresoRaza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Raza raza = getRazaFormulario(request);
        request.setAttribute("respuesta", razaDAO.insert(raza));
        request.getRequestDispatcher("/razas/ingreso.jsp").forward(request, response);

    }

    private void modificarRaza(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Raza target = razaDAO.buscar(request.getParameter("nombre"));
        if (target == null) {
            request.setAttribute("respuesta", "Raza no existe");
        } else if (request.getParameterMap().containsKey("descripcion")) {
            request.setAttribute("respuesta", razaDAO.update(target, getRazaFormulario(request)));
        } else {
            request.setAttribute("raza", target);
        }
        request.getRequestDispatcher("/razas/modificar.jsp").forward(request, response);
    }

    private void eliminarRaza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Raza raza = razaDAO.buscar(request.getParameter("nombre"));
        if (raza == null) {
            request.setAttribute("respuesta", "Raza no Existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", razaDAO.delete(raza));
        } else {
            request.setAttribute("raza", raza);
        }
        request.getRequestDispatcher("/razas/eliminar.jsp").forward(request, response);
    }

    private void listarRazas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaRazas", razaDAO.selectAll());
        request.getRequestDispatcher("/razas/listar.jsp").forward(request, response);
    }

    private Raza getRazaFormulario(HttpServletRequest request) {
        String nombre = request.getParameter("nombre").trim();
        String descripcion;
        if (request.getParameterMap().containsKey("descripcion")) {
            descripcion = request.getParameter("descripcion").trim();
        } else {
            descripcion = "";
        }
        return new Raza(nombre, descripcion);
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
