package com.infinum.connector.file.services;

import com.infinum.connector.file.api.CreateFileResult;
import com.infinum.connector.file.domain.Bucket;
import com.infinum.connector.file.domain.FileMetaData;

public interface FileSystemService {

	byte[] viewFileContent(FileMetaData fileMetaData);

	CreateFileResult createFile(Bucket bucket, byte[] content);

	boolean deleteFile(FileMetaData fileMetaData);
}
