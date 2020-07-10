package com.github.charlesluxinger.api.controller;

import com.github.charlesluxinger.api.model.GitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class FileScanControllerImpl implements FileScanController{

	@PostMapping(value = "/repository", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity gitRepository(@RequestBody @Valid GitRepository repository) {
		return ResponseEntity.ok(repository);
	}

}