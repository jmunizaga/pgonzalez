<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="persistencia.Raza"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Razas - Listado</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("listaRazas") == null) {
        %>
        <jsp:forward page="../AdminRazas?accion=listar" />
        <%
            }
        %>

        <header>
            <h1>Listado de Razas</h1>
        </header>
        <div class="lista">
            <table>
                <thead>
                <td>Nombre</td>
                <td>Descripción</td>
                </thead>
                <c:forEach items="${listaRazas}" var="raza">
                    <tr>
                        <td>
                            <c:out value="${raza.getNombre()}"/>
                        </td>
                        <td>
                            <c:out value="${raza.getDescripcion()}"/>
                        </td> 
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
    <br>
    <footer>
        <a href="menu.jsp">Volver atrás</a>
    </footer>