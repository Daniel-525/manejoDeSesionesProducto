package listeners;
/*
 * Autor: Cristian Arias
 * Fecha y version: 10/11/2025 | Version: 1.0
 * Descripcion: Listener que inicializa recursos globales al iniciar la aplicacion web,
 * creando un mapa concurrente para registrar la cantidad de inicios de sesion por usuario.
 */

// Importa las clases necesarias para manejar eventos del contexto del servlet.
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Anotacion que indica que esta clase actuara como un listener dentro de la aplicacion web.
@WebListener
public class AppInitListener implements ServletContextListener {

    // Metodo que se ejecuta automaticamente cuando el contexto de la aplicacion se inicializa.
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // Crea un mapa concurrente que almacenara los conteos de inicio de sesion por usuario.
        Map<String, Integer> loginCounts = new ConcurrentHashMap<>();

        // Guarda el mapa como atributo global en el contexto de la aplicacion,
        // permitiendo que otros servlets o componentes puedan acceder a el.
        sce.getServletContext().setAttribute("loginCounts", loginCounts);
    }
}
