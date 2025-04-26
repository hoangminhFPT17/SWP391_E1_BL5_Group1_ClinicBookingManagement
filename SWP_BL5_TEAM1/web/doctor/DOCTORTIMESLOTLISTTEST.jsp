<%-- 
    Document   : DOCTORTIMESLOTLISTTEST
    Created on : 26 Apr 2025, 09:17:46
    Author     : LENOVO
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <form id="doctorTimeSlotForm">
            <table class="table table-bordered text-center">
                <thead>
                    <tr>
                        <th>Time Slot</th>
                            <c:forEach var="day" items="${['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']}">
                            <th>${day}</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="slot" items="${timeSlotList}">
                        <tr>
                            <td>${slot.name}</td>
                            <c:forEach var="day" items="${['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']}">
                                <td>
                                    <input 
                                        type="checkbox"
                                        class="time-slot-checkbox"
                                        data-slot-id="${slot.slotId}"
                                        data-day="${day}"
                                        <c:if test="${doctorSlotMap[day] != null && doctorSlotMap[day].contains(slot.slotId)}">checked</c:if>
                                            />
                                    </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>

        <script>
            document.querySelectorAll('.time-slot-checkbox').forEach(checkbox => {
                checkbox.addEventListener('change', function () {
                    const slotId = this.getAttribute('data-slot-id');
                    const day = this.getAttribute('data-day');
                    const isChecked = this.checked ? 1 : 0; // 1 = working, 0 = not working

                    console.log("Slot ID: " + slotId + ", Day: " + day + ", Checked: " + isChecked);

                    fetch('UpdateDoctorTimeSlotServlet', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: new URLSearchParams({
                            slotId: slotId,
                            dayOfWeek: day,
                            isWorking: isChecked
                        })
                    })
                            .then(response => response.json())
                            .then(data => {
                                if (data.success) {
                                    console.log('Updated successfully!');
                                } else {
                                    alert('Failed to update schedule.');
                                }
                            })
                            .catch(err => {
                                console.error('Error updating schedule:', err);
                            });
                });
            });
        </script>

    </body>
</html>
