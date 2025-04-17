-- Insert time slots
INSERT INTO TimeSlot (name, start_time, end_time) VALUES
('Morning', '08:00:00', '12:00:00'),
('Afternoon', '13:00:00', '17:00:00'),
('Night', '18:00:00', '21:00:00');

-- Insert users (Doctor, Receptionist, Patient)
INSERT INTO User (email, password_hash, phone, full_name, is_verified)
VALUES
('doc@example.com', 'hashedpass1', '1111111111', 'Dr. Strange', TRUE),         -- user_id = 1
('reception@example.com', 'hashedpass2', '2222222222', 'Reception Lady', TRUE), -- user_id = 2
('patient@example.com', 'hashedpass3', '3333333333', 'Tony Patient', TRUE);     -- user_id = 3

-- Insert staff accounts
INSERT INTO StaffAccount (user_id, role, department)
VALUES
(1, 'Doctor', 'General Medicine'),
(2, 'Receptionist', 'Front Desk');

-- Insert patient account
INSERT INTO PatientAccount (user_id) VALUES (3); -- patient_account_id = 1

-- Insert patient (phone is the primary key)
INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email)
VALUES ('3333333333', 1, 'Tony Patient', '1990-01-01', 'Male', 'patient@example.com');

-- Assign doctor to time slots
INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week)
VALUES
(1, 1, 'Monday'),  -- Morning
(1, 2, 'Monday'),  -- Afternoon
(1, 3, 'Monday');  -- Night

-- Insert a few appointments
INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status)
VALUES
('3333333333', 1, 1, '2025-05-01', 'Approved'),
('3333333333', 1, 2, '2025-05-03', 'Pending'),
('3333333333', 1, 3, '2025-05-05', 'Completed');

-- Queue for appointment + walk-in
INSERT INTO PatientQueue (patient_phone, doctor_id, slot_id, queue_date, priority_number, patient_type, status, arrival_time, created_by)
VALUES
('3333333333', 1, 1, '2025-05-01', 1, 'Appointment', 'Waiting', '2025-05-01 07:55:00', 2),
('3333333333', 1, 2, '2025-05-02', 2, 'Walk-in', 'Hasn\'t Arrived', NULL, 2);

-- Unavailability for doctor
INSERT INTO DoctorUnavailability (staff_id, slot_id, unavailable_date, reason)
VALUES
(1, 1, '2025-05-24', 'Personal Leave'); -- Unavailable in Morning on 24th May
