package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import util.ConexionBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


//sirve para restrigir ciertas calses a los usuarios
//Implenmetamos una anotacion esta anotacion
//me sirve para poder utilizar la conexion en cualquier parte
//de mi aplicacion
@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*
     * Una clase filter en java es un objeto que realiza la tarea de filtrado
     * e las solicitudes cliente servidor
     * respuesta a un recurso: los filtros se puedes ejecutar
     * en servidores compatibles con Jakarta EE
     * los filtros interceptan solicitudes y respuestas de manera
     * dinamica para transformar. El filtrado se realiza mediante el
     * doFilter*/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        /*
         * request: peticion que hace el cliente
         * response: respuesta del servidor
         * filterChain: es una clase de filtro que representa el flujo
         * del procesamiento, este metodo llama al metodo chain.doFilter
         * dentro de un filtro pasa las solicitud, el siguiente paso la clase
         * filtra o devuelve el recurso destino que puede ser un servlet
         * o jsp */

        //Ontenemos la conexion
        try(Connection conn = ConexionBDD.getConnection()){
            //Verificamos que la conexion realizada  o se cambie a autocommit
            //(configuracion automatica a la base de satos y cada instruccion
            // SQL)
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try{
                //Agregamos la conexion como un atributo e la solicitud
                //esto nos permite que otros componentes como servlets o DAOS
                //puedan acceder a la conexion
                request.setAttribute("conn", conn);
                //pasa la solicitd y la respuesta al siguiente filtro
                //destino
                filterChain.doFilter(request, response);
                conn.commit();

                // *********** CORRECCIÓN DEL ERROR ***********
                // NO se puede usar (SQLException | Exception)
                // porque SQLException es hija de Exception

            } catch(SQLException e) {
                conn.rollback();
                ((HttpServletResponse) response)
                        .sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();

            } catch(Exception e) {
                conn.rollback();
                ((HttpServletResponse) response)
                        .sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
