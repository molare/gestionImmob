$(function() {
// Bar Chart example
    var barData;
    var barOptions;
    var d = new Date();
    var dateYear = d.getFullYear();

    $.ajax({
        url: window.origin + '/payRoll/charts',
        dataType: "json",
        type: 'POST',
        async: false,
        success: function (response) {
            console.log(response);
             barData = {
                labels: ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "septembre", "Octobre", "Novembre", "Decembre"],
                datasets: [
                    {
                        label: dateYear,
                        backgroundColor: '#2ecc71',
                        data: response.firstData
                    },
                    {
                        label: parseInt(dateYear)-1,
                        backgroundColor: '#F39C12',
                        borderColor: "#fff",
                        data: response.secondData
                    },
                    {
                        label: parseInt(dateYear)-2,
                        backgroundColor: '#DADDE0',
                        borderColor: "#fff",
                        data: response.thirtyData
                    }

                ]
            };
             barOptions = {
                responsive: true,
                maintainAspectRatio: false
            };
        }
    });
    var ctx = document.getElementById("payMonthBar").getContext("2d");
    new Chart(ctx, {type: 'bar', data: barData, options:barOptions});
});
