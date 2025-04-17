<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-4 mb-5 mb-lg-0">
                <div class="mb-4">
                    <img src="assets/images/logo-light.png" alt="ClinicBooking" class="footer-logo" style="height: 45px;">
                    <div class="clinic-name text-white">Clinic<span>Booking</span></div>
                </div>
                <p>Your trusted healthcare partner dedicated to providing exceptional medical services with a focus on patient care and satisfaction.</p>
                <div class="footer-social mt-4">
                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                    <a href="#"><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
            
            <div class="col-lg-2 col-md-6 mb-5 mb-lg-0">
                <h5 class="footer-title">Quick Links</h5>
                <ul class="footer-links">
                    <li><a href="home.jsp">Home</a></li>
                    <li><a href="about.jsp">About Us</a></li>
                    <li><a href="services.jsp">Services</a></li>
                    <li><a href="doctors.jsp">Doctors</a></li>
                    <li><a href="contact.jsp">Contact Us</a></li>
                </ul>
            </div>
            
            <div class="col-lg-3 col-md-6 mb-5 mb-lg-0">
                <h5 class="footer-title">Our Services</h5>
                <ul class="footer-links">
                    <li><a href="#">General Checkup</a></li>
                    <li><a href="#">Cardiology</a></li>
                    <li><a href="#">Dental Care</a></li>
                    <li><a href="#">Neurology</a></li>
                    <li><a href="#">Orthopedics</a></li>
                </ul>
            </div>
            
            <div class="col-lg-3 col-md-6">
                <h5 class="footer-title">Contact Info</h5>
                <ul class="footer-links">
                    <li><i class="fas fa-map-marker-alt me-2"></i> 123 Medical Center Drive, City</li>
                    <li><i class="fas fa-phone-alt me-2"></i> +1 (800) 123-4567</li>
                    <li><i class="fas fa-envelope me-2"></i> info@clinicbooking.com</li>
                    <li><i class="fas fa-clock me-2"></i> Mon-Fri: 8:00AM - 8:00PM</li>
                </ul>
            </div>
        </div>
    </div>
    
    <div class="footer-bottom">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6 text-center text-md-start">
                    <div class="copyright">
                        &copy; <script>document.write(new Date().getFullYear())</script> ClinicBooking. All Rights Reserved.
                    </div>
                </div>
                <div class="col-md-6 text-center text-md-end mt-3 mt-md-0">
                    <div class="footer-nav">
                        <a href="#">Privacy Policy</a>
                        <a href="#">Terms of Service</a>
                        <a href="#">FAQ</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- Back to top -->
<a href="#" id="back-to-top" class="btn btn-primary rounded-circle position-fixed bottom-0 end-0 m-4" style="display: none; z-index: 1000;">
    <i class="fas fa-arrow-up"></i>
</a>

<!-- JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Back to top button
    window.addEventListener('scroll', function() {
        const backToTop = document.getElementById('back-to-top');
        if (window.pageYOffset > 300) {
            backToTop.style.display = 'block';
        } else {
            backToTop.style.display = 'none';
        }
    });
    
    document.getElementById('back-to-top').addEventListener('click', function(e) {
        e.preventDefault();
        window.scrollTo({top: 0, behavior: 'smooth'});
    });
    
    // Mobile menu toggle
    const mobileToggle = document.querySelector('.mobile-toggle');
    if (mobileToggle) {
        mobileToggle.addEventListener('click', function() {
            // Here you would toggle the mobile menu
            alert('Mobile menu toggled (functionality to be implemented)');
        });
    }
</script>
