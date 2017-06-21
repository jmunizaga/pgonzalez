<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fichas - Modificar</title>
    </head>
    <body>
        <header>
            <h1>Modificar Fichas</h1>
        </header>
        <div>
            <%
                if (request.getAttribute("respuesta") == null) {
            %>
            <c:if test="${empty ficha}">
                <form action="<%=request.getContextPath()%>/AdminFichas" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>id Ficha</td><td><input type="number" name="id" maxlength="10" required >
                            </td><td><button>Buscar ficha</button></td>
                        </tr>
                        <tr>
                            <td>Mascota Id:</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Fecha Creación</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Peso</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Tamaño</td><td><input disabled></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <c:if test="${not empty ficha}">
                <form action="<%=request.getContextPath()%>/AdminFichas" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>id Ficha</td><td><input type="number" name="id" maxlength="10" readonly value="${ficha.getId()}" ></td>
                        </tr>
                        <tr>
                            <td>Mascota Id</td><td><input type="text" name="mascota_id_FK" maxlength="50" readonly value="${ficha.getMascotaidFK().getId()}">&nbsp;("${ficha.getMascotaidFK().getNombre()}")</td>
                        </tr>
                        <tr>
                            <td>Fecha Creación</td><td><input type="date" name="fecha_creacion" required value="${ficha.getFechaCreacion()}"></td>
                        </tr>
                        <tr>
                            <td>Peso</td><td><input type="text" name="peso" maxlength="6" required value="${ficha.getPeso()}"></td>
                        </tr>
                        <tr>
                            <td>Tamaño</td><td><input type="number" name="tamano" maxlength="6" required value="${ficha.getTamaño()}"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Modificar"></td>
                        </tr>
                    </table>
                </form>
            </c:if>
        </div>
        <br>
        <%
        } else {
        %>
        <h2><c:out value="${respuesta}"/></h2>
        <%
            }
        %>
        <footer>
            <a href="<%=request.getContextPath()%>/fichas/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>
