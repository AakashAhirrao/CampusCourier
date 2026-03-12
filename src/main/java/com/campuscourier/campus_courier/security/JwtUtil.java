package com.campuscourier.campus_courier.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Master password used to sign the cards must be at least 256 bits/ 32 characters long!
    // we hide this in application.properties but for now it is here for you.
    private final String SECRET_STRING = "ThisIsASuperSecretKeyForCampusCourierThatIsVeryLong123!";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    private final long EXPIRATION_TIME = 86400000; // 86,400,000 millisecond in one day

    // Method to print the card
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email) // The main payload (who this card belongs to)
                .setIssuedAt(new Date()) // Stamped with today's date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Stamped with expiration date
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Signed with our master password
                .compact(); // Squashes it all into a single text string
    }

    public String parseEmail(String token) {
        return Jwts.parserBuilder() // Jwt Parse Builder
                .setSigningKey(SECRET_KEY) // key used
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
