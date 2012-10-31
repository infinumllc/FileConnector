package com.infinum.connector.file.domain;

class Bucket implements Audited {

	/**
	 * User friendly name of bucket
	 */
	String name

	/**
	 * location to place/pull a file 
	 * also useful to tell the fileSystemService 
	 * where it should store the file.
	 */
	String path
	
	String fileSystemServiceName

	Boolean defaultBucket
	
	AuditData auditData

	static embedded = ['auditData']

	static constraints = {
		name blank: false, size: 5..150
		fileSystemServiceName blank:false
		auditData nullable: false
	}

	String toString(){
		return name
	}
}
