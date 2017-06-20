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

    }

    private void ingresoCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        Cliente cliente = formularioCliente(request);
        insert(cliente, em);
        //TODO devolver respueta
        String respuesta = "test";
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/clientes/ingreso.jsp").forward(request, response);

    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        Cliente cliente = em.find(Cliente.class, leerRut(request));
        Cliente data = formularioCliente(request);

        cliente.setNombre(data.getNombre());
        cliente.setDireccion(data.getDireccion());
        cliente.setFono(data.getFono());
        String respuesta = null;
        try{
           update(cliente, em);
           respuesta = "exito";
        }catch(Exception e){
           respuesta = "error";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/clientes/modificar.jsp").forward(request, response);

    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        Cliente cliente = em.find(Cliente.class, leerRut(request));
        delete(cliente, em);
        //TODO
        String respuesta = "test";
        request.setAttribute("respuesta", respuesta);
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
        String nombre = request.getParameter("nombre").trim();
        String direccion = request.getParameter("direccion").trim();
        String fono = request.getParameter("fono").trim();
        return new Cliente(rut, nombre, direccion, fono);
    }

    private String leerRut(HttpServletRequest request) {
        return request.getParameter("rut").trim();
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    private void insert(Cliente cliente, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } finally {
            // Close the database connection:
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Cliente cliente, EntityManager em) {
        em.getTransaction().begin();
        em.remove(cliente);
        em.getTransaction().commit();
    }

    private void update(Cliente cliente, EntityManager em) {
        em.getTransaction().begin();
        em.getTransaction().commit();
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
