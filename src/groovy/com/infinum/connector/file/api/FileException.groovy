package com.infinum.connector.file.api

/**
 * Thrown by FileSystemServices
 *
 */
class FileException extends Exception {

	private String path
	
	FileException(){
		super()
	}
	
	FileException(String message){
		super(message)
	}
	
	FileException(String message, String path){
		super(message)
		this.path = path
	}
	
	FileException(String message, String path, Throwable t){
		super(message, t)
		this.path = path
	}
	
	String getPath(){
		return path
	}
}
