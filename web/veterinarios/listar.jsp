<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Veterinarios - Listado</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("listaVeterinarios") == null) {
        %>
        <jsp:forward page="../AdminVeterinarios?accion=listar" />
        <%
            }
        %>

        <header>
            <h1>Listado de Veterinarios</h1>
        </header>
        <div class="lista">
            <table>
                <thead>
                <td>Rut</td>
                <td>Nombre</td>
                <td>Fono</td>
                </thead>
                <c:forEach items="${listaVeterinarios}" var="veterinario">
                    <tr>
                        <td>
                            <c:out value="${veterinario.getRut()}"/>
                        </td>
                        <td>
                            <c:out value="${veterinario.getNombre()}"/>
                        </td>
                        <td>
                            <c:out value="${veterinario.getFono()}"/>
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
</html>