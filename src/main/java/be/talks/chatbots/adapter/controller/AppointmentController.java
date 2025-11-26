package be.talks.chatbots.adapter.controller;

import be.talks.chatbots.adapter.controller.dto.AppointmentCreationRequestDTO;
import be.talks.chatbots.adapter.repository.AppointmentEntity;
import be.talks.chatbots.adapter.repository.AppointmentRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final ChatClient chatClient;

    public AppointmentController(AppointmentRepository appointmentRepository, @Qualifier("botConfigChatClient") ChatClient chatClient) {
        this.appointmentRepository = appointmentRepository;
        this.chatClient = chatClient;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentCreationRequestDTO appointmentCreationRequestDTO) {
        appointmentRepository.save(
                AppointmentEntity.builder()
                        .appointmentDate(appointmentCreationRequestDTO.getAppointmentDate())
                        .licensePlate(appointmentCreationRequestDTO.getLicensePlate())
                        .build()
        );
        return ResponseEntity.ok("Appointment created successfully");
    }

    @GetMapping("/get-appointments")
    public ResponseEntity<List<AppointmentEntity>> getAllAppointments() {
        List<AppointmentEntity> appointments = appointmentRepository.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointment-bot")
    public ResponseEntity<String> appointmentBot(@RequestHeader("username") String username,
                                                 @RequestParam("message") String message) {

        String answer = chatClient
                .prompt()
                .system("""
                        You are a helpful appointment booking assistant for a car service center.
                        
                        TOOL USAGE RULES:
                        - Do NOT use any tools for simple greetings like "hello", "hi", "hey"
                        - Do NOT use tools for general conversation or questions
                        - ONLY use the createAppointment tool when you have BOTH license plate AND appointment date
                        
                        CONVERSATION FLOW FOR APPOINTMENTS:
                        1. When user wants to book an appointment, ask: "May I have your license plate number?"
                        2. After receiving license plate, ask: "What date and time would you prefer? (Please provide in format: YYYY-MM-DD HH:mm)"
                        3. After receiving BOTH pieces of information, confirm and create the appointment
                        
                        For greetings and general questions, respond conversationally WITHOUT using any tools.
                        Ask ONE question at a time and wait for the user's answer.
                        """)
                .user(message)
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
