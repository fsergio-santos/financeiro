
$(function() {
	inputCep = $('.js-cep');
    inputCep.mask('00.000-000');
});

$(function(){
	
	var configuraMascaraTelefone = function (val) {
	    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
	},
	
	opcao = {
	    onKeyPress: function(val, e, field, options) {
	        field.mask(configuraMascaraTelefone.apply({}, arguments), options);
	    }
	};

	$('.js-numeroTelefone').mask(configuraMascaraTelefone, opcao);
	
});
