package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;

import java.util.Set;

public interface GitRepositoryService {

	Set<DataByFileType> findAllFilesGroup(GitRepository repository);
}