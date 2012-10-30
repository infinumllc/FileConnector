package com.infinum.connector.file.domain

import com.infinum.connector.file.api.CreateFileResult

class FileMetaData implements Audited {

	String fileName, hash, contentKey
	
	Long fileSize

	Bucket bucket

	ContentType contentType

	AuditData auditData

	static embedded = ['auditData']
	
	static constraints = { 
		fileName blank: false
		hash blank: false
		contentKey blank: false
		fileSize nullable: false
		bucket nullable: false
		contentType nullable: false
		auditData nullable: false
	}
	
	FileMetaData() {
		super()
	}

	FileMetaData(final String fileName, final ContentType contentType,
			final CreateFileResult cfr, final long createdById) {

		super()

		this.fileName = fileName
		this.bucket = cfr.bucket
		this.contentKey = cfr.contentKey
		this.hash = cfr.hash
		this.fileSize = cfr.fileSize
		this.contentType = contentType
		this.auditData = new AuditData(createdById)
	}
}
