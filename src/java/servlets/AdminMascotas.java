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
import persistencia.Cliente;
import persistencia.Mascota;
import persistencia.Raza;

public class AdminMascotas extends HttpServlet {

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
                ingresoMascota(request, response, em);
            }
            if (request.getParameter("accion").equals("modificar")) {
                modificarMascota(request, response, em);
            }
            if (request.getParameter("accion").equals("eliminar")) {
                eliminarMascota(request, response, em);
            }
            if (request.getParameter("accion").equals("listar")) {
                listarMascotas(request, response, em);
            }
            if (request.getParameter("accion").equals("obtenerDatos")) {
                obtenerDatos(request, response, em);

            }
        }
    }

    private void ingresoMascota(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        Mascota mascota = formularioMascota(request);
        String respuesta;
        try {
            insert(mascota, em);
            respuesta = "Mascota Ingresado";
        } catch (Exception e) {
            respuesta = "Mascota existente o error al ingresar. Intente nuevamente";
        }
        request.setAttribute("respuesta", respuesta);
        request.getRequestDispatcher("/mascotas/ingreso.jsp").forward(request, response);

    }

    private void modificarMascota(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws IOException, ServletException {
        String respuesta = "";
        Mascota mascota = em.find(Mascota.class, leerPrimaryKey(request));
        if (mascota == null) {
            respuesta = "Mascota no Existe";
            request.setAttribute("respuesta", respuesta);
        } else {
            Mascota data = formularioMascota(request);
            if (data.getNombre() == null) {
                request.setAttribute("mascota", mascota);
            } else {
                try {
                    respuesta = "Mascota Actualizada";
                    update(mascota, data, em);
                } catch (Exception e) {
                    respuesta = "Error al actualizar. Intente nuevamente";
                } finally {
                    request.setAttribute("respuesta", respuesta);
                }
            }

        }
        request.getRequestDispatcher("/mascotas/modificar.jsp").forward(request, response);

    }

    private void eliminarMascota(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        String respuesta = "";
        Mascota mascota = em.find(Mascota.class, leerPrimaryKey(request));
        if (mascota == null) {
            respuesta = "Mascota no Existe";
            request.setAttribute("respuesta", respuesta);
        } else if (!request.getParameterMap().containsKey("peso")) {
            request.setAttribute("mascota", mascota);
        } else {
            try {
                respuesta = "Mascota Eliminado";
                delete(mascota, em);
            } catch (Exception e) {
                respuesta = "Error al eliminar. Intente nuevamente";
            } finally {
                request.setAttribute("respuesta", respuesta);
            }
        }
        request.getRequestDispatcher("/mascotas/eliminar.jsp").forward(request, response);
    }

    private void obtenerDatos(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {
        obtenerClientes(request, em);
        obtenerRazas(request, em);
        request.getRequestDispatcher("/mascotas/ingreso.jsp").forward(request, response);

    }

    private void obtenerClientes(HttpServletRequest request, EntityManager em) {
        TypedQuery<Cliente> consultaCliente = em.createNamedQuery("Cliente.findAll", Cliente.class);
        List<Cliente> listaClientes = consultaCliente.getResultList();
        request.setAttribute("listaClientes", listaClientes);
    }

    private void obtenerRazas(HttpServletRequest request, EntityManager em) {
        TypedQuery<Raza> consultaRaza = em.createNamedQuery("Raza.findAll", Raza.class);
        List<Raza> listaRazas = consultaRaza.getResultList();
        request.setAttribute("listaRazas", listaRazas);
    }

    private void listarMascotas(HttpServletRequest request, HttpServletResponse response, EntityManager em)
            throws ServletException, IOException {

        TypedQuery<Mascota> consultaMascotas = em.createNamedQuery("Mascota.findAll", Mascota.class);
        List<Mascota> listaMascotas = consultaMascotas.getResultList();

        request.setAttribute("listaMascotas", listaMascotas);
        request.getRequestDispatcher("/mascotas/listar.jsp").forward(request, response);
    }

    private Mascota formularioMascota(HttpServletRequest request) {
        Date fechaNac;
        String sexo;
        String nombre;
        String cliente_rut_FK;
        String raza_nombre_FK;
        if (request.getParameterMap().containsKey("nombre")) {
            //TODO devolver valor de fecha ajustado
            fechaNac = Date.valueOf(request.getParameter("fecha_nac").trim());
            sexo = request.getParameter("sexo").trim();
            nombre = request.getParameter("nombre").trim();
            cliente_rut_FK = request.getParameter("cliente_rut_FK").trim();
            raza_nombre_FK = request.getParameter("raza_nombre_FK").trim();
        } else {
            fechaNac = null;
            sexo = null;
            nombre = null;
            cliente_rut_FK = null;
            raza_nombre_FK = null;
        }
        Cliente clienteFK = new Cliente(cliente_rut_FK);
        Raza razaFK = new Raza(raza_nombre_FK);
        Mascota mascota = new Mascota(nombre, fechaNac, sexo);
        mascota.setClienterutFK(clienteFK);
        mascota.setRazanombreFK(razaFK);
        return mascota;
    }

    private int leerPrimaryKey(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id").trim());
    }

    //<editor-fold defaultstate="collapsed" desc="Metodos de manipulacion de base de datos.">
    
    private void insert(Mascota mascota, EntityManager em) {
        Cliente clienteFK = mascota.getClienterutFK();
        Raza razaFK = mascota.getRazanombreFK();
        mascota.setClienterutFK(em.find(Cliente.class, clienteFK.getRut()));
        mascota.setRazanombreFK(em.find(Raza.class, razaFK.getNombre()));
        try {
            em.getTransaction().begin();
            em.persist(mascota);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void delete(Mascota mascota, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.remove(mascota);
            em.getTransaction().commit();
        } finally {
            // Cerrar la conexion
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private void update(Mascota mascota, Mascota data, EntityManager em) {
        try {
            em.getTransaction().begin();
            mascota.setNombre(data.getNombre());
            mascota.setFechaNac(data.getFechaNac());
            mascota.setSexo(data.getSexo());
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
