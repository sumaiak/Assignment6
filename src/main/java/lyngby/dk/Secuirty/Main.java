package lyngby.dk.Secuirty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import lyngby.dk.Secuirty.Controller.DemoController;
import lyngby.dk.HotelExercise.HibernateConfig.ApplicationConfig;
import lyngby.dk.Secuirty.Dao.UserDAO;
import lyngby.dk.Secuirty.Models.User;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.put;

public class Main {
    static DemoController demoController = new DemoController();


    public static void main(String[] args) {
        Main.startServer(7070);


    }


    public static void startServer(int port) {
        ObjectMapper om = new ObjectMapper();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig
                .initiateServer()
                .startServer(port)
                .setExceptionHandling()
                .setRoute(() -> {
                    path("demo", () -> {
                        get("test", demoController.sayHello());


                        get("error", ctx -> {
                            throw new Exception("Dette er en test");
                        });
                    });
                });
    }

    public static void closeServer() {
        ApplicationConfig.getInstance().stopServer();
    }

//    public static EndpointGroup getPersonRoutes() {
//        try {
//            return () -> {
//                path("api/user", () -> {
//
//                    post("/", ctx -> {
//                        User incoming = ctx.bodyAsClass(User.class);
//                        UserDAO userDAO = new UserDAO();
//                        incoming = userDAO.createUser("sumaiak", "1234");
//                        ctx.json(incoming);
//                        if (userDAO.getVerifiedUser("sumaiak", "1234") == incoming) {
//                            //return jwt
//                        }
//
//                    });
//                });
//            };
//        }
//    }
//}
}