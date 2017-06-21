<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mascotas - Búsqueda</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <c:if test="${empty mascota}">
            <header>
                <h1>Buscar Mascotas</h1>
            </header>
            <form action="<%=request.getContextPath()%>/AdminMascotas" method="POST">
                <input type="text" name="accion" value="buscar" hidden>
                <table>
                    <tr>
                        <td>Id Mascota</td><td><input type="number" name="id" maxlength="10" required >
                        </td><td><button>Buscar mascota</button></td>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${not empty mascota}">
            <header>
                <h1>Resultado:</h1>
            </header>
            <div class="lista">
                <table>
                    <tr>
                        <td>Id Mascota</td><td>${mascota.getId()}</td>
                    </tr>
                    <tr>
                        <td>Nombre</td><td>${mascota.getNombre()}</td>
                    </tr>
                    <tr>
                        <td>Raza</td><td>${mascota.getRazanombreFK().getNombre()}</td>
                    </tr>
                    <tr>
                        <td>Fecha Nacimiento</td><td>${mascota.getFechaNac()}</td>
                    </tr>
                    <tr>
                        <td>Sexo</td><td>${mascota.getSexo()}</td>
                    </tr>
                    <tr>
                        <td>Dueño</td><td>${mascota.getClienterutFK().getNombre()}</td>
                    </tr>
                </table>
            </div>
            <br>
        </c:if>
        <br>
        <%
        } else {
        %>
        <h2><c:out value="${respuesta}"/></h2>
        <%
            }
        %>
        <footer>
            <a href="<%=request.getContextPath()%>/mascotas/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>