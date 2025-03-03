package com.plaza.usuarios.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    // Inyección de dependencias: JwtUtils para validar tokens y UserDetailsService para cargar usuarios
    public JwtAuthFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Paso 1: Obtener el encabezado "Authorization" de la solicitud
        final String authHeader = request.getHeader("Authorization");

        // Si no hay token o no comienza con "Bearer ", continuar sin autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Paso 2: Extraer el token JWT (eliminar "Bearer ")
        final String jwt = authHeader.substring(7);

        try {
            // Paso 3: Validar el token (firma y expiración)
            if (!jwtUtils.validateToken(jwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
                return;
            }

            // Paso 4: Extraer el email del token
            final String userEmail = jwtUtils.extractEmail(jwt);

            // Paso 5: Si el usuario ya está autenticado, omitir
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Paso 6: Cargar el usuario desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                // Paso 7: Crear objeto de autenticación con roles
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciales (no necesarias aquí)
                        userDetails.getAuthorities() // Roles del usuario
                );

                // Paso 8: Agregar detalles de la solicitud (IP, navegador, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Paso 9: Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            // Continuar con la cadena de filtros
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // Manejar errores (token expirado, usuario no encontrado, etc.)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: " + e.getMessage());
        }
    }
}
