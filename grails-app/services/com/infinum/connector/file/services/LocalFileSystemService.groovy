package com.infinum.connector.file.services

import org.apache.commons.codec.digest.DigestUtils
import com.infinum.connector.file.api.CreateFileResult
import com.infinum.connector.file.api.FileException
import com.infinum.connector.file.domain.Bucket
import com.infinum.connector.file.domain.FileMetaData

/**
 * Implement the File System Service on the local File System
 *
 * TODO possibly zip files on file system? - might not gain that much space since 
 * pdf and office documents are already binary formats
 * 
 * TODO possibly split up directories when they reach a certain size?
 * 
 * TODO should we detect mimetypes?
 * 
 * TODO figure out how to get sha256 hashing - for some reason I can't pull in the dependency on the plugin
 */
class LocalFileSystemService implements FileSystemService {
	
	@Override
	byte[] viewFileContent(final FileMetaData fileMetaData) {
		File directory = getBucketAsDirectory(fileMetaData.bucket)
		File file = new File(directory, fileMetaData.contentKey)
		return file.getBytes()
	}

	@Override
	CreateFileResult createFile(final Bucket bucket, final byte[] content) {
		File directory = getBucketAsDirectory(bucket)
		
		// Create the filename
		String fileName = UUID.randomUUID().toString()
		
		//hash the contents
		String hash = DigestUtils.sha(content).encodeBase64().toString()
		
		//create the file location
		File file = new File(directory, fileName)
		
		//write the file
		try{
			if(file.createNewFile()){
				file.setBytes(content)
			}else{
				throw new FileException("Could not create file within bucket", 
					file.getCanonicalPath())
			}
		}catch(FileException fe){
			throw fe
		}catch(Exception e){
			throw new FileException("Unable to create file", file.getCanonicalPath(), e)
		}
		
		//we were successful, return the filename as the contentKey, and the hash
		return new CreateFileResult(bucket:bucket, contentKey:fileName, 
			hash:hash, fileSize:file.size())
	}

	@Override
	boolean deleteFile(final FileMetaData fileMetaData) {
		File directory = getBucketAsDirectory(fileMetaData.bucket)
		File file = new File(directory, fileMetaData.contentKey)
		return file.delete()
	}

	protected File getBucketAsDirectory(final Bucket bucket) {
		assert bucket != null
		//get the local directory to write to, make sure it exists
		File directory = new File(bucket.path)
		if(!directory.isDirectory()){
			throw new FileException("Bucket path ${bucket.path} does not exist", bucket.path)
		}
		return directory
	}
	
}
