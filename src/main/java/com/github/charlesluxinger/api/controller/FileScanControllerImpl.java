package com.github.charlesluxinger.api.controller;

import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;
import com.github.charlesluxinger.api.service.GitRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class FileScanControllerImpl implements FileScanController{

	private final GitRepositoryService gitRepositoryService;

	@PostMapping(value = "/repository", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public Set<DataByFileType> gitRepository(@RequestBody @Valid final GitRepository repository) {
		return gitRepositoryService.findAllFilesGroup(repository);
	}

}