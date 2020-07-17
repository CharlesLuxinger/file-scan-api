package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.synchronizedSet;

@Service
@Validated
public class FilesDataGroupImpl implements FilesDataGroup {

	protected Set<DataByFileType> filesGroupData;

	public Set<DataByFileType> addFileGroup(@NotNull final DataByFileType newData) {
		if (filesGroupData.contains(newData)){
			filesGroupData.parallelStream()
						  .filter(newData::equals)
						  .findFirst()
						  .ifPresent(oldData -> oldData.sumValues(newData));

			return filesGroupData;
		}

		filesGroupData.add(newData);

		return filesGroupData;
	}

	public void newFilesDataGroupSet(){
		filesGroupData  = synchronizedSet(new HashSet<>());
	}
}
