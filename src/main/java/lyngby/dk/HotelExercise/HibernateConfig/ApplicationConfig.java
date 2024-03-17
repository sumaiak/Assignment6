package lyngby.dk.HotelExercise.HibernateConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class ApplicationConfig {
    ObjectMapper om = new ObjectMapper();
    private Javalin app;
    private static ApplicationConfig instance;
    private ApplicationConfig(){}
    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }
    public ApplicationConfig initiateServer(){
        app = Javalin.create(config->{
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api";
        });

        return instance;
    }
    public ApplicationConfig startServer(int port){
        app.start(port);
        return instance;
    }
    public ApplicationConfig setRoute(EndpointGroup route){
        app.routes(route);
        return instance;
    }

    public ApplicationConfig setExceptionHandling(){
        app.exception(Exception.class, (e,ctx)->{
            ObjectNode node = om.createObjectNode().put("errrorMessage",e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }
    public void stopServer(){

        app.stop();
    }
}
