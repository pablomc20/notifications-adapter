package com.compadres.na.service.alexa;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    // Simula buscar notificaciones no leídas de un usuario
    public List<String> getUnreadNotifications(String userId) {
        // Por ahora, ignoramos el userId y devolvemos la respuesta en duro
        return List.of(
            "Tu mueble, Silla Hermética, ha sido ensamblada."
        );
    }
}