<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes - Búsqueda</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <c:if test="${empty cliente}">
            <header>
                <h1>Buscar Clientes</h1>
            </header>
            <form action="<%=request.getContextPath()%>/AdminClientes" method="POST">
                <input type="text" name="accion" value="buscar" hidden>
                <table>
                    <tr>
                        <td>Rut Cliente</td><td><input type="text" name="rut" maxlength="10" required >
                        </td><td><button>Buscar cliente</button></td>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${not empty cliente}">
            <header>
                <h1>Resultado:</h1>
            </header>
            <div class="lista">
                <table>
                    <tr>
                        <td>Rut Cliente</td><td>${cliente.getRut()}</td>
                    </tr>
                    <tr>
                        <td>Nombre</td><td>${cliente.getNombre()}</td>
                    </tr>
                    <tr>
                        <td>Dirección</td><td>${cliente.getDireccion()}</td>
                    </tr>
                    <tr>
                        <td>Fono</td><td>${cliente.getFono()}</td>
                    </tr>
                </table>
            </div>
            <br>
            <h2> Lista de Mascotas:</h2>
            <div class="lista">
                <table>
                    <thead>
                        <tr>
                            <td>Id Mascota</td>       
                            <td>Nombre</td>
                            <td>Raza</td>
                            <td>Sexo</td>
                            <td>Fecha Nacimiento</td>
                        </tr>
                    </thead>
                    <c:forEach items="${cliente.getMascotaCollection()}" var="mascota">
                        <tr>
                            <td>${mascota.getId()}</td>
                            <td>${mascota.getNombre()}</td>
                            <td>${mascota.getRazanombreFK().getNombre()}</td>
                            <td>${mascota.getSexo()}</td>
                            <td>${mascota.getFechaNac()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
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
            <a href="<%=request.getContextPath()%>/clientes/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>