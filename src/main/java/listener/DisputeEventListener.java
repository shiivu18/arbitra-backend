package listener;

import com.arbitra.backend.event.DisputeStatusChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DisputeEventListener {

    @Async
    @EventListener
    public void handleDisputeStatusChange(DisputeStatusChangeEvent event) {
        // Simulate background processing such as sending an email or pinging an external merchant webhook
        System.out.println("async 🚀 [Event Listener] Dispute ID " + event.getDisputeId() + 
                           " status changed from " + event.getPreviousStatus() + 
                           " to " + event.getNewStatus() + ". Triggering external notification pipeline...");
    }
}