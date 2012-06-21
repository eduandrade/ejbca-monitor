<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>:: EJBCA Monitor ::</title>
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
<link
	href="<c:url value="/resources/jqueryui/1.8/themes/base/jquery.ui.all.css" />"
	rel="stylesheet" type="text/css" />

<style type="text/css">
.spinner {
	position: fixed;
	top: 50%;
	left: 50%;
	margin-left: -50px; /* half width of the spinner gif */
	margin-top: -50px; /* half height of the spinner gif */
	text-align: center;
	z-index: 1234;
	overflow: auto;
	width: 100px; /* width of the spinner gif */
	height: 102px; /*hight of the spinner gif +2px to fix IE8 issue */
}
</style>

</head>

<body>

	<br>
	<br>

	<div id="spinner" class="spinner" style="display: none;">
		<img id="img-spinner"
			src="<c:url value="/resources/images/loading.gif" />" alt="Loading"
			width="50%" />
	</div>

	<table id="tabela" border="1" class="ui-widget ui-widget-content"
		style="display: none">
		<thead>
			<tr class="ui-widget-header">
				<th></th>
				<!-- TODO i18n -->
				<th>Name</th>
				<th>Message</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>

	<script type="text/javascript"
		src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/jqueryform/2.8/jquery.form.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/jqueryui/1.8/jquery.ui.core.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/jqueryui/1.8/jquery.ui.widget.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/jqueryui/1.8/jquery.ui.tabs.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/json2.js" />"></script>

	<script>
		$(document).ready(function() {

	        $("#spinner").bind("ajaxSend", function() {
		        $(this).show();
		        //$('#tabela').fadeOut('fast');
		        $('#tabela').hide();
	        }).bind("ajaxStop", function() {
		        $(this).hide();
		        //$('#tabela').slideDown();
		        $('#tabela').fadeIn('slow');
	        }).bind("ajaxError", function() {
		        $(this).hide();
		        alert('Erro ao carregar os status das AC´s!');
	        });

	        checkStatus();
	        // set interval
	        var tid = setInterval(checkStatus, 30000);

	        function abortTimer() { // to be called when you want to stop the timer
		        clearInterval(tid);
	        }

	        function checkStatus() {
	        	$("#tabela").find("tr:gt(0)").remove(); //limpa linhas da tabela
		        $.getJSON('<c:url value="/check" />', function(data) {
			        $.each(data, function(i, item) {
				        var tr = $("<tr/>");

				        var isOk = item.allOk;
				        var imgOk = "<img src='<c:url value="/resources/messages/success.png" />' width='50%' >";
				        var imgNok = "<img src='<c:url value="/resources/messages/error.png" />' width='50%' >";
				        var imgToUse = "";

				        if (isOk == true) {
					        imgToUse = imgOk;
				        } else {
					        imgToUse = imgNok;
				        }

				        var td1 = $("<td/>").html(imgToUse);
				        var td2 = $("<td/>").html(item.name + "");
				        var td3 = $("<td/>").html(item.message + "");

				        $(tr).append(td1);
				        $(tr).append(td2);
				        $(tr).append(td3);

				        $("#tabela").append(tr);
			        });
		        });

		        //$('#tabela').show('slow');
		        //$('#tabela').fadeIn();
	        }

        });
	</script>

</body>

</html>