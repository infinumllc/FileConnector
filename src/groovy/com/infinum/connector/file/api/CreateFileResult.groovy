package com.infinum.connector.file.api

import com.infinum.connector.file.domain.Bucket


/**
 * When the FileSystemService tries to create a file, this is the result.
 *
 */
class CreateFileResult {
	/**
	 * The bucket that the file was created in
	 */
	Bucket bucket

	/**
	 * In combination with the bucket, is the key to bring back the file
	 */
	String contentKey 

	/**
	 * A hash of the content of the file	
	 */
	String hash
	
	/**
	 * The size of the file stored
	 */
	Long fileSize
}
