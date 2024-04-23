package com.oficial.C1739.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {


    //expiracion del token en 10minutos
    //@Value("${security.jwt.expiration-in-minute}")
    private final Long EXPIRATION_TIME_IN_MINUTES= 10L;


    //clave para firmar el token
    //@Value("${security.jwt.secret}")
    private final String SECRET_KEY= "clave-secretasadasdasdasdasdasdasdasdasd";


    public String generarTokenJWT(UserDetails usuario,Map<String, Object> extraClaims) {

        //Infomacion basica de la estructura de un jwt
        /**
         * 1. Header: Contiene el tipo de token y el algoritmo de encriptación.
         * 2. Payload: Contiene la información que se desea transmitir.
         * 3. Signature: Contiene la firma digital que permite verificar la autenticidad del token.
         */


        /**
         * La expiración del token se establece en minutos y se calcula
         * sumando la fecha actual en milisegundos con el tiempo de expiración
         */

        Date issuedAt = new Date(System.currentTimeMillis());
        Date Expiration = new Date(issuedAt.getTime()+ EXPIRATION_TIME_IN_MINUTES*60*1000);



        /**
         * Claims son los datos que se desean transmitir en el token. Que incluye el cuerpo del token y puede ser el nombre o correo
         * del usuario con su respectivo rol.
         *
         * subject: Es el nombre o propietario del token.
         *
         * signWith: Es el algoritmo de encriptación que se utiliza para firmar el token.
         */


        String jwt= Jwts.builder()
                .header()
                    .type("JWT")
                    .and()



                .subject(usuario.getUsername())
                .issuedAt(issuedAt)
                .expiration(Expiration)
                .claims(extraClaims)


                .signWith(generarKey(), Jwts.SIG.HS256)

                .compact();

        return jwt;

    }

    private SecretKey generarKey() {

        byte[] secreto= SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(secreto);
    }


    //Jwts.parser() esto se encarga de pasar a json, validar la firma, extraer payload y pasarlo a un objeto claims, etc
    //Esto se usar cuando sabemos que nuestro jwt esta firmado
    private Claims extraerClaims(String token) {
        return Jwts.parser().verifyWith(generarKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    //Extraer todos los claims o propiedades del payload para retornar solo el usuario o en nuestro caso, el correo
    public String extraerUsuarioDelToken(String token) {

        String name = extraerClaims(token).getSubject();
        return name;
    }

    public String extractJwtFromRequest(HttpServletRequest request) {

        //1 Obtener el encabezado http llamado authorization
        String authorizationHeader = request.getHeader("Authorization"); //Bearer token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }


        //2 Obtener token JWT desde el encabezado de autorización
        return authorizationHeader.split(" ")[1];
    }

    public Date extraerExpiracion(String jwt) {

        return extraerClaims(jwt).getExpiration();
    }
}
