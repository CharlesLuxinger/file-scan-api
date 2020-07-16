package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface FilesGroupData {

	Set<DataByFileType> addFileGroup(@NotNull final DataByFileType newData);

	void newFilesGroupDataList();

}
