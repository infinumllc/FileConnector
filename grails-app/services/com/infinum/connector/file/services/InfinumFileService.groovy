package com.infinum.connector.file.services

import com.infinum.connector.file.api.CreateFileResult
import com.infinum.connector.file.api.FileForm
import com.infinum.connector.file.api.FileListFilter
import com.infinum.connector.file.domain.Bucket
import com.infinum.connector.file.domain.ContentType
import com.infinum.connector.file.domain.FileMetaData

//Transactional
class InfinumFileService implements FileService {

	private FileSystemService fs

	@Override
	FileMetaData createFile(final FileForm fileForm) {
		final Bucket bucket = Bucket.findBucketsByNameEquals(fileForm
				.getBucketName()).getSingleResult()

		final ContentType contentType = ContentType
				.findContentTypesByNameEquals(fileForm.getContentType())
				.getSingleResult()

		final CreateFileResult cfr = fs.createFile(bucket,
				fileForm.getContent())

		final FileMetaData file = new FileMetaData(fileForm.getFileName(),
				contentType, cfr, fileForm.getCreatedById())

		file.persist()

		return file
	}

	@Override
	boolean deleteFile(final long fileMetaDataId) {
		final FileMetaData fileMetaData = FileMetaData
				.findFileMetaData(fileMetaDataId)
		fs.deleteFile(fileMetaData)
		fileMetaData.remove()
		return true
	}

	@Override
	List<FileMetaData> listFiles(final FileListFilter fileListFilter) {
		// TODO Auto-generated method stub
		return null
	}

	@Override
	FileMetaData viewFileMetaData(final long fileMetaDataId) {
		return FileMetaData.findFileMetaData(fileMetaDataId)
	}

	@Override
	byte[] viewFileContent(final long fileMetaDataId) {
		final FileMetaData fileMetaData = FileMetaData
				.findFileMetaData(fileMetaDataId)
		return fs.viewFileContent(fileMetaData)
	}

}
