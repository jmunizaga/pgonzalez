<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Razas - Ingreso</title>
    </head>
    <body>
        <header>
            <h1>Ingreso de Razas</h1>
        </header>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <div>
            <form action="../AdminRazas" method="POST">
                <input type="text" name="accion" value="ingreso" hidden>
                <table>
                    <tr>
                        <td>Nombre</td><td><input type="text" name="nombre" maxlength="10" required></td>
                    </tr>
                    <tr>
                        <td>Descripción</td><td><input type="text" name="descripcion" maxlength="50" required></td>
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
            <a href="razas/menu.jsp">Volver atrás</a>
        </footer>
        <%
            }
        %>
    </body>
</html>
