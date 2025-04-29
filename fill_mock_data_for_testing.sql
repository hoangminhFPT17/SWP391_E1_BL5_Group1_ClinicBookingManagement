-- Insert time slots
INSERT INTO TimeSlot (name, start_time, end_time, is_active) VALUES
('Morning1', '07:00:00', '09:00:00', TRUE),
('Morning2', '09:00:00', '11:00:00', TRUE),
('Afternoon1', '12:00:00', '14:00:00', TRUE),
('Afternoon2', '14:00:00', '16:00:00', TRUE),
('Night1', '16:00:00', '18:00:00', FALSE);

-- Insert Users
INSERT INTO User (email, password_hash, phone, full_name, is_verified)
VALUES 
('house@example.com', 'hash1', '1112223333', 'Dr. Gregory House', TRUE),
('cuddy@example.com', 'hash2', '1112223334', 'Dr. Lisa Cuddy', TRUE),
('wilson@example.com', 'hash3', '1112223335', 'Dr. James Wilson', TRUE),
('cameron@example.com', 'hash4', '1112223336', 'Dr. Allison Cameron', TRUE),
('chase@example.com', 'hash5', '1112223337', 'Dr. Robert Chase', TRUE),
('foreman@example.com', 'hash6', '1112223338', 'Dr. Eric Foreman', TRUE),
('reception@example.com', 'hash7', '1112223340', 'Brenda Receptionist', TRUE),
('patient1@example.com', 'hash8', '2223334444', 'John Doe', TRUE),
('patient2@example.com', 'hash9', '3334445555', 'Jane Smith', TRUE);

-- Insert StaffAccount using subqueries to get user_id
INSERT INTO StaffAccount (user_id, role, department) VALUES
((SELECT user_id FROM User WHERE email = 'house@example.com'), 'Doctor', 'Diagnostics'),
((SELECT user_id FROM User WHERE email = 'cuddy@example.com'), 'Manager', 'Administration'),
((SELECT user_id FROM User WHERE email = 'wilson@example.com'), 'Doctor', 'Oncology'),
((SELECT user_id FROM User WHERE email = 'cameron@example.com'), 'Doctor', 'Immunology'),
((SELECT user_id FROM User WHERE email = 'chase@example.com'), 'Doctor', 'Surgery'),
((SELECT user_id FROM User WHERE email = 'foreman@example.com'), 'Doctor', 'Neurology'),
((SELECT user_id FROM User WHERE email = 'reception@example.com'), 'Receptionist', 'Front Desk');

-- Insert PatientAccount
INSERT INTO PatientAccount (user_id)
VALUES 
((SELECT user_id FROM User WHERE email = 'patient1@example.com')),
((SELECT user_id FROM User WHERE email = 'patient2@example.com'));

-- Insert Patients
INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email)
VALUES
('2223334444', (SELECT patient_account_id FROM PatientAccount WHERE user_id = (SELECT user_id FROM User WHERE email = 'patient1@example.com')), 'John Doe', '1990-05-15', 'Male', 'patient1@example.com'),
('3334445555', (SELECT patient_account_id FROM PatientAccount WHERE user_id = (SELECT user_id FROM User WHERE email = 'patient2@example.com')), 'Jane Smith', '1985-10-22', 'Female', 'patient2@example.com');

-- Insert Specialties
INSERT INTO Specialty (name) VALUES 
('Diagnostics'), ('Oncology'), ('Immunology'), ('Surgery'), ('Neurology'),('General Medicine');

-- Insert DoctorSpecialty
INSERT INTO DoctorSpecialty (staff_id, specialty_id) VALUES
(1, 1), -- House - Diagnostics
(3, 2), -- Wilson - Oncology
(4, 3), -- Cameron - Immunology
(5, 4), -- Chase - Surgery
(6, 5); -- Foreman - Neurology

-- Insert General Medicine Examination Packages
INSERT INTO ExaminationPackage (name, description, specialty_id) VALUES
('Normal Checkup', 'Basic consultation and health check for general concerns', 
 (SELECT specialty_id FROM Specialty WHERE name = 'General Medicine')),
