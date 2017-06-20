<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Razas - Eliminar</title>
    </head>
    <body>
        <header>
            <h1>Eliminar Razas</h1>
        </header>
        <div>
            <%
                if (request.getAttribute("respuesta") == null) {
            %>
            <c:if test="${empty raza}">
                <form action="<%=request.getContextPath()%>/AdminRazas" method="POST">
                    <input type="text" name="accion" value="eliminar" hidden>
                    <table>
                        <tr>
                            <td>Nombre</td><td><input type="text" name="nombre" maxlength="10" required >
                            </td><td><button>Buscar raza</button></td>
                        </tr>
                        <tr>
                            <td>Descripción</td><td><input type="text" name="descripcion" maxlength="50" disabled required></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <c:if test="${not empty raza}">
                <form action="<%=request.getContextPath()%>/AdminRazas" method="POST">
                    <input type="text" name="accion" value="eliminar" hidden>
                    <table>
                        <tr>
                            <td>Nombre</td><td><input type="text" name="nombre" maxlength="10" readonly value="${raza.getRut()}" ></td>
                        </tr>
                        <tr>
                            <td>Descripción</td><td><input type="text" name="descripcion" maxlength="50" readonly value="${raza.getNombre()}"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Eliminar"></td>
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
            <a href="<%=request.getContextPath()%>/razas/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>
