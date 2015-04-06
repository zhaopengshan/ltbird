// JavaScript Document
 function ShowDIV(thisObjID) {
          $("#BgDiv").css({ display: "block", height: $(document).height() });
          var yscroll = document.documentElement.scrollTop;
          $("#" + thisObjID ).css("display", "block");
		  $("#DivShim").css({"display": "block","z-index":"98", height: $(document).height(),width:$(document).width()});
  		  document.documentElement.scrollTop = 0;
      }

      function closeDiv(thisObjID) {
          $("#BgDiv").css("display", "none");
          $("#" + thisObjID).css("display", "none");
          $("#DivShim").css("display", "none");
      }
	 function SecDIV(thisObjID) {
          $("#secDiv").css({ display: "block", height: $(document).height() });	  
          var yscroll = document.documentElement.scrollTop;
          $("#" + thisObjID ).css("display", "block");
		  $("#DivShim").css({"display": "block","z-index":"98", height: $(document).height(),width:$(document).width()});
  		  document.documentElement.scrollTop = 0;
      }
      function CloseSec(thisObjID) {
          $("#secDiv").css("display", "none");
          $("#" + thisObjID).css("display", "none");
		  $("#DivShim").css("display", "none");
      }