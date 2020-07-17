package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilesDataGroupImplTest {

	private FilesDataGroupImpl filesGroupData;

	@BeforeEach
	public void setUp(){
		filesGroupData = new FilesDataGroupImpl();
	}

	@Test
	@DisplayName("when add a file type that not existed should only add")
	public void when_add_a_file_type_that_not_existed_should_only_add_addFileGroup(){
		filesGroupData.newFilesDataGroupSet();
		var newData = new DataByFileType("java", 4, 7);
		var oldData = new DataByFileType("go", 9, 4);

		filesGroupData.addFileGroup(oldData);
		filesGroupData.addFileGroup(newData);

		var groupData = filesGroupData.filesGroupData;

		groupData.parallelStream()
				 .filter(newData::equals)
				 .findFirst()
				 .ifPresent(data -> assertThat(data).isEqualToComparingFieldByField(newData));
	}

	@Test
	@DisplayName("when add a file type that not existed sum values add")
	public void when_add_a_file_type_that_existed_should_sum_values_addFileGroup(){
		filesGroupData.newFilesDataGroupSet();
		var newData = new DataByFileType("java", 4, 7);
		var oldData = new DataByFileType("java", 9, 4);

		filesGroupData.addFileGroup(oldData);
		filesGroupData.addFileGroup(newData);

		var groupData = filesGroupData.filesGroupData;

		groupData.parallelStream()
				.filter(newData::equals)
				.findFirst()
				.ifPresent(data -> {
					assertEquals(oldData.getLines(), 13);
					assertEquals(oldData.getBytes(), 11);
				});
	}
}