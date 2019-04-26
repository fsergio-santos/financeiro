/**
 * 
 */

$(function(){
	$('#dataNascimento').mask('00/00/0000');
	$('#dataNascimento').datepicker({
		orientation: 'bottom',
		language: 'pt-BR',
		autoclose: true,
		todayHighlight: true
	});
});
