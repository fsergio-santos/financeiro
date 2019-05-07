package com.financeiro.web.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.financeiro.security.UsuarioSistema;
import com.financeiro.service.exception.IdNaoValidoServiceException;
import com.financeiro.service.exception.NaoExisteDaoException;
import com.financeiro.service.exception.PessoaInexistenteOuInativaException;
import com.financeiro.web.error.MessageErrors;

@ControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {

	/*@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> idInvalido(IdNaoValidoServiceException ex, WebRequest request) {

		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("Id de identificação não existe!").addErro(ex.getMessage())
						.addStatus(HttpStatus.BAD_REQUEST).addHttpMethod(getHttpMethod(request))
						.addPath(getPath(request)).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ org.hibernate.exception.ConstraintViolationException.class })
	public ResponseEntity<Object> constraintViolada(org.hibernate.exception.ConstraintViolationException ex,
			WebRequest request) {

		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("Constraint violada: " + ex.getConstraintName())
						.addErro(ex.getMessage()).addStatus(HttpStatus.CONFLICT).addHttpMethod(getHttpMethod(request))
						.addPath(getPath(request)).build(),
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ org.hibernate.PropertyValueException.class })
	public ResponseEntity<Object> propriedadeNula(org.hibernate.PropertyValueException ex, WebRequest request) {

		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("O atributo '" + ex.getPropertyName() + "' não pode ser nulo.")
						.addErro(ex.getMessage()).addStatus(HttpStatus.BAD_REQUEST)
						.addHttpMethod(getHttpMethod(request)).addPath(getPath(request)).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ NaoExisteDaoException.class })
	public ResponseEntity<Object> entidadeNaoEncontrada(NaoExisteDaoException ex, WebRequest request) {

		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("Recurso não encontrado na base de dados.").addErro(ex.getMessage())
						.addStatus(HttpStatus.NOT_FOUND).addHttpMethod(getHttpMethod(request)).addPath(getPath(request))
						.build(),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class })
	public ResponseEntity<Object> serverException(RuntimeException ex, WebRequest request) {

		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("Um exceção foi lançada.").addErro(ex.getMessage())
						.addStatus(HttpStatus.INTERNAL_SERVER_ERROR).addHttpMethod(getHttpMethod(request))
						.addPath(getPath(request)).build(),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex,
			WebRequest request) {
		return handleExceptionInternal(ex,
				MessageErrors.builder().addDetalhe("Id de identificação não existe!").addErro(ex.getMessage())
						.addStatus(HttpStatus.BAD_REQUEST).addHttpMethod(getHttpMethod(request))
						.addPath(getPath(request)).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURI();
	}

	private String getHttpMethod(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getMethod();
	}*/

	@ModelAttribute("usuario_logado")
	public UsuarioSistema getCurrentUser(@AuthenticationPrincipal UsuarioSistema usuario_logado) {
		return (usuario_logado == null) ? null : usuario_logado;
	}
}
