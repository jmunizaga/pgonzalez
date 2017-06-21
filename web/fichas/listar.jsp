<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="persistencia.Ficha"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fichas - Listado</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("listaFichas") == null) {
        %>
        <jsp:forward page="../AdminFichas?accion=listar" />
        <%
            }
        %>

        <header>
            <h1>Listado de Fichas</h1>
        </header>
        <div class="lista">
            <table>
                <thead>
                <td>id Ficha</td>
                <td>Fecha Creación</td>
                <td>Peso</td>
                <td>Tamaño</td>
                <td>Id Mascota</td>
                <td>Nombre</td>
                </thead>
                <c:forEach items="${listaFichas}" var="ficha">
                    <tr>
                        <td>${ficha.getId()}</td>
                        <td>${ficha.getFechaCreacion()}</td>
                        <td>${ficha.getPeso()}</td>
                        <td>${ficha.getTamaño()}</td>
                        <td>${ficha.getMascotaidFK().getId()}</td>
                        <td>${ficha.getMascotaidFK().getNombre()}</td>
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