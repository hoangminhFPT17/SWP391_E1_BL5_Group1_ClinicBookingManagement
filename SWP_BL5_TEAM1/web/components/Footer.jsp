<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<footer class="bg-dark text-white py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h5>Clinic Management System</h5>
                <p>Providing quality healthcare services</p>
            </div>
            <div class="col-md-4">
                <h5>Contact</h5>
                <address>
                    <p>Email: contact@clinic.com</p>
                    <p>Phone: +84 123 456 789</p>
                    <p>Address: 123 Healthcare St., Hanoi, Vietnam</p>
                </address>
            </div>
            <div class="col-md-4">
                <h5>Quick Links</h5>
                <ul class="list-unstyled">
                    <li><a href="about" class="text-white">About Us</a></li>
                    <li><a href="services" class="text-white">Services</a></li>
                    <li><a href="doctors" class="text-white">Doctors</a></li>
                    <li><a href="contact" class="text-white">Contact Us</a></li>
                </ul>
            </div>
        </div>
        <hr>
        <div class="text-center">
            <p>© <%= java.time.Year.now().getValue() %> Clinic Management System. All rights reserved.</p>
        </div>
    </div>
</footer>
