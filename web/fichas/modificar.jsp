<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes - Modificar</title>
    </head>
    <body>
        <header>
            <h1>Modificar Clientes</h1>
        </header>
        <div>
            <%
                if (request.getAttribute("respuesta") == null) {
            %>
            <c:if test="${empty cliente}">
                <form action="<%=request.getContextPath()%>/AdminClientes" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>Rut</td><td><input type="text" name="rut" maxlength="10" required >
                            </td><td><button>Buscar cliente</button></td>
                        </tr>
                        <tr>
                            <td>Nombre</td><td><input type="text" name="nombre" maxlength="50" disabled required></td>
                        </tr>
                        <tr>
                            <td>Dirección</td><td><input type="text" name="direccion" maxlength="50" disabled required></td>
                        </tr>
                        <tr>
                            <td>Fono</td><td><input type="number" name="fono" maxlength="9" disabled required=""></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <c:if test="${not empty cliente}">
                <form action="<%=request.getContextPath()%>/AdminClientes" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>Rut</td><td><input type="text" name="rut" maxlength="10" readonly value="${cliente.getRut()}" ></td>
                        </tr>
                        <tr>
                            <td>Nombre</td><td><input type="text" name="nombre" maxlength="50" required value="${cliente.getNombre()}"></td>
                        </tr>
                        <tr>
                            <td>Dirección</td><td><input type="text" name="direccion" maxlength="50" required value="${cliente.getDireccion()}"></td>
                        </tr>
                        <tr>
                            <td>Fono</td><td><input type="number" name="fono" maxlength="9" required value="${cliente.getFono()}"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Modificar"></td>
                        </tr>
                    </table>
                </form>
            </c:if>
        </div>
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
