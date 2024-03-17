package lyngby.dk.Secuirty.token;

import com.nimbusds.jose.*;

import com.nimbusds.jwt.JWTClaimsSet;
import lyngby.dk.HotelExercise.DTOS.UserDTO;


import java.util.Date;

public class VerifyToken {


        private final String ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY;

        public VerifyToken(String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) {
            this.ISSUER = ISSUER;
            this.TOKEN_EXPIRE_TIME = TOKEN_EXPIRE_TIME;
            this.SECRET_KEY = SECRET_KEY;
        }



        private JWTClaimsSet createClaims(String username, String rolesAsString, Date date) {
            return new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(ISSUER)
                    .claim("username", username)
                    .claim("roles", rolesAsString)
                    .expirationTime(new Date(date.getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                    .build();
        }

        private JWSObject createHeaderAndPayload(JWTClaimsSet claimsSet) {
            return new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
        }


        public UserDTO getJWTClaimsSet(JWTClaimsSet claimsSet)  {

            if (new Date().after(claimsSet.getExpirationTime()))
                throw new RuntimeException(

                );

            String username = claimsSet.getClaim("username").toString();
            String roles = claimsSet.getClaim("roles").toString();
            String[] rolesArray = roles.split(",");

            return new UserDTO(username, rolesArray);
        }

    }
