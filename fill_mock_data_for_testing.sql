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
('house@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223333', 'Dr. Gregory House', TRUE),
('cuddy@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223334', 'Dr. Lisa Cuddy', TRUE),
('wilson@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223335', 'Dr. James Wilson', TRUE),
('cameron@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223336', 'Dr. Allison Cameron', TRUE),
('chase@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223337', 'Dr. Robert Chase', TRUE),
('foreman@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223338', 'Dr. Eric Foreman', TRUE),
('reception@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '1112223340', 'Brenda Receptionist', TRUE),
('patient1@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '2223334444', 'John Doe', TRUE),
('patient2@example.com', '827ccb0eea8a706c4c34a16891f84e7b', '3334445555', 'Jane Smith', TRUE);

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

-- Patients with linked accounts
INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email)
VALUES
('2223334444', (SELECT patient_account_id FROM PatientAccount WHERE user_id = (SELECT user_id FROM User WHERE email = 'patient1@example.com')), 'John Doe', '1990-05-15', 'Male', 'patient1@example.com'),
('3334445555', (SELECT patient_account_id FROM PatientAccount WHERE user_id = (SELECT user_id FROM User WHERE email = 'patient2@example.com')), 'Jane Smith', '1985-10-22', 'Female', 'patient2@example.com');

-- Walk-in patients (no patient_account_id)
INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email)
VALUES
('4445556666', NULL, 'Martha M. Masters', '1992-08-10', 'Female', 'martha@example.com'),
('5556667777', NULL, 'Amber Volakis', '1984-03-05', 'Female', 'amber@example.com'),
('6667778888', NULL, 'Michael Tritter', '1970-01-20', 'Male', 'tritter@example.com');

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

