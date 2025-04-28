<%-- 
    Document   : managerAnalytic
    Created on : 28 Apr 2025, 00:56:45
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Most Booked Time Slots</title>

    <!-- Load Google Charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Time Slot', 'Bookings'],
                <c:forEach var="timeSlot" items="${timeSlotBookingCounts}">
                    ['${timeSlot.name}', ${timeSlot.bookingCount}]<c:if test="${!timeSlotBookingCounts[timeSlotBookingCounts.size()-1].equals(timeSlot)}">,</c:if>
                </c:forEach>
            ]);

            var options = {
                title: '',
                pieHole: 0.4,
                legend: { position: 'bottom' },
                chartArea: {width: '90%', height: '75%'},
                pieSliceText: 'percentage',
                slices: {
                    0: {color: '#4e73df'},
                    1: {color: '#1cc88a'},
                    2: {color: '#36b9cc'},
                    3: {color: '#f6c23e'},
                    4: {color: '#e74a3b'}
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('rchart-1'));
            chart.draw(data, options);
        }
    </script>
</head>

<body>
    <div class="container mt-5">
        <div class="card border-0 rounded shadow">
            <div class="d-flex justify-content-between px-4 pt-4">
                <h6 class="align-items-center mb-0">
                    Most Booked Time Slots
                </h6>
                <i class="ri-pie-chart-2-line text-primary h5"></i> <!-- You can change the icon -->
            </div>

            <!-- Chart -->
            <div id="rchart-1" style="height: 350px;"></div> <!-- Control height here -->
        </div>
    </div>
</body>
</html>

