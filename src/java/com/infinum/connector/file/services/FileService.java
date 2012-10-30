package com.infinum.connector.file.services;

import java.util.List;

import com.infinum.connector.file.api.FileForm;
import com.infinum.connector.file.api.FileListFilter;
import com.infinum.connector.file.domain.FileMetaData;

/**
 * Abstracts access to the FileSystemServices.  
 * Stores metadata in the database.
 *
 */
public interface FileService {

	FileMetaData createFile(FileForm fileForm);

	boolean deleteFile(long fileMetaDataId);

	List<FileMetaData> listFiles(FileListFilter fileListFilter);

	FileMetaData viewFileMetaData(long fileMetaDataId);

	byte[] viewFileContent(long fileMetaDataId);
}