-- Insert Examination Packages for all specialties with Normal and VIP tiers
INSERT INTO ExaminationPackage (name, description, specialty_id, tier) VALUES
-- Diagnostics
('Diagnostics - Normal Checkup', 'Basic diagnostic procedures and evaluation', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Diagnostics'), 'Normal'),
('Diagnostics - VIP Checkup', 'Advanced diagnostic testing with priority consultation', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Diagnostics'), 'VIP'),

-- Oncology
('Oncology - Normal Screening', 'Routine cancer screening tests', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Oncology'), 'Normal'),
('Oncology - VIP Screening', 'Comprehensive oncology tests with expert review', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Oncology'), 'VIP'),

-- Immunology
('Immunology - Normal Assessment', 'Standard immune system evaluation', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Immunology'), 'Normal'),
('Immunology - VIP Assessment', 'Detailed immunological tests with personalized consultation', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Immunology'), 'VIP'),

-- Surgery
('Surgery - Normal Consultation', 'Basic surgical consultation and evaluation', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Surgery'), 'Normal'),
('Surgery - VIP Consultation', 'Surgical evaluation with premium services and follow-up', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Surgery'), 'VIP'),

-- Neurology
('Neurology - Normal Exam', 'Routine neurological assessment', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Neurology'), 'Normal'),
('Neurology - VIP Exam', 'Comprehensive brain and nerve diagnostics with top-tier specialists', 
 (SELECT specialty_id FROM Specialty WHERE name = 'Neurology'), 'VIP'),

-- General Medicine
('General Medicine - Normal Checkup', 'Basic consultation and health check for general concerns', 
 (SELECT specialty_id FROM Specialty WHERE name = 'General Medicine'), 'Normal'),
('General Medicine - VIP Checkup', 'Comprehensive consultation with extended tests and personalized care', 
 (SELECT specialty_id FROM Specialty WHERE name = 'General Medicine'), 'VIP');

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

-- Insert Appointments (future/pending)
INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status, package_id, description)
VALUES
('2223334444', 1, 1, '2025-04-25', 'Pending', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Diagnostics - Normal Checkup'), 
    'House diagnostic evaluation'),
('3334445555', 3, 2, '2025-04-26', 'Pending', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Oncology - Normal Screening'), 
    'Oncology initial consult'),

-- Appointments (linked to real patients, past/completed)
('2223334444', 1, 1, '2025-04-01', 'Completed', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Diagnostics - Normal Checkup'), 
    'Diagnostics with House'),
('3334445555', 3, 2, '2025-04-05', 'Completed', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Oncology - Normal Screening'), 
    'Oncology consult with Wilson'),

-- Appointments (walk-ins)
('4445556666', 4, 3, '2025-04-07', 'Completed', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'General Medicine - Normal Checkup'), 
    'General checkup with Cameron'),
('5556667777', 5, 4, '2025-04-10', 'Completed', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Surgery - VIP Consultation'), 
    'VIP Surgery consult with Chase'),
('6667778888', 6, 1, '2025-04-12', 'Completed', 
    (SELECT package_id FROM ExaminationPackage WHERE name = 'Neurology - Normal Exam'), 
    'Neurology screening with Foreman');

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
('test1@gmail.com','827ccb0eea8a706c4c34a16891f84e7b','3334445557','Kiet1',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL),
('test2@gmail.com','827ccb0eea8a706c4c34a16891f84e7b','3334445556','Kiet2',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL),
('test3@gmail.com','827ccb0eea8a706c4c34a16891f84e7b','3334445557','Kiet3',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL),
('test4@gmail.com','827ccb0eea8a706c4c34a16891f84e7b','3334445556','Kiet4',1,NULL,NULL,'2025-04-28 13:39:46',NULL,NULL,NULL);

INSERT INTO StaffAccount (user_id, role, department) VALUES
((SELECT user_id FROM User WHERE email = 'test1@gmail.com'), 'Doctor', 'Neurology'),
((SELECT user_id FROM User WHERE email = 'test2@gmail.com'), 'Doctor', 'Neurology'),
((SELECT user_id FROM User WHERE email = 'test3@gmail.com'), 'Manager', 'Administration'),
((SELECT user_id FROM User WHERE email = 'test4@gmail.com'), 'Receptionist', 'Front desk');

-- Walk-in patients (no account)
INSERT INTO Patient (phone, patient_account_id, full_name, date_of_birth, gender, email, created_at)
VALUES
('7771112222', NULL, 'Rachel Taub', '1985-09-20', 'Female', 'rachel.taub@example.com', '2025-03-01 09:30:00'),
('8882223333', NULL, 'Jeffrey Cole', '1988-01-11', 'Male', 'jeffrey.cole@example.com', '2025-03-02 10:00:00'),
('9993334444', NULL, 'Amber Volakis', '1983-04-25', 'Female', 'amber.volakis@example.com', '2025-03-03 11:15:00'),
('0004445555', NULL, 'Henry Dobson', '1979-08-14', 'Male', 'henry.dobson@example.com', '2025-03-04 08:50:00'),
('1115556666', NULL, 'Sam Carr', '1990-06-30', 'Male', 'sam.carr@example.com', '2025-03-05 14:45:00');

-- Past completed appointments (walk-in patients)
INSERT INTO Appointment (patient_phone, doctor_id, slot_id, appointment_date, status, description, package_id, created_at)
VALUES
('7771112222', 2, 1, '2025-03-01', 'Completed', 'Routine diagnostics by Dr. Foreman',
 (SELECT package_id FROM ExaminationPackage WHERE name = 'Diagnostics - Normal Checkup'), '2025-02-28 16:00:00'),

('8882223333', 3, 2, '2025-03-02', 'Completed', 'Cancer screening with Dr. Wilson',
 (SELECT package_id FROM ExaminationPackage WHERE name = 'Oncology - Normal Screening'), '2025-03-01 09:00:00'),

('9993334444', 4, 3, '2025-03-03', 'Completed', 'Neurology review with Dr. Foreman',
 (SELECT package_id FROM ExaminationPackage WHERE name = 'Neurology - Normal Exam'), '2025-03-02 11:00:00'),

('0004445555', 5, 4, '2025-03-04', 'Completed', 'Surgery consult with Dr. Chase',
 (SELECT package_id FROM ExaminationPackage WHERE name = 'Surgery - Normal Consultation'), '2025-03-03 10:15:00'),

('1115556666', 6, 1, '2025-03-05', 'Completed', 'General medicine visit with Dr. Cameron',
 (SELECT package_id FROM ExaminationPackage WHERE name = 'General Medicine - Normal Checkup'), '2025-03-04 14:10:00');

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