('VIP Checkup', 'Comprehensive consultation with extended tests and personalized care', 
 (SELECT specialty_id FROM Specialty WHERE name = 'General Medicine'));

-- Insert DoctorTimeSlot coverage: Spread out doctor coverage at random
INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week, max_appointments)
VALUES
-- Monday
(1, 1, 'Monday', 5), (1, 2, 'Monday', 5), (1, 4, 'Monday', 5),
(3, 1, 'Monday', 5), (3, 4, 'Monday', 5), (3, 5, 'Monday', 5),
(4, 1, 'Monday', 4), (4, 2, 'Monday', 4), (4, 4, 'Monday', 4), (4, 5, 'Monday', 4),
(5, 1, 'Monday', 5), (5, 2, 'Monday', 5), (5, 3, 'Monday', 5), (5, 4, 'Monday', 5), (5, 5, 'Monday', 5),
(6, 3, 'Monday', 3), (6, 5, 'Monday', 3),

-- Tuesday
(1, 2, 'Tuesday', 5), (1, 4, 'Tuesday', 5), (1, 5, 'Tuesday', 5),
(3, 1, 'Tuesday', 5), (3, 2, 'Tuesday', 5), (3, 3, 'Tuesday', 5), (3, 4, 'Tuesday', 5), (3, 5, 'Tuesday', 5),
(4, 3, 'Tuesday', 5), (4, 4, 'Tuesday', 5),
(5, 1, 'Tuesday', 5), (5, 2, 'Tuesday', 5), (5, 5, 'Tuesday', 5),
(6, 1, 'Tuesday', 5), (6, 2, 'Tuesday', 5), (6, 3, 'Tuesday', 5), (6, 4, 'Tuesday', 5), (6, 5, 'Tuesday', 5),

-- Wednesday
(1, 1, 'Wednesday', 5), (1, 3, 'Wednesday', 5), (1, 4, 'Wednesday', 5), (1, 5, 'Wednesday', 5),
(3, 1, 'Wednesday', 5), (3, 2, 'Wednesday', 5), (3, 3, 'Wednesday', 5), (3, 4, 'Wednesday', 5), (3, 5, 'Wednesday', 5),
(4, 1, 'Wednesday', 5), (4, 2, 'Wednesday', 5), (4, 5, 'Wednesday', 5),
(5, 1, 'Wednesday', 5), (5, 3, 'Wednesday', 5), (5, 4, 'Wednesday', 5), (5, 5, 'Wednesday', 5),
(6, 4, 'Wednesday', 5), (6, 5, 'Wednesday', 5),

-- Thursday
(1, 2, 'Thursday', 5), (1, 4, 'Thursday', 5), (1, 5, 'Thursday', 5),
(3, 1, 'Thursday', 5), (3, 3, 'Thursday', 5), (3, 4, 'Thursday', 5), (3, 5, 'Thursday', 5),
(4, 1, 'Thursday', 5), (4, 2, 'Thursday', 5), (4, 5, 'Thursday', 5),
(5, 2, 'Thursday', 5), (5, 4, 'Thursday', 5), (5, 5, 'Thursday', 5),
(6, 2, 'Thursday', 5), (6, 3, 'Thursday', 5), (6, 4, 'Thursday', 5), (6, 5, 'Thursday', 5),

-- Friday
(1, 2, 'Friday', 5), (1, 3, 'Friday', 5), (1, 5, 'Friday', 5),
(3, 3, 'Friday', 5), (3, 4, 'Friday', 5),
(4, 1, 'Friday', 5), (4, 2, 'Friday', 5), (4, 4, 'Friday', 5), (4, 5, 'Friday', 5),
(5, 3, 'Friday', 5), (5, 4, 'Friday', 5), (5, 5, 'Friday', 5),
(6, 1, 'Friday', 5), (6, 2, 'Friday', 5), (6, 4, 'Friday', 5), (6, 5, 'Friday', 5),

