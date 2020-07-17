package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface FilesDataGroup {

	Set<DataByFileType> addFileGroup(@NotNull final DataByFileType newData);

	void newFilesDataGroupSet();

}
