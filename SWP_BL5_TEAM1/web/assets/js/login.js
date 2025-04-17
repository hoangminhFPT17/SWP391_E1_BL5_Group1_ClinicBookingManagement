/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", () => {
    const registerButton = document.getElementById("showRegister");
    const loginButtons = document.querySelectorAll("#showLogin"); // Chọn tất cả nút có id này
    const container = document.getElementById("container");

    if (!container) {
        console.error("Container element not found!");
        return;
    }

    if (registerButton) {
        registerButton.addEventListener("click", () => {
            console.log("Register button clicked");
            container.classList.add("right-panel-active");
        });
    } else {
        console.error("Register button not found");
    }

    if (loginButtons.length > 0) {
        loginButtons.forEach(button => {
            button.addEventListener("click", (event) => {
                event.preventDefault(); // Ngăn chặn chuyển trang do thẻ <a>
                console.log("Login button clicked");
                container.classList.remove("right-panel-active");
            });
        });
    } else {
        console.error("Login button not found");
    }
});


