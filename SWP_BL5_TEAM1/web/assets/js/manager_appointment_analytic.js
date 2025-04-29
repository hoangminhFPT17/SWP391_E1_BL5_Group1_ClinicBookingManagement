(function () {
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {
        'packages': ['corechart', 'bar']
    });

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    console.log("JS script is running");

    // Callback that creates and populates a data table, and draws the chart.
    function drawChart() {
        drawMonthlyAppointmentColumnChart();
    }

    function drawMonthlyAppointmentColumnChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Month');
        data.addColumn('number', 'Appointments');

        // Populate the data table
        for (var month = 1; month <= 12; month++) {
            var monthName = getMonthName(month);
            var count = monthlyAppointmentCountsMap[month] || 0;
            data.addRow([monthName, count]);
        }

        var options = {
            title: "Monthly Appointment Counts",
            chartArea: {width: '60%'},
            hAxis: {
                title: 'Month',
                minValue: 0
            },
            vAxis: {
                title: 'Appointments'
            },
            bars: 'vertical', // Vertical bars
            width: '100%',
            height: 400
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('columnchart'));
        chart.draw(data, options);
    }

    // Helper function to map month numbers to names
    function getMonthName(monthNumber) {
        const monthNames = [
            "", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ];
        return monthNames[monthNumber];
    }
})();


