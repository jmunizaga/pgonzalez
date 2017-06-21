<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atenciones - Ingreso</title>
    </head>
    <body>
        <header>
            <h1>Ingreso de Atenciones</h1>
        </header>
        <%
            if (request.getAttribute("listaFichas") == null) {
        %>
        <jsp:forward page="../AdminAtenciones?accion=obtenerDatos" />
        <%
            }
        %>

        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <div>
            <form action="../AdminAtenciones" method="POST">
                <input type="text" name="accion" value="ingreso" hidden>
                <table>
                    <tr>
                        <td>Ficha Asociado</td><td>
                            <select name="ficha_id_FK" required>
                                <c:forEach items="${listaFichas}" var="ficha">
                                    <option value="${ficha.getId()}">
                                        <c:out value="${ficha.getMascotaidFK().getNombre()} ${ficha.getMascotaidFK().getClienterutFK().getNombre()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                    </tr>
                    <tr>
                        <td>Veterinario</td><td>
                            <select name="veterinario_rut_FK" required>
                                <c:forEach items="${listaVeterinarios}" var="veterinario">
                                    <option value="${veterinario.getRut()}">
                                        <c:out value="${veterinario.getNombre()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                    </tr>
                    <tr>
                        <td>Diagnóstico</td><td><textarea name="diagnostico" rows="5" cols="30" required></textarea></td>
                    </tr>
                    <tr>
                        <td>Observación</td><td><textarea name="observacion" rows="5" cols="30" required></textarea></td>
                    </tr>
                    <tr>
                        <td>Tratamiento</td><td><textarea name="tratamiento" rows="5" cols="30" required></textarea></td>
                    </tr>
                    <tr>
                        <td>Fecha Atención</td><td><input type="date" name="fecha_atencion" required></td>
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
            <a href="atenciones/menu.jsp">Volver atrás</a>
        </footer>
        <%
            }
        %>
    </body>
</html>
