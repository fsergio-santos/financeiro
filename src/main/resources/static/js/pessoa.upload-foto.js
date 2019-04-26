$(document).ready(function(){
	if ( $('#foto').val() != '' ){
		 showFotoPessoa();
		 
 	} else {
		$(document).on('change','#Fileinput',function(e){
			e.preventDefault();
			var url="/fotos";
		    ajaxFormSubmit(url,'#Ajaxform',function(output){
				var data=JSON.parse(output);
		    })	
		});	
	}
});

function showFotoPessoa(){
	var fileInput = $('.js-remove-botao');
	var showHtmlFotoPessoaTemplate = $('#show-foto-pessoa').html();
	var templates = Handlebars.compile(showHtmlFotoPessoaTemplate);
	var showHtmlFotoPessoa = templates({nomeFoto:$('#foto').val()});
	var containerFotoPessoa = $('.js-img_preview');
	fileInput.hide();
	containerFotoPessoa.addClass('img_preview');
    containerFotoPessoa.append(showHtmlFotoPessoa);
    $('.js-remove-show-foto').on('click', function() {
 		$('.js-show-foto-pessoa').remove();
 		containerFotoPessoa.removeClass('img_preview');
 		fileInput.show();
 		$('#foto').val('');
 		$('#contentType').val('');
		$("#Fileinput").val('');
 	});
}

function fechaLinkUpload(){
	if ( $("#showImage").lenght ) {
		 $("#showImage").addClass=("disabledLink");
	}
}

function ajaxFormSubmit(url, form, callback) {
    var formData = new FormData($(form)[0]);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        datatype: 'json',
        beforeSend: function() {
        },
        success: function(data) {
        	var inputNomeFoto = $('input[name=foto]');
        	var inputContentType = $('input[name=contentType]');
        	var fileInput = $('.js-remove-botao');
        	
        	inputNomeFoto.val(data.nome);
        	inputContentType.val(data.contentType);
        	
        	fileInput.hide();
      
        	var htmlFotoPessoaTemplate = $('#foto-pessoa').html();
        	var template = Handlebars.compile(htmlFotoPessoaTemplate);
        	var htmlFotoPessoa = template({nomeFoto:data.nome});
        	var containerFotoPessoa = $('.js-img_preview');
        	containerFotoPessoa.addClass('img_preview');
            containerFotoPessoa.append(htmlFotoPessoa);
            
            $('.js-remove-foto').on('click', function() {
        		$('.js-foto-pessoa').remove();
        		containerFotoPessoa.removeClass('img_preview');
        		fileInput.show();
       			inputNomeFoto.val('');
       			inputContentType.val('');
       			$("#Fileinput").val('');
        	});
        },
 
        complete: function() {
            // success alerts
        },
 
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        },
        cache: false,
        contentType: false,
        processData: false
 
    });
 
}