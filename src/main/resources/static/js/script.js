const toggleBtn = document.querySelector("#toggle-btn");
const sidebar = document.querySelector("#sidebar");

toggleBtn.addEventListener("click", function() {
    if (sidebar.classList.contains("expand")) {
        sidebar.classList.remove("expand");
    } else {
        sidebar.classList.add("expand");
    }
});

/* Toggle a Class "expand" to #sidebar */
/*
const toggleBtn =  document.querySelector("#toggle-btn");

toggleBtn.addEventListener("click", function() {
    document.querySelector("#sidebar").classList.toggle("expand");
});*/
