
$( document ).ready(function() {
	var inputCpfCnpj = $('#cnpjcpf');
	if ( inputCpfCnpj.val() != ''){
		 inputCpfCnpj.removeAttr('disabled');
	}
});

$("#cnpjcpf").blur(function(e){
    e.preventDefault();	
    if (("#id").val() == '') { 
    	validar_documento();
    }
});


function validar_documento() {
	var cnpjCpf = $("#cnpjcpf").val();
	var url="/pessoa/buscar_pessoa_documento";
	$.ajax({
        url: url,
        type: 'GET',
        data: {
        	'cnpjCpf':cnpjCpf,
        },
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
        	if (data.id=="201"){
         		$("#cnpjCpfError").show().html(data.mensagem);
        		$("#cnpjcpf").addClass('is-invalid')
        		$("#cnpjcpf").focus();
        	} else {
        		$("#cnpjCpfError").html('').hide();
        		$("#cnpjcpf").removeClass('is-invalid');
        	}
        },	
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        },
    });
	
}


$(function() {
	radioTipoPessoa = $('.js-radio-tipo-pessoa');
	labelCpfCnpj = $('[for=cnpjcpf]');
	inputCpfCnpj = $('#cnpjcpf');
	radioTipoPessoa.on('change', function(e){
		if ( $('#id_FISICA').prop('checked')==true) {
			var mascara_fisica = $('#id_FISICA').data("mascara");
			var documento_fisica = $('#id_FISICA').data("documento");
			labelCpfCnpj.text(documento_fisica);
			inputCpfCnpj.mask(mascara_fisica);	
			inputCpfCnpj.val('');
			inputCpfCnpj.removeAttr('disabled');
			$('#upload-select').removeAttr('disabled');
			$('#upload-drop').removeAttr('disabled');
			$('#salario').removeAttr('disabled');
		} 
		if ( $('#id_JURIDICA').prop('checked')==true) {
		    var mascara_juridica = $('#id_JURIDICA').data("mascara");
			var documento_juridica = $('#id_JURIDICA').data("documento");
			labelCpfCnpj.text(documento_juridica);
			inputCpfCnpj.mask(mascara_juridica);	
			inputCpfCnpj.val('');
			inputCpfCnpj.removeAttr('disabled');
			$('#upload-select').prop('disabled',true);
			$('#upload-drop').prop('disabled',true);
			$('#salario').prop('disabled',true);
		}
	}.bind(this));
});


