package servlets;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Cliente;
import persistencia.dao.ClienteDAO;

public class AdminClientes extends HttpServlet {

    ClienteDAO clienteDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        clienteDAO = new ClienteDAO((EntityManagerFactory) getServletContext().getAttribute("emf"));

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoCliente(request, response);
            }
            if (user_action.equals("modificar")) {
                modificarCliente(request, response);
            }
            if (user_action.equals("eliminar")) {
                eliminarCliente(request, response);
            }
            if (user_action.equals("listar")) {
                listarClientes(request, response);
            }
            if (user_action.equals("buscar")) {
                buscarClientes(request, response);
            }

        }
    }

    private void buscarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cliente cliente = clienteDAO.buscar(request.getParameter("rut"));
        if (cliente == null) {
            request.setAttribute("respuesta", "Cliente no existe");
        } else {
            request.setAttribute("cliente",cliente);
        }
        request.getRequestDispatcher("/clientes/busqueda.jsp").forward(request, response);
    }

    private void ingresoCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = getClienteFormulario(request);
        request.setAttribute("respuesta", clienteDAO.insert(cliente));
        request.getRequestDispatcher("/clientes/ingreso.jsp").forward(request, response);

    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Cliente target = clienteDAO.buscar(request.getParameter("rut"));
        if (target == null) {
            request.setAttribute("respuesta", "Cliente no existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", clienteDAO.update(target, getClienteFormulario(request)));
        } else {
            request.setAttribute("cliente", target);
        }
        request.getRequestDispatcher("/clientes/modificar.jsp").forward(request, response);
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cliente cliente = clienteDAO.buscar(request.getParameter("rut"));
        if (cliente == null) {
            request.setAttribute("respuesta", "Cliente no Existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", clienteDAO.delete(cliente));
        } else {
            request.setAttribute("cliente", cliente);
        }
        request.getRequestDispatcher("/clientes/eliminar.jsp").forward(request, response);
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaClientes", clienteDAO.selectAll());
        request.getRequestDispatcher("/clientes/listar.jsp").forward(request, response);
    }

    private Cliente getClienteFormulario(HttpServletRequest request) {
        String rut = request.getParameter("rut");
        String nombre = "";
        String direccion = "";
        String fono = "";
        if (request.getParameterMap().containsKey("nombre")) {
            nombre = request.getParameter("nombre").toString().trim();
            direccion = request.getParameter("direccion").toString().trim();
            fono = request.getParameter("fono").toString().trim();
        }
        return new Cliente(rut, nombre, direccion, fono);
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
