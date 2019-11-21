$(function() {

        var d = new Date();
        var dateYear = d.getFullYear();
        var firstDatas = 0;
        var secondDatas = 0;
        var threeDatas = 0;
        var doughnutData;
        var doughnutOptions;
        var lineData;
        var lineOptions
    $.ajax({
            url: window.origin +'/payRoll/charts',
            dataType: "json",
            type: 'POST',
            async: false,
            success: function (response) {
                console.log(response);

                for (var i in response.firstData){
                    firstDatas+=parseInt(response.firstData[i]);

                };
                for (var j in response.secondData){
                    secondDatas+=parseInt(response.secondData[j]);

                };
                for (var k in response.thirtyData){
                    threeDatas+=parseInt(response.thirtyData[k]);

                };

                doughnutData = {
                    labels: [dateYear,parseInt(dateYear)-1,parseInt(dateYear)-2],
                    datasets: [{
                        data: [firstDatas,secondDatas,threeDatas],
                        backgroundColor: ["rgb(255, 99, 132)","rgb(54, 162, 235)","rgb(255, 205, 86)"]
                    }]
                } ;


                 doughnutOptions = {
                    responsive: true,
                    legend: {
                        display: true
                    },
                };

            }
        });




    var ctx4 = document.getElementById("payYear").getContext("2d");
    new Chart(ctx4, {type: 'doughnut', data: doughnutData, options:doughnutOptions});



    $.ajax({
        url: window.origin + '/payRoll/charts',
        dataType: "json",
        type: 'POST',
        async: false,
        success: function (response) {
            console.log(response);
             lineData = {
                labels: ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "septembre", "Octobre", "Novembre", "Decembre"],
                datasets: [
                    {
                        label: dateYear,
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgba(52,152,219, 1)',
                        pointBorderColor: "#fff",
                        data:response.firstData,
                    }, {
                        label: parseInt(dateYear)-1,
                        backgroundColor: "rgb(54, 162, 235)",
                        borderColor: 'rgba(213,217,219, 1)',
                        pointBorderColor: "#fff",
                        data: response.secondData,
                    }, {
                        label: parseInt(dateYear)-2,
                        backgroundColor: "rgb(255, 205, 86)",
                        borderColor: 'rgba(213,217,219, 1)',
                        pointBorderColor: "#fff",
                        data: response.thirtyData,
                    }
                ]
            };
             lineOptions = {
                responsive: true,
                maintainAspectRatio: false
            };
        }
    });

    var ctx = document.getElementById("payMonth").getContext("2d");
    new Chart(ctx, {type: 'line', data: lineData, options:lineOptions});


});