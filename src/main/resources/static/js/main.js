
//
//
// document.getElementById('formDate').valueAsDate = new Date();
//
// function getCurrentDate() {
//     var date = new Date();
//     var year = date.getFullYear();
//     var day = date.getDate();
//     var month = date.getMonth() + 1;
//     return "0" + day + "/" + 0 + month + "/" + year;
//
// };
window.onload = function today(){
    var date = new Date();

    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;

    var today = year + "-" + month + "-" + day;
    var firstDay = year + "-" + month + "-" + "01";
    var dateToday = document.getElementById("formDate");
    if ( dateToday !== null) {
        dateToday.value = today;
    }

    var dateTo = document.getElementById("dateTo");
    if ( dateTo !== null) {
        dateTo.value = today;
    }

    var dateFirstDay = document.getElementById("dateFrom");
    if ( dateFirstDay !== null) {
        dateFirstDay.value = firstDay;
    }

};
