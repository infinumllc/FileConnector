package com.infinum.connector.file.domain;

/**
 * Marks a domain class as having AuditData
 */
public interface Audited {

	AuditData getAuditData();
}
