const toggleBtn =  document.querySelector("#toggle-btn");

toggleBtn.addEventListener("click", function() {
    document.querySelector("#sidebar").classList.toggle("expand");
});

/*document.addEventListener("DOMContentLoaded", function() {
    const toggleBtn = document.querySelector("#toggle-btn");
    const sidebar = document.querySelector("#sidebar");

    if (toggleBtn) {
        toggleBtn.addEventListener("click", function() {
        toggleBtn.addEventListener("click", function() {
            sidebar.classList.toggle("expand");
        });
    }
});*/

//const toggleBtn = document.querySelector("#toggle-btn");
//const sidebar = document.querySelector("#sidebar");
//
//toggleBtn.addEventListener("click", function() {
//    if (sidebar.classList.contains("expand")) {
//        sidebar.classList.remove("expand");
//    } else {
//        sidebar.classList.add("expand");
//    }
//});

