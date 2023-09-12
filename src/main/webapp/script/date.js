function digitalClock() {
    var date = new Date();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    var months = new makeArray('Января', 'Февраля', 'Марта', 'Апреля', 'Мая', 'Июня', 'Июля', 'Августа', 'Сентября', 'Октября', 'Ноября', 'Декабря');
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var yy = date.getYear();
    var year = (yy < 1000) ? yy + 1900 : yy;

    if (hours < 10) hours = "0" + hours;
    if (minutes < 10) minutes = "0" + minutes;
    if (seconds < 10) seconds = "0" + seconds;
    document.getElementById("id_clock").innerHTML = day + " " + months[month] + " "  + year + "г."
        + " " + hours + ":" + minutes + ":" + seconds;
    setTimeout("digitalClock()", 1000);
}
function makeArray() {
    for (i = 0; i < makeArray.arguments.length; i++)
        this[i + 1] = makeArray.arguments[i];
}