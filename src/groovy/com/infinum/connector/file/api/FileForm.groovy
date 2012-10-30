package com.infinum.connector.file.api

/**
 * The form for submitting a file to the system.
 */
class FileForm {

	/**
	 * The user assigned name of the file
	 */
	String fileName
	
	/**
	 * the type of file - aka mimetype
	 */
	String contentType
	
	/**
	 * Who created submitted this file
	 */
	Long createdById

	/**
	 * The content of the file as a byte array
	 */
	byte[] content

	/**
	 * Optional: specify the bucket in which to store this file
	 */
	String bucketname

}
