package lyngby.dk.Secuirty.Controller;

import io.javalin.http.Handler;

public class DemoController {
    public Handler sayHello()
    {
        return ctx -> ctx.json("{\"msg\":\"Hello from server\"}");
    }
}
