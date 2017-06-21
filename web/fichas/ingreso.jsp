<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="persistencia.Mascota"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fichas - Ingreso</title>
    </head>
    <body>
        <header>
            <h1>Ingreso de Fichas</h1>
        </header>
        <%
            if (request.getAttribute("listaMascotas") == null) {
        %>
        <jsp:forward page="../AdminFichas?accion=obtenerMascotas" />
        <%
            }
        %>

        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <div>
            <form action="../AdminFichas" method="POST">
                <input type="text" name="accion" value="ingreso" hidden>
                <table>
                    <tr>
                        <td>Mascota Asociada</td><td>
                            <select name="mascota_id_FK" required>
                                <c:forEach items="${listaMascotas}" var="mascota">
                                    <option value="${mascota.getId()}">
                                        <c:out value="${mascota.getNombre()}  // Dueño: ${mascota.getClienterutFK().getRut()}"/>
                                    </option>
                                </c:forEach>
                            </select>

                    </tr>
                    <tr>
                        <td>Fecha Creación</td><td><input type="date" name="fecha_creacion" required></td>
                    </tr>
                    <tr>
                        <td>Peso (kg)</td><td><input type="text" name="peso" maxlength="6" required></td>
                    </tr>
                    <tr>
                        <td>Tamaño (cm)</td><td><input type="number" name="tamano" maxlength="3" required></td>
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
            <a href="fichas/menu.jsp">Volver atrás</a>
        </footer>
        <%
            }
        %>
    </body>
</html>