-- Saturday
(1, 1, 'Saturday', 4), (1, 3, 'Saturday', 4), (1, 5, 'Saturday', 4),
(3, 1, 'Saturday', 4), (3, 2, 'Saturday', 4), (3, 4, 'Saturday', 4), (3, 5, 'Saturday', 4),
(4, 1, 'Saturday', 4), (4, 2, 'Saturday', 4), (4, 3, 'Saturday', 4), (4, 4, 'Saturday', 4), (4, 5, 'Saturday', 4),
(5, 2, 'Saturday', 4), (5, 3, 'Saturday', 4), (5, 4, 'Saturday', 4), (5, 5, 'Saturday', 4),
(6, 2, 'Saturday', 4), (6, 3, 'Saturday', 4), (6, 4, 'Saturday', 4), (6, 5, 'Saturday', 4),

-- Sunday
(1, 2, 'Sunday', 4), (1, 3, 'Sunday', 4), (1, 4, 'Sunday', 4), (1, 5, 'Sunday', 4),
(3, 1, 'Sunday', 4), (3, 4, 'Sunday', 4), (3, 5, 'Sunday', 4),
(4, 2, 'Sunday', 4), (4, 3, 'Sunday', 4),
(5, 1, 'Sunday', 4),
(6, 1, 'Sunday', 4);

-- Insert Appointments
INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status, package_id, description)
VALUES
('2223334444', 1, 1, '2025-04-25', 'Pending', 1, 'House diagnostic evaluation'),
('3334445555', 3, 2, '2025-04-26', 'Pending', 2, 'Oncology initial consult');

-- Insert Medical Records
INSERT INTO MedicalRecord (patient_phone, diagnosis, prescription, notes)
VALUES
('2223334444', 'Unknown condition', 'Vicodin', 'House suspects autoimmune'),
('3334445555', 'Benign tumor', 'Observation', 'Follow-up in 6 months');

-- Insert DoctorUnavailability
INSERT INTO DoctorUnavailability (staff_id, slot_id, unavailable_date, reason)
VALUES
(1, 1, '2025-04-28', 'Vacation'),
(3, 2, '2025-04-29', 'Medical conference');

-- Insert Doctor Handoff
INSERT INTO DoctorHandoff (from_doctor_id, to_doctor_id, appointment_id, reason, status)
VALUES
(1, 4, 1, 'House busy with another case', 'In Progress');

-- Insert Token (example only)
INSERT INTO token (token, expiryTime, isUsed, userId)
VALUES
('abc123token', '2025-05-01 12:00:00', 0, 1);

-- Insert Invoices for two appointments
INSERT INTO Invoice (patient_phone, appointment_id, payment_method, status)
VALUES 
('2223334444', 1, 'Credit Card', 'Completed'),
('3334445555', 2, 'Cash', 'Processing');

