<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Veterinarios - Búsqueda</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <c:if test="${empty veterinario}">
            <header>
                <h1>Buscar Veterinarios</h1>
            </header>
            <form action="<%=request.getContextPath()%>/AdminVeterinarios" method="POST">
                <input type="text" name="accion" value="buscar" hidden>
                <table>
                    <tr>
                        <td>Rut Veterinario</td><td><input type="text" name="rut" maxlength="10" required >
                        </td><td><button>Buscar veterinario</button></td>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${not empty veterinario}">
            <header>
                <h1>Resultado:</h1>
            </header>
            <div class="lista">
                <table>
                    <tr>
                        <td>Rut Veterinario</td><td>${veterinario.getRut()}</td>
                    </tr>
                    <tr>
                        <td>Nombre</td><td>${veterinario.getNombre()}</td>
                    </tr>
                    <tr>
                        <td>Fono</td><td>${veterinario.getFono()}</td>
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
            <a href="<%=request.getContextPath()%>/veterinarios/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>