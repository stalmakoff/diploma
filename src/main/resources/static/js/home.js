$(document).ready(function () {

  // parallax library
  var scene = document.getElementById('scene');
  var parallax = new Parallax(scene);

  // sticky header
  var $header = $('header');
  var $sticky = $header.before($header.clone().addClass("sticky"));

  $(window).on("scroll", function () {
    var scrollFromTop = $(window).scrollTop();
    $("body").toggleClass("scroll", (scrollFromTop > 350));
  });

  // smooth scroll
  $('.menu li a[href^="#"]').on('click', function (e) {
    e.preventDefault();

    var target = $(this.hash);

    if (target.length) {
      $('html, body').stop().animate({
        scrollTop: target.offset().top - 70
      }, 1000);
    }

  });




});