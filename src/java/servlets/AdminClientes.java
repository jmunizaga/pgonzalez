package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Cliente;

public class AdminClientes extends HttpServlet {

//    @PersistenceUnit
//    EntityManagerFactory emf;
//
//    @PersistenceContext
//    private EntityManager em; 
//    
//    @Resource
//    UserTransaction utx;
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pgonzalezPU");
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
    }

    private void ingresoCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        Cliente cliente = formularioCliente(request);
        String respuesta = insert(cliente, em);
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/clientes/ingreso.jsp").forward(request, response);
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em) {

        Cliente cliente = em.find(Cliente.class, leerRut(request));
        delete(cliente, em);
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response, EntityManager em) 
            throws ServletException, IOException {

        TypedQuery<Cliente> consultaClientes = em.createNamedQuery("Cliente.findAll", Cliente.class);
        //consultaClientes.setParameter("rut");
        List<Cliente> listaClientes = consultaClientes.getResultList();

        request.setAttribute("listaClientes", listaClientes);
        request.getRequestDispatcher("/clientes/listar.jsp").forward(request, response);
    }

    private Cliente formularioCliente(HttpServletRequest request) {
        String rut = request.getParameter("rut").trim();
        String nombre = request.getParameter("nombre").trim();
        String direccion = request.getParameter("direccion").trim();
        String fono = request.getParameter("fono").trim();
        return new Cliente(rut, nombre, direccion, fono);
    }

    private String leerRut(HttpServletRequest request) {
        return request.getParameter("rut").trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private String insert(Cliente cliente, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            em.close();
            return "Ingreso Exitoso";
        } catch (Exception e) {
            return "Error ingresando Datos:" + e.toString();
        }

    }

    private void delete(Cliente cliente, EntityManager em) {
        em.getTransaction().begin();
        em.remove(cliente);
        em.getTransaction().commit();
        em.close();

    }

    private void update(Cliente cliente, EntityManager em) {

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
