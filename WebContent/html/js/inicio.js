function desplegarMenu() {
    document.getElementById("menudes").classList.toggle("show");
}

window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
      var menudes = document.getElementsByClassName("menucontenido");
      var i;
      for (i = 0; i < menudes.length; i++) {
        var openmenudes = menudes[i];
        if (openmenudes.classList.contains('show')) {
          openmenudes.classList.remove('show');
        }
      }
    }
  }