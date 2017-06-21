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
import persistencia.Cliente;

public class AdminClientes extends HttpServlet {

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
                ingresoCliente(request, response, em);
            }
            if (request.getParameter("accion").equals("modificar")) {
                modificarCliente(request, response, em);
            }
            if (request.getParameter("accion").equals("eliminar")) {
                eliminarCliente(request, response, em);
            }
            if (request.getParameter("accion").equals("listar")) {
                listarClientes(request, response, em);
            }
        }
        //TODO
//        if (request.getParameterMap().containsKey("buscar")) {
//            listarClientes(request, response, em);
//        }
    }

    private void ingresoCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Cliente cliente = formularioCliente(request);
        String respuesta;
        try {
            insert(cliente, em);
            respuesta = "Cliente Ingresado";
        } catch (Exception e) {
            respuesta = "Cliente existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/clientes/ingreso.jsp").forward(request, response);

    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        String respuesta = "";
        Cliente cliente = em.find(Cliente.class, leerPrimaryKey(request));
        if (cliente == null) {
            respuesta = "Cliente no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Cliente data = formularioCliente(request);
            if (data.getNombre().equals("")) {
                request.setAttribute("cliente", cliente);
            } else {
                try {
                    respuesta = "Cliente Actualizado";
                    update(cliente, data, em);
                } catch (Exception e) {
                    respuesta = "Error al actualizar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/clientes/modificar.jsp").forward(request, response);

    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Cliente cliente = em.find(Cliente.class, leerPrimaryKey(request));
        if (cliente == null) {
            respuesta = "Cliente no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Cliente data = formularioCliente(request);
            if (data.getNombre().equals("")) {
                request.setAttribute("cliente", cliente);
            } else {
                try {
                    respuesta = "Cliente Eliminado";
                    delete(cliente, em);
                } catch (Exception e) {
                    respuesta = "Error al eliminar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/clientes/eliminar.jsp").forward(request, response);
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        TypedQuery<Cliente> consultaClientes = em.createNamedQuery("Cliente.findAll", Cliente.class
        );
        //consultaClientes.setParameter("rut");
        List<Cliente> listaClientes = consultaClientes.getResultList();

        request.setAttribute("listaClientes", listaClientes);
        request.getRequestDispatcher("/clientes/listar.jsp").forward(request, response);
    }

    private Cliente formularioCliente(HttpServletRequest request) {
        String rut = request.getParameter("rut").trim();
        String nombre;
        String direccion;
        String fono;
        if (request.getParameterMap().containsKey("nombre")) {
            nombre = request.getParameter("nombre").trim();
            direccion = request.getParameter("direccion").trim();
            fono = request.getParameter("fono").trim();
        } else {
            nombre = "";
            direccion = "";
            fono = "";
        }
        return new Cliente(rut, nombre, direccion, fono);
    }

    private String leerPrimaryKey(HttpServletRequest request) {
        return request.getParameter("rut").trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private void insert(Cliente cliente, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Cliente cliente, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void update(Cliente cliente, Cliente data, EntityManager em) {
        try {
            em.getTransaction().begin();
            cliente.setNombre(data.getNombre());
            cliente.setDireccion(data.getDireccion());
            cliente.setFono(data.getFono());
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
