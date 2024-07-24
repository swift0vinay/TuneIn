package org.teadev.tunein.utility;

import jakarta.servlet.http.HttpServletRequest;

public class TuneInUtility {
    
    public static String getJwtTokenFromHeaders(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
}
