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

-- Patient uses phone number as primary key
CREATE TABLE Patient (
    phone VARCHAR(20) PRIMARY KEY,
    patient_account_id INT UNIQUE, -- Nullable for walk-ins
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender ENUM('Male', 'Female'),
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

CREATE TABLE Specialty (
    specialty_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE DoctorSpecialty (
    staff_id INT NOT NULL,
    specialty_id INT NOT NULL,
    FOREIGN KEY (staff_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (specialty_id) REFERENCES Specialty(specialty_id),
    PRIMARY KEY (staff_id, specialty_id)
);

CREATE TABLE ExaminationPackage (
    package_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    specialty_id INT NOT NULL,
    FOREIGN KEY (specialty_id) REFERENCES Specialty(specialty_id)
);
ALTER TABLE ExaminationPackage
ADD COLUMN tier ENUM('Normal', 'VIP') NOT NULL DEFAULT 'Normal';

-- Use patient phone as FK
CREATE TABLE Appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    doctor_id INT NOT NULL,
    slot_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    status ENUM('No-show','Pending','In progress','Waiting payment','Completed','Canceled','On-hand-off','Back-from-hand-off') DEFAULT 'No-show',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_phone) REFERENCES Patient(phone),
    FOREIGN KEY (doctor_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id)
);
-- Inter2 change business rules on Appointment
ALTER TABLE Appointment ADD COLUMN description TEXT;
ALTER TABLE Appointment ADD COLUMN package_id INT;
ALTER TABLE Appointment ADD FOREIGN KEY (package_id) REFERENCES ExaminationPackage(package_id);

CREATE TABLE DoctorTimeSlot (
    id INT AUTO_INCREMENT PRIMARY KEY,
    staff_id INT NOT NULL,
    slot_id INT NOT NULL,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
    max_appointments INT DEFAULT 10, -- ✅ New field

    FOREIGN KEY (staff_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (slot_id) REFERENCES TimeSlot(slot_id),
    UNIQUE (staff_id, slot_id, day_of_week)
);

-- MedicalRecord now tied to patient directly, not appointment
CREATE TABLE MedicalRecord (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    diagnosis TEXT,
    prescription TEXT,
    notes TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_phone) REFERENCES Patient(phone)
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

CREATE TABLE DoctorHandoff (
    handoff_id INT AUTO_INCREMENT PRIMARY KEY,
    from_doctor_id INT NOT NULL,
    to_doctor_id INT NOT NULL,
    appointment_id INT NOT NULL,
    reason TEXT,
    status ENUM('Pending','In Progress','Completed') DEFAULT 'Pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (from_doctor_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (to_doctor_id) REFERENCES StaffAccount(staff_id),
    FOREIGN KEY (appointment_id) REFERENCES Appointment(appointment_id)
);

CREATE TABLE Token (
	id int AUTO_INCREMENT NOT NULL,
	token varchar(255) NOT NULL,
	expiryTime datetime(6) NOT NULL,
	isUsed bit NOT NULL,
	userId int NOT NULL,
PRIMARY KEY CLUSTERED (id ASC)
);

CREATE TABLE Invoice (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_phone VARCHAR(20) NOT NULL,
    appointment_id INT NOT NULL,
    payment_method VARCHAR(50),
    generate_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Processing', 'Completed', 'Failed') DEFAULT 'Processing',
    
    FOREIGN KEY (patient_phone) REFERENCES Patient(phone),
    FOREIGN KEY (appointment_id) REFERENCES Appointment(appointment_id)
);

CREATE TABLE InvoiceItem (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    description VARCHAR(255) NOT NULL, -- e.g. "X-ray Scan", "Consultation Fee"
    quantity INT DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) AS (quantity * unit_price) STORED,

    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id)
);


ALTER TABLE `swp_clinic`.`user` 
ADD COLUMN `img_path` VARCHAR(255) NULL AFTER `created_at`,
ADD COLUMN `user_bio` VARCHAR(45) NULL AFTER `img_path`,
ADD COLUMN `pdf_path` VARCHAR(45) NULL AFTER `user_bio`;



-- MySQL 8.0 DDL for storing condensed invoices (fixed 3 line items)

-- Single table to store all invoice data, including exactly three items
CREATE TABLE `invoices` (
  `invoice_id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id` INT NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `patient_name` VARCHAR(100) NOT NULL,
  `patient_phone` VARCHAR(20) NOT NULL,
  `doctor_name` VARCHAR(100) NOT NULL,
  `issue_date` DATE NOT NULL,
  `due_date` DATE NOT NULL,
  -- Item 1
  `item1_description` VARCHAR(255) NOT NULL,
  `item1_rate` int NOT NULL,
  -- Item 2
  `item2_description` VARCHAR(255) NOT NULL,
  `item2_rate` int NOT NULL,
  -- Item 3
  `item3_description` VARCHAR(255) NOT NULL,
  `item3_rate` int NOT NULL,
  -- Computed totals
  `subtotal` int AS (
    item1_rate + item2_rate + item3_rate
  ) STORED,
  `total` int AS (subtotal) STORED,

  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `invoices`
  ADD COLUMN `package` NVARCHAR(100) NOT NULL DEFAULT '' AFTER `patient_phone`;
  
  ALTER TABLE invoices
MODIFY COLUMN item1_rate INT NOT NULL,
MODIFY COLUMN item2_rate INT NOT NULL,
MODIFY COLUMN item3_rate INT NOT NULL,
MODIFY COLUMN subtotal INT AS (item1_rate + item2_rate + item3_rate) STORED,
MODIFY COLUMN total INT AS (subtotal) STORED;

CREATE TABLE `payment` (
  `payment_id`     INT            NOT NULL AUTO_INCREMENT,
  `user_id`        INT            NULL,
  `amount`         DECIMAL(10,2)  NULL,
  `payment_date`   DATETIME       NULL,
  `appointment_id` INT            NULL,
  `status`         ENUM('Processing','Completed','Failed') NOT NULL DEFAULT 'Processing',
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;

ALTER TABLE `swp_clinic`.`appointment` 
CHANGE COLUMN `status` `status` ENUM('Pending', 'Approved', 'Waiting', 'In progress', 'Cancelled', 'Completed', 'No-show', 'Waiting-Payment', 'Payment-Complete') NULL DEFAULT 'Pending' ;