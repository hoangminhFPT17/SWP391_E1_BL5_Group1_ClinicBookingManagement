<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>ClinicBooking - Online Medical Appointments</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Book appointments with top doctors online" />
        <meta name="keywords" content="clinic, booking, doctor, appointment, medical" />
        
        <!-- favicon -->
        <link rel="shortcut icon" href="assets/images/favicon.ico">
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        
        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/@mdi/font@6.5.95/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet" />
        
        <!-- Main CSS -->
        <link href="assets/css/main.css" rel="stylesheet" type="text/css" />
    </head>
    
    <body>
        <!-- Include Header -->
        <jsp:include page="cpn/header.jsp" />
        
        <!-- Hero Section -->
        <section class="hero-section">
            <div class="container">
                <div class="hero-content">
                    <h1 class="hero-title">Your Health Is Our Priority</h1>
                    <p class="hero-text">Book appointments with top doctors online, get medical consultations, and manage your healthcare journey with ease.</p>
                    <div>
                        <a href="appointment.jsp" class="btn btn-primary">Book Appointment</a>
                        <a href="#about" class="btn btn-light ms-2">Learn More</a>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Features Start -->
        <section class="section py-5">
            <div class="container">
                <div class="text-center mb-5">
                    <h2 class="mb-3">Our Services</h2>
                    <p class="text-muted">Experience top-quality healthcare services designed to meet your medical needs</p>
                </div>

                <div class="row">
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card h-100 border-0 shadow-sm">
                            <div class="card-body text-center">
                                <div class="mb-4">
                                    <i class="fas fa-calendar-check text-primary" style="font-size: 3rem;"></i>
                                </div>
                                <h5>Easy Scheduling</h5>
                                <p class="text-muted">Book appointments online with just a few clicks, anytime and anywhere.</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card h-100 border-0 shadow-sm">
                            <div class="card-body text-center">
                                <div class="mb-4">
                                    <i class="fas fa-user-md text-primary" style="font-size: 3rem;"></i>
                                </div>
                                <h5>Expert Doctors</h5>
                                <p class="text-muted">Access a network of qualified specialists across multiple medical fields.</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card h-100 border-0 shadow-sm">
                            <div class="card-body text-center">
                                <div class="mb-4">
                                    <i class="fas fa-heartbeat text-primary" style="font-size: 3rem;"></i>
                                </div>
                                <h5>Quality Care</h5>
                                <p class="text-muted">Receive personalized treatment plans and follow-ups for your health concerns.</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card h-100 border-0 shadow-sm">
                            <div class="card-body text-center">
                                <div class="mb-4">
                                    <i class="fas fa-headset text-primary" style="font-size: 3rem;"></i>
                                </div>
                                <h5>24/7 Support</h5>
                                <p class="text-muted">Get assistance whenever you need it with our round-the-clock customer service.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Features End -->
        
        <!-- About Section -->
        <section class="section py-5 bg-light" id="about">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6 mb-4 mb-lg-0">
                        <img src="assets/images/about/about-2.png" class="img-fluid rounded shadow" alt="About Clinic">
                    </div>
                    <div class="col-lg-6">
                        <h2 class="mb-4">About Our Clinic</h2>
                        <p class="mb-4">ClinicBooking is a state-of-the-art medical facility dedicated to providing exceptional healthcare services to our community. Our team of experienced medical professionals is committed to delivering personalized care with compassion and expertise.</p>
                        <p class="mb-4">We integrate the latest medical technologies with a patient-centered approach to ensure the best outcomes for those we serve. Our mission is to make quality healthcare accessible to everyone through our convenient online booking platform.</p>
                        <a href="/about.jsp" class="btn btn-primary">Learn More</a>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Doctors Section -->
        <section class="section py-5">
            <div class="container">
                <div class="text-center mb-5">
                    <h2 class="mb-3">Our Dedicated Doctors</h2>
                    <p class="text-muted">Meet our team of experienced specialists committed to your health and well-being</p>
                </div>
                
                <div class="row">
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card border-0 shadow text-center h-100">
                            <img src="assets/images/doctors/doctor-1.jpg" class="card-img-top" alt="Doctor">
                            <div class="card-body">
                                <h5 class="card-title">Dr. John Smith</h5>
                                <p class="text-muted">Cardiologist</p>
                                <div class="mt-3">
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-twitter"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle"><i class="fab fa-linkedin-in"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card border-0 shadow text-center h-100">
                            <img src="assets/images/doctors/doctor-2.jpg" class="card-img-top" alt="Doctor">
                            <div class="card-body">
                                <h5 class="card-title">Dr. Sarah Johnson</h5>
                                <p class="text-muted">Neurologist</p>
                                <div class="mt-3">
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-twitter"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle"><i class="fab fa-linkedin-in"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card border-0 shadow text-center h-100">
                            <img src="assets/images/doctors/doctor-3.jpg" class="card-img-top" alt="Doctor">
                            <div class="card-body">
                                <h5 class="card-title">Dr. Michael Chen</h5>
                                <p class="text-muted">Dermatologist</p>
                                <div class="mt-3">
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-twitter"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle"><i class="fab fa-linkedin-in"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="card border-0 shadow text-center h-100">
                            <img src="assets/images/doctors/doctor-4.jpg" class="card-img-top" alt="Doctor">
                            <div class="card-body">
                                <h5 class="card-title">Dr. Emily Wilson</h5>
                                <p class="text-muted">Pediatrician</p>
                                <div class="mt-3">
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle me-1"><i class="fab fa-twitter"></i></a>
                                    <a href="#" class="btn btn-sm btn-outline-primary rounded-circle"><i class="fab fa-linkedin-in"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Call to Action -->
        <section class="py-5" style="background: linear-gradient(135deg, #0cb8b6, #1e3a8a);">
            <div class="container text-center text-white py-4">
                <h2 class="mb-4">Ready to Book Your Appointment?</h2>
                <p class="mb-4 mx-auto" style="max-width: 600px;">Schedule your appointment today and experience quality healthcare from our expert medical team.</p>
                <a href="appointment.jsp" class="btn btn-light">Book an Appointment</a>
            </div>
        </section>
        
        <!-- Include Footer -->
        <jsp:include page="cpn/footer.jsp" />
    </body>
</html>
