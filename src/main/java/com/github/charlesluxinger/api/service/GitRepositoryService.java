package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface GitRepositoryService {

	Set<DataByFileType> findAllFilesGroup(@Valid @NotNull final GitRepository repository);
}