function toggleSubMenu(serviceId) {
    var submenu = document.getElementById(serviceId);
    if (submenu.style.display === "none") {
        submenu.style.display = "block";
    } else {
        submenu.style.display = "none";
    }
}