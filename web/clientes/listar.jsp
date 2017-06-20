<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="persistencia.Cliente"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes - Listado</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("listaClientes") == null) {
        %>
        <jsp:forward page="../AdminClientes?accion=listar" />
        <%
            }
        %>

        <header>
            <h1>Listado de Clientes</h1>
        </header>
        <div class="lista">
            <table>
                <thead>
                <td>Rut</td>
                <td>Nombre</td>
                <td>Dirección</td>
                <td>Fono</td>
                </thead>
                <c:forEach items="${listaClientes}" var="cliente">
                    <tr>
                        <td>
                            <c:out value="${cliente.getRut()}"/>
                        </td>
                        <td>
                            <c:out value="${cliente.getNombre()}"/>
                        </td>
                        <td>
                            <c:out value="${cliente.getDireccion()}"/>
                        </td> 
                        <td>
                            <c:out value="${cliente.getFono()}"/>
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