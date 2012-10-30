package com.infinum.connector.file.domain

import java.util.Date

class AuditData {

	Date dateCreated, lastUpdated //Auto managed by grails

	long createdById

	AuditData() {
		super()
	}

	AuditData(final long createdById) {
		this.createdById = createdById
	}
}
