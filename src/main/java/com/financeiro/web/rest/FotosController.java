package com.financeiro.web.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.financeiro.model.dto.Foto;
import com.financeiro.service.storage.FotoStorage;
import com.financeiro.service.storage.FotoStorageRunnable;

@RestController
@RequestMapping(value="/fotos")
public class FotosController {

	@Autowired
	private FotoStorage fotoStorage;
	
	@RequestMapping(method=RequestMethod.POST)
	public DeferredResult<Foto> fileUpload(@RequestParam("file") MultipartFile[] file) throws IOException {
  	     DeferredResult<Foto> resultado = new DeferredResult<>();
		 if (!file[0].getOriginalFilename().isEmpty()) {
		  	 Thread thread = new Thread(new FotoStorageRunnable(file, resultado, fotoStorage));
			 thread.start();
		 }
	     return resultado;
	   }
	
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) {
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoStorage.recuperar(nome);
	}
	
}
