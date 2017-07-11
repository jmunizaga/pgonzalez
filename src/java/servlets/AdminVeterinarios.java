package servlets;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Veterinario;
import persistencia.dao.VeterinarioDAO;

public class AdminVeterinarios extends HttpServlet {

    VeterinarioDAO veterinarioDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        veterinarioDAO = new VeterinarioDAO((EntityManagerFactory) getServletContext().getAttribute("emf"));

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoVeterinario(request, response);
            }
            if (user_action.equals("modificar")) {
                modificarVeterinario(request, response);
            }
            if (user_action.equals("eliminar")) {
                eliminarVeterinario(request, response);
            }
            if (user_action.equals("listar")) {
                listarVeterinarios(request, response);
            }
            if (user_action.equals("buscar")) {
                buscarVeterinarios(request, response);
            }
        }
        if (request.getParameterMap().containsKey("buscar")) {
            listarVeterinarios(request, response);
        }
    }

    private void buscarVeterinarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Veterinario veterinario = veterinarioDAO.buscar(request.getParameter("rut"));
        if (veterinario == null) {
            request.setAttribute("respuesta", "Veterinario no existe");
        } else {
            request.setAttribute("veterinario", veterinario);
        }
        request.getRequestDispatcher("/veterinarios/busqueda.jsp").forward(request, response);
    }

    private void ingresoVeterinario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Veterinario veterinario = getVeterinarioFormulario(request);
        request.setAttribute("respuesta", veterinarioDAO.insert(veterinario));
        request.getRequestDispatcher("/veterinarios/ingreso.jsp").forward(request, response);

    }

    private void modificarVeterinario(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Veterinario target = veterinarioDAO.buscar(request.getParameter("rut"));
        if (target == null) {
            request.setAttribute("respuesta", "Veterinario no existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", veterinarioDAO.update(target, getVeterinarioFormulario(request)));
        } else {
            request.setAttribute("veterinario", target);
        }
        request.getRequestDispatcher("/veterinarios/modificar.jsp").forward(request, response);
    }

    private void eliminarVeterinario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Veterinario veterinario = veterinarioDAO.buscar(request.getParameter("rut"));
        if (veterinario == null) {
            request.setAttribute("respuesta", "Veterinario no Existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", veterinarioDAO.delete(veterinario));
        } else {
            request.setAttribute("veterinario", veterinario);
        }
        request.getRequestDispatcher("/veterinarios/eliminar.jsp").forward(request, response);
    }

    private void listarVeterinarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaVeterinarios", veterinarioDAO.selectAll());
        request.getRequestDispatcher("/veterinarios/listar.jsp").forward(request, response);
    }

    private Veterinario getVeterinarioFormulario(HttpServletRequest request) {
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
