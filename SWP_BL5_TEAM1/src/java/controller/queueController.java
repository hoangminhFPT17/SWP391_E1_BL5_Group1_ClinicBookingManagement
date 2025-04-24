//package com.clinicmanagement.controller;
//
//import com.clinicmanagement.model.*;
//import com.clinicmanagement.service.QueueService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//// DTO for Queue Response
//class QueueResponse {
//    private Integer queueId;
//    private String patientName;
//    private String status;
//    private Integer priority;
//    private Integer position;
//    private Integer estimatedWaitTime;
//    private String serviceType;
//    private String checkInTime;
//
//    public QueueResponse(Queue queue, String patientName) {
//        this.queueId = queue.getQueueId();
//        this.patientName = patientName;
//        this.status = queue.getStatus().name();
//        this.priority = queue.getPriority();
//        this.position = queue.getPosition();
//        this.estimatedWaitTime = queue.getEstimatedWaitTime();
//        this.serviceType = queue.getServiceType();
//        this.checkInTime = queue.getCheckInTime().toString();
//    }
//
//    // Getters and Setters
//    public Integer getQueueId() { return queueId; }
//    public void setQueueId(Integer queueId) { this.queueId = queueId; }
//    public String getPatientName() { return patientName; }
//    public void setPatientName(String patientName) { this.patientName = patientName; }
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//    public Integer getPriority() { return priority; }
//    public void setPriority(Integer priority) { this.priority = priority; }
//    public Integer getPosition() { return position; }
//    public void setPosition(Integer position) { this.position = position; }
//    public Integer getEstimatedWaitTime() { return estimatedWaitTime; }
//    public void setEstimatedWaitTime(Integer estimatedWaitTime) { this.estimatedWaitTime = estimatedWaitTime; }
//    public String getServiceType() { return serviceType; }
//    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
//    public String getCheckInTime() { return checkInTime; }
//    public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }
//}
//
//// Controller
//@RestController
//@RequestMapping("/api/queue")
//public class QueueController {
//
//    @Autowired
//    private QueueService queueService;
//
//    // Add a patient to the queue (appointment or walk-in)
//    @PostMapping("/add")
//    public ResponseEntity<?> addToQueue(@RequestBody QueueRequest request) {
//        try {
//            // Validate input
//            if (request.getPatientId() == null || request.getServiceType() == null) {
//                return ResponseEntity.badRequest().body("patientId and serviceType are required");
//            }
//
//            // Create queue entry
//            Queue queue = new Queue();
//            Patient patient = queueService.getPatientById(request.getPatientId());
//            queue.setPatient(patient);
//            if (request.getDoctorId() != null) {
//                queue.setDoctor(queueService.getDoctorById(request.getDoctorId()));
//            }
//            if (request.getAppointmentId() != null) {
//                Appointment appointment = queueService.getAppointmentById(request.getAppointmentId());
//                if (!appointment.getPatient().getPatientId().equals(request.getPatientId()) ||
//                    appointment.getStatus() != Appointment.AppointmentStatus.Scheduled) {
//                    return ResponseEntity.badRequest().body("Invalid or inactive appointment");
//                }
//                queue.setAppointment(appointment);
//            }
//            queue.setServiceType(request.getServiceType());
//            queue.setPriority(request.getPriority() != null ? request.getPriority() : 0);
//            queue.setStatus(Queue.QueueStatus.Waiting);
//            queue.setCheckInTime(LocalDateTime.now());
//
//            // Set initial position and wait time
//            Integer maxPosition = queueService.getMaxPosition(request.getDoctorId());
//            queue.setPosition(maxPosition != null ? maxPosition + 1 : 1);
//            queue.setEstimatedWaitTime(queueService.estimateWaitTime(request.getDoctorId(), request.getServiceType()));
//
//            // Save queue entry
//            queue = queueService.saveQueueEntry(queue);
//
//            // Log notification
//            User user = patient.getUser();
//            queueService.logNotification(user.getUserId(), Notification.NotificationType.InApp,
//                String.format("You have been added to the queue for %s. Estimated wait: %d minutes.",
//                    queue.getServiceType(), queue.getEstimatedWaitTime()));
//
//            // Update queue positions
//            Integer doctorId = queue.getDoctor() != null ? queue.getDoctor().getDoctorId() : null;
//            queueService.updateQueuePositions(doctorId);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(new QueueResponse(queue,
//                user.getFirstName() + " " + user.getLastName()));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Update queue status
//    @PutMapping("/{queueId}/status")
//    public ResponseEntity<?> updateQueueStatus(@PathVariable Integer queueId, @RequestBody StatusUpdateRequest request) {
//        try {
//            Queue queue = queueService.getQueueById(queueId);
//            Queue.QueueStatus newStatus = Queue.QueueStatus.valueOf(request.getStatus());
//            Queue.QueueStatus oldStatus = queue.getStatus();
//
//            queue.setStatus(newStatus);
//            queue = queueService.saveQueueEntry(queue);
//
//            // Update queue positions if moving out of Waiting
//            Integer doctorId = queue.getDoctor() != null ? queue.getDoctor().getDoctorId() : null;
//            if (oldStatus == Queue.QueueStatus.Waiting && newStatus != Queue.QueueStatus.Waiting) {
//                queueService.updateQueuePositions(doctorId);
//            }
//
//            // Log notification
//            Patient patient = queue.getPatient();
//            User user = patient.getUser();
//            queueService.logNotification(user.getUserId(), Notification.NotificationType.InApp,
//                String.format("Your queue status updated to %s.", newStatus));
//
//            return ResponseEntity.ok(new QueueResponse(queue, user.getFirstName() + " " + user.getLastName()));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Get queue for a doctor
//    @GetMapping("/doctor/{doctorId}")
//    public ResponseEntity<List<QueueResponse>> getQueue(@PathVariable Integer doctorId) {
//        List<Queue> queueEntries = queueService.getQueueByDoctorIdAndStatus(doctorId, Queue.QueueStatus.Waiting);
//        List<QueueResponse> response = queueEntries.stream()
//            .map(queue -> {
//                Patient patient = queue.getPatient();
//                User user = patient.getUser();
//                return new QueueResponse(queue, user.getFirstName() + " " + user.getLastName());
//            })
//            .collect(Collectors.toList());
//        return ResponseEntity.ok(response);
//    }
//
//    // Reorder queue by changing priority
//    @PutMapping("/{queueId}/reorder")
//    public ResponseEntity<?> reorderQueue(@PathVariable Integer queueId, @RequestBody PriorityUpdateRequest request) {
//        try {
//            Queue queue = queueService.getQueueById(queueId);
//            queue.setPriority(request.getPriority());
//            queue = queueService.saveQueueEntry(queue);
//
//            // Update queue positions
//            Integer doctorId = queue.getDoctor() != null ? queue.getDoctor().getDoctorId() : null;
//            queueService.updateQueuePositions(doctorId);
//
//            // Log notification
//            Patient patient = queue.getPatient();
//            User user = patient.getUser();
//            queueService.logNotification(user.getUserId(), Notification.NotificationType.InApp,
//                String.format("Your queue position has been updated. New position: %d.", queue.getPosition()));
//
//            return ResponseEntity.ok(new QueueResponse(queue, user.getFirstName() + " " + user.getLastName()));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Remove patient from queue
//    @DeleteMapping("/{queueId}")
//    public ResponseEntity<?> removeFromQueue(@PathVariable Integer queueId) {
//        try {
//            Queue queue = queueService.getQueueById(queueId);
//            queue.setStatus(Queue.QueueStatus.Canceled);
//            queueService.saveQueueEntry(queue);
//
//            // Update queue positions
//            Integer doctorId = queue.getDoctor() != null ? queue.getDoctor().getDoctorId() : null;
//            queueService.updateQueuePositions(doctorId);
//
//            // Log notification
//            Patient patient = queue.getPatient();
//            User user = patient.getUser();
//            queueService.logNotification(user.getUserId(), Notification.NotificationType.InApp,
//                "You have been removed from the queue.");
//
//            return ResponseEntity.ok("Patient removed from queue");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
//
//// Request DTOs
//class QueueRequest {
//    private Integer patientId;
//    private Integer doctorId;
//    private Integer appointmentId;
//    private String serviceType;
//    private Integer priority;
//
//    // Getters and Setters
//    public Integer getPatientId() { return patientId; }
//    public void setPatientId(Integer patientId) { this.patientId = patientId; }
//    public Integer getDoctorId() { return doctorId; }
//    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
//    public Integer getAppointmentId() { return appointmentId; }
//    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }
//    public String getServiceType() { return serviceType; }
//    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
//    public Integer getPriority() { return priority; }
//    public void setPriority(Integer priority) { this.priority = priority; }
//}
//
//class StatusUpdateRequest {
//    private String status;
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//}
//
//class PriorityUpdateRequest {
//    private Integer priority;
//
//    public Integer getPriority() { return priority; }
//    public void setPriority(Integer priority) { this.priority = priority; }
//}
//
//// Service Interface (Assumed to interact with DAL)
//interface QueueService {
//    Patient getPatientById(Integer patientId);
//    Doctor getDoctorById(Integer doctorId);
//    Appointment getAppointmentById(Integer appointmentId);
//    Queue getQueueById(Integer queueId);
//    Integer getMaxPosition(Integer doctorId);
//    Integer estimateWaitTime(Integer doctorId, String serviceType);
//    Queue saveQueueEntry(Queue queue);
//    List<Queue> getQueueByDoctorIdAndStatus(Integer doctorId, Queue.QueueStatus status);
//    void updateQueuePositions(Integer doctorId);
//    void logNotification(Integer userId, Notification.NotificationType type, String message);
//}