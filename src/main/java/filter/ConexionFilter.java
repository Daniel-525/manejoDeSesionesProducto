package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;
import util.ConexionBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//Implementamos una anotacion. Esta anotacion
//me sirve para poder utilizar la conexion en cualquier parte
// de mi aplicacion

@WebFilter("/*")
public class ConexionFilter implements Filter {
    /*
    * Una clase en filter en java es un objeto que realiza tareas
    * de filtrado en las soliciyudes cliente servidor
    * respuesta a un recurso: los filtros se pueden ejecutar
    * en servidores compatibles con jakarta ee
    * los filtros interceptan solicitudes y respuestas de manera
    * dinamica para transformar o utilizar la informacion
    * que contienen . El filtrado se realiza mediante el
    * metodo doFilter
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        /*
        * request: peticion que hace el cliente
        * response: respuesta al servidor
        * filterchain: es una clase de filtro que representa el
        * flujo de procesamiento, este metodo llama al metodo chain.dofilter(request, response)
        * dentro de un filtro para la solicitud, el siguiente paso la clase filtra o te devuelve el recurso
        * destino que puede ser un servlet o un jsp
         */

        //obtenemos la conexion

        try (Connection conn = ConexionBDD.getConnection()) {
            // verificamos que la conexion realizada o se cambien a autocommit
            //(configuracion automatica a la base de datos y cada instruccion
            //sql)
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try {
                //agregamos la conexion como un atributo en la solicitud
                //esto nos permite que otros componentes como servlets o daos
                //puedan acceder a la conexion
                request.setAttribute("conn", conn);
                //pasa la solicitud y la respuesta al siguiente filtro o al recurso
                //destino
                filterChain.doFilter(request, response);
                conn.commit();
            }catch (SQLException e){
                conn.rollback();
                //enviamos un codigo de error HTTP 500 al cliente
                //indicando un problema interno del servidor
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
