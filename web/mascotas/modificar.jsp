<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mascotas - Modificar</title>
    </head>
    <body>
        <header>
            <h1>Modificar Mascotas</h1>
        </header>
        <div>
            <%
                if (request.getAttribute("respuesta") == null) {
            %>
            <c:if test="${empty mascota}">
                <form action="<%=request.getContextPath()%>/AdminMascotas" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>Id Mascota</td><td><input type="number" name="id" maxlength="10" required >
                            </td><td><button>Buscar mascota</button></td>
                        </tr>
                        <tr>
                            <td>Dueño</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Raza</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Nombre</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Fecha Nacimiento</td><td><input disabled></td>
                        </tr>
                        <tr>
                            <td>Sexo</td><td><input disabled></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <c:if test="${not empty mascota}">
                <form action="<%=request.getContextPath()%>/AdminMascotas" method="POST">
                    <input type="text" name="accion" value="modificar" hidden>
                    <table>
                        <tr>
                            <td>Id Mascota</td><td><input type="number" name="id" maxlength="10" readonly value="${mascota.getId()}" ></td>
                        </tr>
                        <tr>
                            <td>Dueño</td><td><input type="text" name="cliente_rut_FK" maxlength="50" readonly value="${mascota.getClienterutFK().getRut()}">&nbsp;("${mascota.getClienterutFK().getNombre()}")</td>
                        </tr>
                        <tr>
                            <td>Raza</td><td><input type="text" name="raza_nombre_FK" maxlength="50" readonly value="${mascota.getRazanombreFK().getNombre()}"></td>
                        </tr>
                        <tr>
                            <td>Nombre</td><td><input type="text" name="nombre" maxlength="50" required value="${mascota.getNombre()}"></td>
                        </tr>
                        <tr>
                            <td>Fecha Nacimiento</td><td><input type="date" name="fecha_nac" required value="${mascota.getFechaNac()}"></td>
                        </tr>
                        <tr>
                            <td>Sexo</td>
                            <td>
                                <select name="sexo" required>
                                    <c:if test="${mascota.getSexo()=='M'}">
                                        <option value="M" selected>Masculino</option>
                                        <option value="F">Femenino</option>
                                    </c:if>
                                    <c:if test="${mascota.getSexo()=='F'}">
                                        <option value="M">Masculino</option>
                                        <option value="F" selected>Femenino</option>
                                    </c:if>

                                </select>
                            </td>
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
            <a href="<%=request.getContextPath()%>/mascotas/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>
