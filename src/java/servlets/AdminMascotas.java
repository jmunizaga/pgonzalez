package servlets;

import java.io.IOException;
import java.sql.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Cliente;
import persistencia.Mascota;
import persistencia.Raza;
import persistencia.dao.ClienteDAO;
import persistencia.dao.MascotaDAO;
import persistencia.dao.RazaDAO;

public class AdminMascotas extends HttpServlet {

    MascotaDAO mascotaDAO;
    ClienteDAO clienteDAO;
    RazaDAO razaDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        mascotaDAO = new MascotaDAO(emf);
        clienteDAO = new ClienteDAO(emf);
        razaDAO = new RazaDAO(emf);

        if (request.getParameterMap().containsKey("accion")) {
            String user_action = request.getParameter("accion");
            if (user_action.equals("ingreso")) {
                ingresoMascota(request, response);
            }
            if (user_action.equals("modificar")) {
                modificarMascota(request, response);
            }
            if (user_action.equals("eliminar")) {
                eliminarMascota(request, response);
            }
            if (user_action.equals("listar")) {
                listarMascotas(request, response);
            }
            if (user_action.equals("obtenerDatos")) {
                obtenerDatos(request, response);
            }
            if (user_action.equals("buscar")) {
                buscarMascotas(request, response);
            }

        }
    }

    private void buscarMascotas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Mascota mascota = mascotaDAO.buscar(request.getParameter("id"));
        if (mascota == null) {
            request.setAttribute("respuesta", "Mascota no existe");
        } else {
            request.setAttribute("mascota", mascota);
        }
        request.getRequestDispatcher("/mascotas/busqueda.jsp").forward(request, response);
    }

    private void ingresoMascota(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Mascota mascota = getMascotaFormulario(request);
        mascota.setClienterutFK(clienteDAO.buscar(mascota.getClienterutFK().getRut()));
        mascota.setRazanombreFK(razaDAO.buscar(mascota.getRazanombreFK().getNombre()));
        request.setAttribute("respuesta", mascotaDAO.insert(mascota));
        request.getRequestDispatcher("/mascotas/ingreso.jsp").forward(request, response);

    }

    private void modificarMascota(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Mascota target = mascotaDAO.buscar(request.getParameter("id"));
        if (target == null) {
            request.setAttribute("respuesta", "Mascota no existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", mascotaDAO.update(target, getMascotaFormulario(request)));
        } else {
            request.setAttribute("mascota", target);
        }
        request.getRequestDispatcher("/mascotas/modificar.jsp").forward(request, response);
    }

    private void eliminarMascota(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Mascota mascota = mascotaDAO.buscar(request.getParameter("id"));
        if (mascota == null) {
            request.setAttribute("respuesta", "Mascota no Existe");
        } else if (request.getParameterMap().containsKey("nombre")) {
            request.setAttribute("respuesta", mascotaDAO.delete(mascota));
        } else {
            request.setAttribute("mascota", mascota);
        }
        request.getRequestDispatcher("/mascotas/eliminar.jsp").forward(request, response);
    }

    private void listarMascotas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaMascotas", mascotaDAO.selectAll());
        request.getRequestDispatcher("/mascotas/listar.jsp").forward(request, response);
    }

    private void obtenerDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listaClientes", clienteDAO.selectAll());
        request.setAttribute("listaRazas", razaDAO.selectAll());
        request.getRequestDispatcher("/mascotas/ingreso.jsp").forward(request, response);

    }

    private Mascota getMascotaFormulario(HttpServletRequest request) {
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
