<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="persistencia.Ficha"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mascotas - Listado</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("listaMascotas") == null) {
        %>
        <jsp:forward page="../AdminMascotas?accion=listar" />
        <%
            }
        %>

        <header>
            <h1>Listado de Mascotas</h1>
        </header>
        <div class="lista">
            <table>
                <thead>
                <td>id Mascota</td>
                <td>Cliente</td>
                <td>Raza</td>
                <td>Nombre</td>
                <td>Fecha de Nacimiento</td>
                <td>Sexo</td>
                </thead>
                <c:forEach items="${listaMascotas}" var="mascota">
                    <tr>
                        <td>${mascota.getId()}</td>
                        <td>${mascota.getClienterutFK().getNombre()}</td>
                        <td>${mascota.getRazanombreFK().getNombre()}</td>
                        <td>${mascota.getNombre()}</td>
                        <td>${mascota.getFechaNac()}</td>
                        <td>${mascota.getSexo()}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
    <br>
    <footer>
        <a href="menu.jsp">Volver atrás</a>
    </footer>
</html>