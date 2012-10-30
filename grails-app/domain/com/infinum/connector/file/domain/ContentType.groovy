package com.infinum.connector.file.domain

class ContentType implements Audited {

	String name

	AuditData auditData

	static embedded = ['auditData']
	
	static constraints = { 
		name blank: false, size: 5..150 
		auditData nullable: false
	}
}
