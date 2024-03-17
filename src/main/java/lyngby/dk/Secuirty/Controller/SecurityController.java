package lyngby.dk.Secuirty.Controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import lyngby.dk.HotelExercise.DTOS.UserDTO;
import lyngby.dk.HotelExercise.HibernateConfig.HibernateConfig;
import lyngby.dk.Secuirty.Dao.UserDAO;
import lyngby.dk.Secuirty.Models.User;

import java.sql.Date;

public class SecurityController {

    private static UserDAO userDAO;
    private static String SECRET_KEY = "sDJfl9pP6zRZks2n2KtFzUPu0x4RFwvbPXXoZZv9JflXic7Q4rK4cJzBtEoAwXGP";

    public SecurityController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Handler authenticate() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode returnObject = objectMapper.createObjectNode();
        return (ctx) -> {
            if (ctx.method().toString().equals("OPTIONS")) {
                ctx.status(200);
                return;
            }
            String header = ctx.header("Authorization");
            if (header == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header missing"));
                return;
            }
            String token = header.split(" ")[1];
            if (token == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header malformed"));
                return;
            }
            UserDTO verifiedTokenUser = verifyToken(token);
            if (verifiedTokenUser == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Invalid User or Token"));
            }
            System.out.println(" AUTHENTICATING: " + verifiedTokenUser);
            ctx.attribute("user", verifiedTokenUser);
        };
    }

    private UserDTO verifyToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
            User user;
            try (EntityManager em = emf.createEntityManager()) {
                user = em.find(User.class, username);
            }

            return user != null ? new UserDTO(user) : null;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }





    public String createToken(UserDTO user, String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) throws
            JOSEException {
        try {
            if (user == null) {
                throw new IllegalArgumentException("UserDTO is null");
            }

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(ISSUER)
                    .claim("username", user.getUsername())
                    .claim("roles", user.getRoles().stream().reduce((s1, s2) -> s1 + "," + s2).get())
                    .expirationTime(new Date(System.currentTimeMillis() + Long.parseLong(TOKEN_EXPIRE_TIME)))
                    .build();

            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(SECRET_KEY);

            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

            // Create JWS object
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);

            // Sign the JWS object
            jwsObject.sign(signer);

            return jwsObject.serialize();

        } catch (Exception e) {
            e.printStackTrace();
            throw new JOSEException("Could not create token", e);
        }
    }


}