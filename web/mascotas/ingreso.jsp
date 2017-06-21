<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mascotas - Ingreso</title>
    </head>
    <body>
        <header>
            <h1>Ingreso de Mascotas</h1>
        </header>
        <%
            if (request.getAttribute("listaClientes") == null) {
        %>
        <jsp:forward page="../AdminMascotas?accion=obtenerDatos" />
        <%
            }
        %>

        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <div>
            <form action="../AdminMascotas" method="POST">
                <input type="text" name="accion" value="ingreso" hidden>
                <table>
                    <tr>
                        <td>Cliente Asociado</td><td>
                            <select name="cliente_rut_FK" required>
                                <c:forEach items="${listaClientes}" var="cliente">
                                    <option value="${cliente.getRut()}">
                                        <c:out value="${cliente.getRut()} ${cliente.getNombre()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                    </tr>
                    <tr>
                        <td>Raza</td><td>
                            <select name="raza_nombre_FK" required>
                                <c:forEach items="${listaRazas}" var="raza">
                                    <option value="${raza.getNombre()}">
                                        <c:out value="${raza.getNombre()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                    </tr>
                    <tr>
                        <td>Nombre</td><td><input type="text" name="nombre" required></td>
                    </tr>
                    <tr>
                        <td>Fecha Nacimiento</td><td><input type="date" name="fecha_nac" required></td>
                    </tr>
                    <tr>
                        <td>Sexo</td>
                        <td>
                            <select name="sexo" required>
                                <option value="M">Masculino</option>
                                <option value="F">Femenino</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><button>Ingresar</button></td>
                    </tr>
                </table>
            </form>
        </div>
        <br>
        <footer>
            <a href="menu.jsp">Volver atrás</a>
        </footer>
        <%
        } else {
        %>
        <h2><c:out value="${respuesta}"/></h2>
        <footer>
            <a href="mascotas/menu.jsp">Volver atrás</a>
        </footer>
        <%
            }
        %>
    </body>
</html>
