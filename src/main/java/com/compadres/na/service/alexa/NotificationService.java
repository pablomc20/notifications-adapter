package com.compadres.na.service.alexa;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    /**
     * Returns unread notifications for the Alexa user. The {@code userId} is the Amazon account identifier
     * from the skill envelope; a future backend can filter on it.
     */
    public List<String> getUnreadNotifications(String userId) {
        // Stub: ignores userId until a real notification API exists
        return List.of(
            "Tu mueble, Silla Hermética, ha sido ensamblada."
        );
    }
}