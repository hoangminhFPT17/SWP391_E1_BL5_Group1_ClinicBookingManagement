<%-- 
    Document   : Auth
    Created on : Apr 16, 2025, 11:26:38 PM
    Author     : ADMIN
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login & Registration</title>
        <link rel="stylesheet" href="assets/css/logincs.css"/>
        <style>
            /* Additional styles for error messages */
            .error-message {
                color: #ff3860;
                font-size: 14px;
                margin-top: 5px;
                margin-bottom: 10px;
            }
            
            .success-message {
                color: #23d160;
                font-size: 14px;
                margin-top: 5px;
                margin-bottom: 10px;
            }
            
            /* Modal styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.4);
            }

            .modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 50%;
                border-radius: 5px;
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
            }
        </style>
    </head>
    <body>

        <!-- Modal for error messages -->
        <div id="error-modal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Notification</h2>
                <p id="modal-message">
                    ${requestScope.errorLogin}${requestScope.errorRegister}${requestScope.registerComplete}${requestScope.ResetPASS}${requestScope.verificationSuccess}
                </p>
            </div>
        </div>

                <div class="container" id="container">
                    <div class="form-container register-container">
                        <form action="register" method="post">
                            <h1>Register Here</h1>
                            <input type="text" placeholder="Full Name" required name="fullname" minlength="2" maxlength="50">
                            <input type="email" placeholder="Email" required name="email" pattern="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" 
                                   title="Please enter a valid email address">
                            <input type="text" placeholder="Phone number" required name="phone" pattern="\d{10,11}" 
                                   title="Phone number must be 10-11 digits">
                            <input type="password" placeholder="Password" required name="password" minlength="6" 
                                   title="Password must be at least 6 characters">
                            <button type="submit">Register</button>
                            <span>Already have an account? <a href="#" id="showLogin">Login</a></span>
                        </form>
                    </div>

            <div class="form-container login-container">
                <form action="login" method="post">
                    <h1>Login Here</h1>
                    <input type="text" placeholder="Enter your email" required name="email">
                    <input type="password" placeholder="Password" required name="password">
                    <div class="content">
                        <div class="pass-link">
                            <a href="forgotPassword.jsp">Forgot password?</a>
                        </div>
                    </div>
                    <button>Login</button>
                    <span>Don't have an account?</span>
                </form>
            </div>

            <div class="overlay-container">
                <div class="overlay">
                    <img src="img/infi.png" alt="alt"/>
                    <div class="overlay-panel overlay-left">
                        <h1 class="title">Hello <br> friends</h1>
                        <p>If you have an account, login here and have fun</p>
                        <button class="ghost" id="showLogin">Login
                        </button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1 class="title">Start your <br> journey now</h1>
                        <p>If you don't have an account yet, join us and start your journey.</p>
                       <button class="ghost" id="showRegister">Register</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/js/login.js"></script>
        
        <!-- Add script to show modal when there's an error message -->
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                // Get the modal
                var modal = document.getElementById("error-modal");
                
                // Get the <span> element that closes the modal
                var span = document.getElementsByClassName("close")[0];
                
                // Get the error message
                var errorMessage = document.getElementById("modal-message").textContent.trim();
                
                // If there's an error message, show the modal
                if (errorMessage !== "") {
                    modal.style.display = "block";
                    
                    // Check if it's a success message
                    if(errorMessage.includes("successful") || errorMessage.includes("verified")) {
                        document.getElementById("modal-message").className = "success-message";
                    } else {
                        document.getElementById("modal-message").className = "error-message";
                    }
                }
                
                // When the user clicks on <span> (x), close the modal
                span.onclick = function() {
                    modal.style.display = "none";
                }
                
                // When the user clicks anywhere outside of the modal, close it
                window.onclick = function(event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
            });
        </script>
    </body>
</html>

