function openTab(tabValue, element, color) {
    // Скрыть все элементы с помощью class="tabcontent" по умолчанию */
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }


    // Удалите цвет фона всех ссылок/кнопок вкладок
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }

    // Показать содержимое конкретной вкладки
    document.getElementById(tabValue).style.display = "block";

    // Добавить конкретный цвет кнопки, используемой для открытия содержимого вкладки
    element.style.backgroundColor = color;
}

// Получить элемент с помощью id="defaultOpen" и нажмите на нее
document.getElementById("defaultOpen").click();

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Закройте выпадающее меню, если пользователь щелкает за его пределами
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}