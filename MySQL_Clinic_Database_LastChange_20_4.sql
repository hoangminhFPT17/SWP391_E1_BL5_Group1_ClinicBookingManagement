-- Drop every table function
/* 
SET FOREIGN_KEY_CHECKS = 0;
SET @tables = NULL;
SELECT GROUP_CONCAT(table_schema, '.', table_name) INTO @tables
  FROM information_schema.tables 
  WHERE table_schema = DATABASE(); -- Only drop tables in the current database
SELECT IFNULL(@tables,'') INTO @tables;
SET @stmt = IF(@tables IS NULL, 'SELECT ''No tables found''', CONCAT('DROP TABLES ', @tables));
PREPARE stmt FROM @stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;
*/

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    full_name VARCHAR(100),
    is_verified BOOLEAN DEFAULT FALSE,
    otp_code VARCHAR(10),
    otp_expiry DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE StaffAccount (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    role ENUM('Doctor', 'Manager', 'Receptionist') NOT NULL,
    department VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE PatientAccount (
    patient_account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- ✅ Patient uses phone number as primary key
CREATE TABLE Patient (
    phone VARCHAR(20) PRIMARY KEY,
    patient_account_id INT UNIQUE, -- Nullable for walk-ins
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender ENUM('Male', 'Female', 'Other'),
    email VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_account_id) REFERENCES PatientAccount(patient_account_id)
);

CREATE TABLE TimeSlot (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL, -- e.g. "Morning1"
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- ✅ Use patient phone as FK
CREATE TABLE Appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    doctor_id INT NOT NULL,
    slot_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    status ENUM('Pending', 'Approved', 'Cancelled', 'Completed') DEFAULT 'Pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_phone) REFERENCES Patient(phone),
    FOREIGN KEY (doctor_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id)
);

CREATE TABLE DoctorTimeSlot (
    id INT AUTO_INCREMENT PRIMARY KEY,
    staff_id INT NOT NULL,
    slot_id INT NOT NULL,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
    FOREIGN KEY (staff_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id),
    UNIQUE (staff_id, slot_id, day_of_week)
);

-- ✅ MedicalRecord now tied to patient directly, not appointment
CREATE TABLE MedicalRecord (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    diagnosis TEXT,
    prescription TEXT,
    notes TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_phone) REFERENCES Patient(phone)
);

CREATE TABLE PatientQueue (
    queue_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    doctor_id INT NOT NULL,
    slot_id INT NOT NULL,
    queue_date DATE NOT NULL,

    priority_number INT NOT NULL, -- e.g., order of arrival or custom priority
    patient_type ENUM('Appointment', 'Walk-in') NOT NULL,
    status ENUM('Hasn\'t Arrived', 'Waiting', 'In Progress', 'Completed') DEFAULT 'Hasn\'t Arrived',

    arrival_time DATETIME, -- only set when patient shows up (not when receptionist adds them)
    created_by INT, -- user_id of receptionist who added the patient

    FOREIGN KEY (patient_phone) REFERENCES Patient(phone),
    FOREIGN KEY (doctor_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id),
    FOREIGN KEY (created_by) REFERENCES User(user_id),

    UNIQUE (patient_phone, doctor_id, queue_date) -- optional: prevent duplicates
);

CREATE TABLE DoctorUnavailability (
    unavailability_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_id INT NOT NULL, -- Doctor ID
    slot_id INT NOT NULL, -- Time slot (e.g. Morning)
    unavailable_date DATE NOT NULL,
    reason TEXT, -- Optional: sick leave, vacation, etc.

    FOREIGN KEY (staff_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id),
    
    UNIQUE (staff_id, slot_id, unavailable_date) -- Prevent duplicates
);

CREATE TABLE token (
	id int AUTO_INCREMENT NOT NULL,
	token varchar(255) NOT NULL,
	expiryTime datetime(6) NOT NULL,
	isUsed bit NOT NULL,
	userId int NOT NULL,
PRIMARY KEY CLUSTERED (id ASC)
);

