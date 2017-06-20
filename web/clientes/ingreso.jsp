<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes - Ingreso</title>
    </head>
    <body>
        <header>
            <h1>Ingreso de Clientes</h1>
        </header>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <div>
            <form action="../AdminClientes" method="POST">
                <input type="text" name="accion" value="ingreso" hidden>
                <table>
                    <tr>
                        <td>Rut</td><td><input type="text" name="rut" maxlength="10" required></td>
                    </tr>
                    <tr>
                        <td>Nombre</td><td><input type="text" name="nombre" maxlength="50" required></td>
                    </tr>
                    <tr>
                        <td>Dirección</td><td><input type="text" name="direccion" maxlength="50" required></td>
                    </tr>
                    <tr>
                        <td>Fono</td><td><input type="number" name="fono" maxlength="9" required></td>
                    </tr>
                    <tr>
                        <td><button>Ingresar</button></td>
                    </tr>
                </table>
            </form>
        </div>
        <%
        } else {
        %>
        <h2><c:out value="${respuesta}"/></h2>
        <%
            }
        %>

    </body>
    <br>
    <footer>
        <a href="./clientes/menu.jsp">Volver atrás</a>
    </footer>
</html>
