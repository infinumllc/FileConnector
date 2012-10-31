package com.infinum.connector.file.services

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.ApplicationContext

import com.infinum.connector.file.api.CreateFileResult
import com.infinum.connector.file.api.FileException
import com.infinum.connector.file.api.FileForm
import com.infinum.connector.file.api.FileListFilter
import com.infinum.connector.file.domain.Bucket
import com.infinum.connector.file.domain.ContentType
import com.infinum.connector.file.domain.FileMetaData

/**
 * 
 * TODO Allow to specify Security - or allow viewing file on the external system
 */
class InfinumFileService implements FileService {

	GrailsApplication grailsApplication
	BucketService bucketService
	
	@Override
	FileMetaData createFile(final FileForm fileForm) throws FileException{
		final Bucket bucket = bucketService.getBucketByNameOrDefaultBucket(
				fileForm.bucketname)
		
		final ContentType contentType = ContentType.findByName(fileForm.contentType)
		if(!contentType){
			// TODO what to do if ContentType not specified?
		}
		
		final FileSystemService fileSystemService = 
				bucketService.resolveFileSystemServiceFromBucket(bucket)
		
		final CreateFileResult cfr = fileSystemService.createFile(bucket,
				fileForm.getContent())

		final FileMetaData file = new FileMetaData(fileForm.getFileName(),
				contentType, cfr, fileForm.getCreatedById())

		if(!file.save()){
			StringBuilder sb = new StringBuilder()
			file.errors.each {
				sb.append(it)
				sb.append('\n')
			}
			throw new FileException("Unable to save File " + sb.toString())
		}

		return file
	}

	@Override
	boolean deleteFile(final long fileMetaDataId) throws FileException {
		final FileMetaData fileMetaData = FileMetaData.get(fileMetaDataId)
		if(fileMetaData){
			final FileSystemService fileSystemService = 
					bucketService.resolveFileSystemServiceFromBucket(
							fileMetaData.bucket)
			fileSystemService.deleteFile(fileMetaData)
			fileMetaData.delete()
		}
		
		return true
	}

	@Override
	List<FileMetaData> listFiles(final FileListFilter fileListFilter) {
		// TODO implement file query
		return null
	}

	@Override
	FileMetaData viewFileMetaData(final long fileMetaDataId) {
		return FileMetaData.get(fileMetaDataId)
	}

	@Override
	byte[] viewFileContent(final long fileMetaDataId) throws FileException {
		final FileMetaData fileMetaData = FileMetaData.get(fileMetaDataId)
		if(!fileMetaData){
			throw new FileException("File not found")
		}
		
		final FileSystemService fileSystemService = 
				bucketService.resolveFileSystemServiceFromBucket(fileMetaData.bucket)
		
		return fileSystemService.viewFileContent(fileMetaData)
	}
}
