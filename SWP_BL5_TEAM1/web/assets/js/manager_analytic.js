(function () {
    // Load the Visualization API and the corechart package.
    google.charts.load('current',
            {
                'packages': ['corechart', 'bar']
            });

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    console.log("js script is runing");
    // Callback that creates and populates a data table, and draws the chart.
    function drawChart() {
        drawPieChart();
        drawColumnChart();
        drawDemographicsChart();
    }

    function drawPieChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Time Slot');
        data.addColumn('number', 'Bookings');

        for (var slotName in timeSlotBookedCountMap) {
            data.addRow([slotName, timeSlotBookedCountMap[slotName]]);
        }

        var options = {
            title: 'Most Booked Time Slots',
            pieHole: 0.4,
            width: '100%', // Ensure chart width is 100% of the container
            height: '100%' // Ensure chart height is 100% of the container
        };
        console.log("slotName" + slotName + " and number " + timeSlotBookedCountMap[slotName]);
        var pieChart = new google.visualization.PieChart(document.getElementById('piechart'));

        pieChart.draw(data, options);
    }

    function drawColumnChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Doctor');
        data.addColumn('number', 'Appointments');

        // Populate the data table
        appointmentCountByDoctor.forEach(function (item) {
            data.addRow([item.doctor_name, item.appointment_count]);
        });

        var options = {
            title: "Doctor with the most appointments",
            chartArea: {width: '60%'},
            hAxis: {
                title: 'Appointments',
                minValue: 0
            },
            vAxis: {
                title: 'Doctor'
            }
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('columnchart'));
        chart.draw(data, options);
    }
    
    function drawDemographicsChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Age Range');
        data.addColumn('number', 'Patient Count');
        
        // Populate the chart with demographics data
        for (var category in demographics) {
            data.addRow([category, demographics[category]]);
        }

        var options = {
            title: 'Patient Age Demographics',
            chartArea: {width: '60%'},
            hAxis: {
                title: 'Patient Count',
                minValue: 0
            },
            vAxis: {
                title: 'Age Range'
            }
        };

        var chart = new google.visualization.BarChart(document.getElementById('demographicsChart'));
        chart.draw(data, options);
    }
})();

