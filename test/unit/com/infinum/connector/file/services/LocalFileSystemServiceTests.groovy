package com.infinum.connector.file.services

import static org.junit.Assert.*
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import com.infinum.connector.file.api.CreateFileResult
import com.infinum.connector.file.domain.AuditData
import com.infinum.connector.file.domain.Bucket
import com.infinum.connector.file.domain.FileMetaData

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LocalFileSystemService)
class LocalFileSystemServiceTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }
	
	private Bucket validBucketInTestEnv(){
		return new Bucket(name:'test bucket', path:'/tmp/testing',
			auditData: new AuditData(1L))
	}

    void testGetBucketAsDirectory() {
		Bucket bucket = validBucketInTestEnv()
		File directory = service.getBucketAsDirectory(bucket)
		
		assert null != directory
    }
	
    void testGetBucketAsDirectoryNotExists() {
		Bucket bucket = new Bucket(name:'test bucket', path:'/tmp/testing/iDontExist', 
			auditData: new AuditData(1L))
		
		shouldFail{
			service.getBucketAsDirectory(bucket)
		}
    }
	
	void testCreateFileViewFileDeleteFile(){
		String fileString = "Testing, Testing, 1, 2, 3"
		Bucket bucket = validBucketInTestEnv()
		
		CreateFileResult createFileResult = service.createFile(
			bucket, fileString.bytes)
		
		assert null != createFileResult
		
		assert 0 < createFileResult.fileSize
		assert null != createFileResult.hash
		assert null != createFileResult.contentKey
		assert bucket == createFileResult.bucket
		
		FileMetaData fileMetaData = new FileMetaData(bucket:bucket, 
			contentKey: (createFileResult.contentKey))
		
		byte[] content = service.viewFileContent(fileMetaData)
		
		assert (fileString.bytes) == content
		
		boolean success = service.deleteFile(fileMetaData)
		
		assert success
	}
}
