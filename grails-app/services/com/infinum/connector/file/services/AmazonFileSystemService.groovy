package com.infinum.connector.file.services

import com.infinum.connector.file.api.CreateFileResult
import com.infinum.connector.file.domain.Bucket
import com.infinum.connector.file.domain.FileMetaData

class AmazonFileSystemService implements FileSystemService {

	@Override
	byte[] viewFileContent(FileMetaData fileMetaData) {
		// TODO Auto-generated method stub
		return null
	}

	@Override
	CreateFileResult createFile(Bucket bucket, byte[] content) {
		// TODO Auto-generated method stub
		return null
	}

	@Override
	boolean deleteFile(FileMetaData fileMetaData) {
		// TODO Auto-generated method stub
		return false
	}

}
