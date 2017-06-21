<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fichas - Búsqueda</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    </head>
    <body>
        <%
            if (request.getAttribute("respuesta") == null) {
        %>
        <c:if test="${empty ficha}">
            <header>
                <h1>Buscar Fichas</h1>
            </header>
            <form action="<%=request.getContextPath()%>/AdminFichas" method="POST">
                <input type="text" name="accion" value="buscar" hidden>
                <table>
                    <tr>
                        <td>id Ficha</td><td><input type="number" name="id" maxlength="10" required >
                        </td><td><button>Buscar ficha</button></td>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${not empty ficha}">
            <header>
                <h1>Resultado:</h1>
            </header>
            <div class="lista">
                <table>
                    <tr>
                        <td>id Ficha</td><td>${ficha.getId()}</td>
                    </tr>
                    <tr>
                        <td>Mascota Id</td><td>${ficha.getMascotaidFK().getId()}</td>
                    </tr>
                    <tr>
                        <td>Mascota Nombre</td><td>${ficha.getMascotaidFK().getNombre()}</td>
                    </tr>
                    <tr>
                        <td>Fecha Creación</td><td>${ficha.getFechaCreacion()}</td>
                    </tr>
                    <tr>
                        <td>Peso</td><td>${ficha.getPeso()}</td>
                    </tr>
                    <tr>
                        <td>Tamaño</td><td>${ficha.getTamaño()}</td>
                    </tr>
                </table>
            </div>
            <br>
            <h2> Lista de Atenciones:</h2>
            <div class="lista">
                <table>
                    <thead>
                        <tr>
                            <td>Id Atención</td>       
                            <td>Fecha Atención</td>
                            <td>Diagnóstico</td>
                            <td>Tratamiento</td>
                            <td>Observación</td>
                            <td>Veterinario</td>
                            <td>Id Mascota</td>
                            <td>Nombre</td>
                            <td>Rut Cliente</td>
                            <td>Nombre Cliente</td>

                        </tr>
                    </thead>
                    <c:forEach items="${ficha.getAtencionCollection()}" var="atencion">
                        <tr>
                            <td>${atencion.getId()}</td>
                            <td>${atencion.getFechaAtencion()}</td>
                            <td>${atencion.getDiagnostico()}</td>
                            <td>${atencion.getTratamiento()}</td>
                            <td>${atencion.getObservacion()}</td>
                            <td>${atencion.getVeterinariorutFK().getNombre()}</td>
                            <td>${ficha.getMascotaidFK().getId()}</td>
                            <td>${ficha.getMascotaidFK().getNombre()}</td>
                            <td>${ficha.getMascotaidFK().getClienterutFK().getRut()}</td>
                            <td>${ficha.getMascotaidFK().getClienterutFK().getNombre()}</td>
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
            <a href="<%=request.getContextPath()%>/fichas/menu.jsp">Volver atrás</a>
        </footer>
    </body>
</html>