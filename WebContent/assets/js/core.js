/*
--------------------------------------------------------------
  Template Name: Gappa - Chat & Discussion Plateform Template
  File: Core JS File
--------------------------------------------------------------
 */
"use strict";
$(document).ready(function() {
    /* -- Show User Info -- */
    $("#view-user-info").on("click", function(e) {
        e.preventDefault();
        $(".chat-user-info").addClass("show");
        $(".chat-bottom").addClass("small");
    });
    /* -- Close User Info -- */
    $("#close-user-info").on("click", function(e) {
        e.preventDefault();
        $(".chat-user-info").removeClass("show");
        $(".chat-bottom").removeClass("small");
    });
    /* -- Collapse Chat Rightbar -- */
    $(".chat-userlist .nav-link").on("click", function(e) {
        e.preventDefault();
        $(".chat-rightbar").addClass("show");
    });
    /* -- Back Chat Rightbar -- */
    $(".back-arrow").on("click", function(e) {
        e.preventDefault();
        $(".chat-rightbar").removeClass("show");
    });
    /* -- User Media Slider -- */
    $('.user-media-slider').slick({
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 1,
        autoplay: true,
        prevArrow: '<i class="feather icon-chevron-left"></i>',
        nextArrow: '<i class="feather icon-chevron-right"></i>'
    });
    /* -- Chat Left Body Scroll -- */
    $('.chat-left-body').slimscroll({
        height: '710',
        position: 'right',
        size: "5px",
        color: 'rgba(0,0,0, 0.1)',
    });
    /* -- Chat Body Scroll -- */
    $('.chat-body').slimscroll({
        height: '800',
        position: 'right',
        size: "5px",
        color: 'rgba(0,0,0, 0.1)',
    });
    /* -- Chat Add Users Scroll -- */
    $('.add-users-list').slimscroll({
        height: '350',
        position: 'right',
        size: "5px",
        color: 'rgba(0,0,0, 0.1)',
    });
    /* -- Chat Profile Pic Upload -- */    
    var readURL = function(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('.profile-pic').attr('src', e.target.result);
            }    
            reader.readAsDataURL(input.files[0]);
        }
    }
    $(".profile-upload").on('change', function(){
        readURL(this);
    });    
    $(".upload-button").on('click', function() {
       $(".profile-upload").click();
    });
    /* -- Bootstrap Popover -- */
    $('[data-toggle="popover"]').popover();
    /* -- Bootstrap Tooltip -- */
    $('[data-toggle="tooltip"]').tooltip();
});