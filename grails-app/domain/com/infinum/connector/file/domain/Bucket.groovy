package com.infinum.connector.file.domain;

class Bucket implements Audited {

	/**
	 * User friendly name of bucket
	 */
	String name
	
	/**
	 * location to place/pull a file
	 */
	String path

	AuditData auditData

	static embedded = ['auditData']
	
	static constraints = { 
		name blank: false, size: 5..150 
		auditData nullable: false
	}
}
