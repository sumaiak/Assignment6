package lyngby.dk.Secuirty.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;
import lyngby.dk.HotelExercise.DTOS.UserDTO;
import lyngby.dk.Secuirty.Models.User;
import lyngby.dk.Secuirty.Dao.UserDAO;

public class UserController {


        ObjectMapper objectMapper;

        public Handler login(UserDAO dao) {
            return ctx -> {
                ObjectNode returnObject; // For sending json message back
                try {
                    UserDTO dto = ctx.bodyAsClass(UserDTO.class);
                    System.out.println("USER in Login" + dto);

                    User verifyUser = dao.getVerifiedUser(dto.getUsername(), dto.getPassword());

                    ctx.status(200).json(dto.getUsername());
                } catch (EntityNotFoundException e) {
                    ctx.status(404).result(e.getMessage());
                } catch (Exception e) {
                    ctx.status(500).result("Internal server error");
                }
            };
        }

        public Handler register(UserDAO dao) {
            return (ctx) -> {
                ObjectNode returnObject = objectMapper.createObjectNode();
                try {
                    UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                    User created = dao.createUser(userInput.getUsername(), userInput.getPassword());

                    ctx.status(HttpStatus.CREATED).json(userInput.getUsername());
                } catch (Exception e) {
                    ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                    ctx.json(returnObject.put("msg", "User already exists"));
                }
            };
        }


    }

