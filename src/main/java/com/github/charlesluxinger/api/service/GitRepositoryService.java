package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepositoryRequest;
import com.github.charlesluxinger.api.validator.GitHubUrl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface GitRepositoryService {

	Set<DataByFileType> findAllFilesGroup(@GitHubUrl final String repository);
}