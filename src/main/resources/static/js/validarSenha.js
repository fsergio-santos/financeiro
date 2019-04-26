/**
 * Rotina para Verificação de Senhas
 */

var msg = [ 'não é aceitável', 'muito fácil', 'fácil', 'padrão', 'muito bom','senha forte.' ];
var desc = [ '0%', '20%', '40%', '60%', '80%', '100%' ];
var descClass = [ '', 'bg-danger', 'bg-danger', 'bg-warning', 'bg-success','bg-success' ];

var visivel = 0;

$(document).ready(
		function() {

			var password	= $("#password");
			var contraSenha = $("#contraSenha");
			var email 		= $("#email");
			var progresso1  = $("#progress1");
			var progresso2 	= $("#progress2");
			var emailError 	= $("#emailError");

			if (visivel == 0) {
				visivel = 1;
				progresso1.hide();
				progresso2.hide();
			}

			email.blur(function() {
				if ($.trim(email.val()).length == 0) {
					emailError.show().html("Digite um endereço de E-mail válido.");
					e.preventDefault();
					email.focus();
					return false;
				}
				if (IsEmail(email) == false) {
					email.addClass('is-invalid');
					emailError.show().html("Endereço de E-mail inválido.");
					email.focus();
					return false;
				}
				emailError.html('').hide();
				email.removeClass('is-invalid');
				progresso1.show();
			});

			password.blur(function() {
				progresso2.show();
			});

			password.keyup(function() {
				passwordStrength($(this).val());
			});

			contraSenha.keyup(function() {
				contraSenhaStrength($(this).val());
			});

			contraSenha.blur(function(e) {
				verificarSenhas();
			});

		});

function IsEmail(email) {
	var regex = new RegExp(
			/^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i);
	if (!regex.test($.trim(email.val()))) {
		return false;
	} else {
		return true;
	}
}

function verificarSenhas() {

	var inputPassword = $("#password");
	var inputContraSenha = $("#contraSenha");
	var passwordError = $("#passwordError");
	var contraSenhaError = $("#contraSenhaError");

	if (inputPassword.val() != inputContraSenha.val()) {
		inputPassword.addClass('is-invalid');
		inputContraSenha.addClass('is-invalid');
		passwordError.show().html('As senhas estão diferenres.');
		contraSenhaError.show().html('As senhas estão diferentes. ');
		inputContraSenha.focus();
	} else {
		inputPassword.removeClass('is-invalid');
		inputContraSenha.removeClass('is-invalid');
		passwordError.html('').hide();
		contraSenhaError.html('').hide();
	}
}

function passwordStrength(password) {

	var score = 0;
	if (password.length > 6)
		score++;
	if ((password.match(/[a-z]/)) && (password.match(/[A-Z]/)))
		score++;
	if (password.match(/\d+/))
		score++;
	if (password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/))
		score++;
	if (password.length > 10)
		score++;
	$(".js-passwordTamanho").removeClass(descClass[score - 1]).addClass(
			descClass[score]).css("width", desc[score]);
	$("#password_text").html(msg[score]);

}

function contraSenhaStrength(password) {
	var score = 0;
	if (password.length > 6)
		score++;
	if ((password.match(/[a-z]/)) && (password.match(/[A-Z]/)))
		score++;
	if (password.match(/\d+/))
		score++;
	if (password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/))
		score++;
	if (password.length > 10)
		score++;

	$(".js-contraSenhaTamanho").removeClass(descClass[score - 1]).addClass(
			descClass[score]).css("width", desc[score]);
	$("#contraSenha_text").html(msg[score]);

}