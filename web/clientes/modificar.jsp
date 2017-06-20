<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="clientes" method="POST">
                <table>
                    <tr>
                        <td>Rut</td><td><input type="text" name="rut" maxlength="10" disabled></td><td><button>Buscar cliente</button></td>
                    </tr>
                    <tr>
                        <td>Nombre</td><td><input type="text" name="nombre" maxlength="50" required=""></td>
                    </tr>
                    <tr>
                        <td>Dirección</td><td><input type="text" name="direccion" maxlength="50" required></td>
                    </tr>
                    <tr>
                        <td>Fono</td><td><input type="number" name="fono" maxlength="9" required=""></td>
                    </tr>
                    <tr>
                        <td><button>Modificar</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
    <br>
    <footer>
            <a href="menu.jsp">Volver atrás</a>
    </footer>
</html>