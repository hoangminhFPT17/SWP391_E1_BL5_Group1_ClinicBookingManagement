// <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
// <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
// <!DOCTYPE html>
// <html>
// <head>
//     <meta charset="UTF-8">
//     <title>Patient Queue Manager</title>
//     <style>
//         table {
//             width: 80%;
//             margin: 20px auto;
//             border-collapse: collapse;
//         }
//         th, td {
//             padding: 10px;
//             border: 1px solid #ddd;
//             text-align: center;
//         }
//         th {
//             background-color: #f2f2f2;
//         }
//         button {
//             padding: 10px 20px;
//             background-color: #4CAF50;
//             color: white;
//             border: none;
//             cursor: pointer;
//         }
//         button:hover {
//             background-color: #45a049;
//         }
//     </style>
// </head>
// <body>
//     <h1 style="text-align: center;">Patient Queue Manager</h1>

//     <form method="POST" action="patientQueueManager">
//         <table>
//             <thead>
//                 <tr>
//                     <th>Patient Name</th>
//                     <th>Appointment Time</th>
//                     <th>Priority</th>
//                 </tr>
//             </thead>
//             <tbody>
//                 <c:forEach var="appointment" items="${activeAppointments}" varStatus="loop">
//                     <tr>
//                         <td>${appointment.patientName}</td>
//                         <td>${appointment.appointmentTime}</td>
//                         <td>
//                             <input type="hidden" name="id" value="${appointment.id}"/>
//                             <input type="number" name="priority" value="${appointment.priority}" min="1"/>
//                         </td>
//                     </tr>
//                 </c:forEach>
//             </tbody>
//         </table>
//         <div style="text-align: center;">
//             <button type="submit">Update Queue</button>
//         </div>
//     </form>
// </body>
// </html>
// <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
// <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
// <!DOCTYPE html>
// <html>
// <head>
//     <meta charset="UTF-8">
//     <title>Patient Queue Manager</title>
//     <style>
//         table {
//             width: 80%;
//             margin: 20px auto;
//             border-collapse: collapse;
//         }
//         th, td {
//             padding: 10px;
//             border: 1px solid #ddd;
//             text-align: center;
//         }
//         th {
//             background-color: #f2f2f2;
//         }
//         button {
//             padding: 10px 20px;
//             background-color: #4CAF50;
//             color: white;
//             border: none;
//             cursor: pointer;
//         }
//         button:hover {
//             background-color: #45a049;
//         }
//     </style>
// </head>
// <body>
//     <h1 style="text-align: center;">Patient Queue Manager</h1>

//     <form method="POST" action="patientQueueManager">
//         <table>
//             <thead>
//                 <tr>
//                     <th>Patient Name</th>
//                     <th>Appointment Time</th>
//                     <th>Priority</th>
//                 </tr>
//             </thead>
//             <tbody>
//                 <c:forEach var="appointment" items="${activeAppointments}" varStatus="loop">
//                     <tr>
//                         <td>${appointment.patientName}</td>
//                         <td>${appointment.appointmentTime}</td>
//                         <td>
//                             <input type="hidden" name="id" value="${appointment.id}"/>
//                             <input type="number" name="priority" value="${appointment.priority}" min="1"/>
//                         </td>
//                     </tr>
//                 </c:forEach>
//             </tbody>
//         </table>
//         <div style="text-align: center;">
//             <button type="submit">Update Queue</button>
//         </div>
//     </form>
// </body>
// </html>