-- Insert InvoiceItems for invoice_id = 1 (House's patient)
INSERT INTO InvoiceItem (invoice_id, description, quantity, unit_price)
VALUES 
(1, 'Consultation Fee', 1, 300.00),
(1, 'Blood Test', 1, 100.00),
(1, 'X-ray Scan', 1, 150.00);

-- Insert InvoiceItems for invoice_id = 2 (Wilson's patient)
INSERT INTO InvoiceItem (invoice_id, description, quantity, unit_price)
VALUES 
(2, 'Oncology Consultation', 1, 400.00),
(2, 'MRI Scan', 1, 500.00);

INSERT INTO swp_clinic.`user` (email,password_hash,phone,full_name,is_verified,otp_code,otp_expiry,created_at,img_path,user_bio,pdf_path) VALUES
	 ('test2@gmail.com','32c9583de09461a7210cf28cfe80aa2c','3334445557','Kiet2',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL),
('test@gmail.com','32c9583de09461a7210cf28cfe80aa2c','3334445556','Kiet',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL);

INSERT INTO StaffAccount (user_id, role, department) VALUES
((SELECT user_id FROM User WHERE email = 'test@gmail.com'), 'Doctor', 'Neurology'),
((SELECT user_id FROM User WHERE email = 'test2@gmail.com'), 'Doctor', 'Neurology'),
((SELECT user_id FROM User WHERE email = 'test3@gmail.com'), 'Manager', 'Administration'),
((SELECT user_id FROM User WHERE email = 'test4@gmail.com'), 'Receptionist', 'Front desk');

INSERT INTO swp_clinic.`user` (email,password_hash,phone,full_name,is_verified,otp_code,otp_expiry,created_at,img_path,user_bio,pdf_path) VALUES
('test3@gmail.com','32c9583de09461a7210cf28cfe80aa2c','3334445557','Kiet2',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL),
('test4@gmail.com','32c9583de09461a7210cf28cfe80aa2c','3334445556','Kiet',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL);
/*
-- Full DoctorTimeSlot coverage: 5 doctors × 5 slots × 7 days = 175 entries
INSERT INTO DoctorTimeSlot (staff_id, slot_id, day_of_week, max_appointments)
VALUES
-- Monday
(1, 1, 'Monday', 5), (1, 2, 'Monday', 5), (1, 3, 'Monday', 5), (1, 4, 'Monday', 5), (1, 5, 'Monday', 5),
(3, 1, 'Monday', 5), (3, 2, 'Monday', 5), (3, 3, 'Monday', 5), (3, 4, 'Monday', 5), (3, 5, 'Monday', 5),
(4, 1, 'Monday', 5), (4, 2, 'Monday', 5), (4, 3, 'Monday', 5), (4, 4, 'Monday', 5), (4, 5, 'Monday', 5),
(5, 1, 'Monday', 5), (5, 2, 'Monday', 5), (5, 3, 'Monday', 5), (5, 4, 'Monday', 5), (5, 5, 'Monday', 5),
(6, 1, 'Monday', 5), (6, 2, 'Monday', 5), (6, 3, 'Monday', 5), (6, 4, 'Monday', 5), (6, 5, 'Monday', 5),

-- Tuesday
(1, 1, 'Tuesday', 5), (1, 2, 'Tuesday', 5), (1, 3, 'Tuesday', 5), (1, 4, 'Tuesday', 5), (1, 5, 'Tuesday', 5),
(3, 1, 'Tuesday', 5), (3, 2, 'Tuesday', 5), (3, 3, 'Tuesday', 5), (3, 4, 'Tuesday', 5), (3, 5, 'Tuesday', 5),
(4, 1, 'Tuesday', 5), (4, 2, 'Tuesday', 5), (4, 3, 'Tuesday', 5), (4, 4, 'Tuesday', 5), (4, 5, 'Tuesday', 5),
(5, 1, 'Tuesday', 5), (5, 2, 'Tuesday', 5), (5, 3, 'Tuesday', 5), (5, 4, 'Tuesday', 5), (5, 5, 'Tuesday', 5),
(6, 1, 'Tuesday', 5), (6, 2, 'Tuesday', 5), (6, 3, 'Tuesday', 5), (6, 4, 'Tuesday', 5), (6, 5, 'Tuesday', 5),

-- Wednesday
(1, 1, 'Wednesday', 5), (1, 2, 'Wednesday', 5), (1, 3, 'Wednesday', 5), (1, 4, 'Wednesday', 5), (1, 5, 'Wednesday', 5),
(3, 1, 'Wednesday', 5), (3, 2, 'Wednesday', 5), (3, 3, 'Wednesday', 5), (3, 4, 'Wednesday', 5), (3, 5, 'Wednesday', 5),
(4, 1, 'Wednesday', 5), (4, 2, 'Wednesday', 5), (4, 3, 'Wednesday', 5), (4, 4, 'Wednesday', 5), (4, 5, 'Wednesday', 5),
(5, 1, 'Wednesday', 5), (5, 2, 'Wednesday', 5), (5, 3, 'Wednesday', 5), (5, 4, 'Wednesday', 5), (5, 5, 'Wednesday', 5),
(6, 1, 'Wednesday', 5), (6, 2, 'Wednesday', 5), (6, 3, 'Wednesday', 5), (6, 4, 'Wednesday', 5), (6, 5, 'Wednesday', 5),

-- Thursday
(1, 1, 'Thursday', 5), (1, 2, 'Thursday', 5), (1, 3, 'Thursday', 5), (1, 4, 'Thursday', 5), (1, 5, 'Thursday', 5),
(3, 1, 'Thursday', 5), (3, 2, 'Thursday', 5), (3, 3, 'Thursday', 5), (3, 4, 'Thursday', 5), (3, 5, 'Thursday', 5),
(4, 1, 'Thursday', 5), (4, 2, 'Thursday', 5), (4, 3, 'Thursday', 5), (4, 4, 'Thursday', 5), (4, 5, 'Thursday', 5),
(5, 1, 'Thursday', 5), (5, 2, 'Thursday', 5), (5, 3, 'Thursday', 5), (5, 4, 'Thursday', 5), (5, 5, 'Thursday', 5),
(6, 1, 'Thursday', 5), (6, 2, 'Thursday', 5), (6, 3, 'Thursday', 5), (6, 4, 'Thursday', 5), (6, 5, 'Thursday', 5),

-- Friday
(1, 1, 'Friday', 5), (1, 2, 'Friday', 5), (1, 3, 'Friday', 5), (1, 4, 'Friday', 5), (1, 5, 'Friday', 5),
(3, 1, 'Friday', 5), (3, 2, 'Friday', 5), (3, 3, 'Friday', 5), (3, 4, 'Friday', 5), (3, 5, 'Friday', 5),
(4, 1, 'Friday', 5), (4, 2, 'Friday', 5), (4, 3, 'Friday', 5), (4, 4, 'Friday', 5), (4, 5, 'Friday', 5),
(5, 1, 'Friday', 5), (5, 2, 'Friday', 5), (5, 3, 'Friday', 5), (5, 4, 'Friday', 5), (5, 5, 'Friday', 5),
(6, 1, 'Friday', 5), (6, 2, 'Friday', 5), (6, 3, 'Friday', 5), (6, 4, 'Friday', 5), (6, 5, 'Friday', 5),

-- Saturday
(1, 1, 'Saturday', 4), (1, 2, 'Saturday', 4), (1, 3, 'Saturday', 4), (1, 4, 'Saturday', 4), (1, 5, 'Saturday', 4),
(3, 1, 'Saturday', 4), (3, 2, 'Saturday', 4), (3, 3, 'Saturday', 4), (3, 4, 'Saturday', 4), (3, 5, 'Saturday', 4),
(4, 1, 'Saturday', 4), (4, 2, 'Saturday', 4), (4, 3, 'Saturday', 4), (4, 4, 'Saturday', 4), (4, 5, 'Saturday', 4),
(5, 1, 'Saturday', 4), (5, 2, 'Saturday', 4), (5, 3, 'Saturday', 4), (5, 4, 'Saturday', 4), (5, 5, 'Saturday', 4),
(6, 1, 'Saturday', 4), (6, 2, 'Saturday', 4), (6, 3, 'Saturday', 4), (6, 4, 'Saturday', 4), (6, 5, 'Saturday', 4),

-- Sunday
(1, 1, 'Sunday', 4), (1, 2, 'Sunday', 4), (1, 3, 'Sunday', 4), (1, 4, 'Sunday', 4), (1, 5, 'Sunday', 4),
(3, 1, 'Sunday', 4), (3, 2, 'Sunday', 4), (3, 3, 'Sunday', 4), (3, 4, 'Sunday', 4), (3, 5, 'Sunday', 4),
(4, 1, 'Sunday', 4), (4, 2, 'Sunday', 4), (4, 3, 'Sunday', 4), (4, 4, 'Sunday', 4), (4, 5, 'Sunday', 4),
(5, 1, 'Sunday', 4), (5, 2, 'Sunday', 4), (5, 3, 'Sunday', 4), (5, 4, 'Sunday', 4), (5, 5, 'Sunday', 4),
(6, 1, 'Sunday', 4), (6, 2, 'Sunday', 4), (6, 3, 'Sunday', 4), (6, 4, 'Sunday', 4), (6, 5, 'Sunday', 4);
*/
