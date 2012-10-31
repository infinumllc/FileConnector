package com.infinum.connector.file.services

import static org.junit.Assert.*
import grails.test.mixin.*
import grails.test.mixin.support.*

import org.junit.*

import com.infinum.connector.file.domain.AuditData
import com.infinum.connector.file.domain.Bucket

@Mock(Bucket)
class BucketServiceIntegrationTests {

	BucketService bucketService
	
    @Before
    void setUp() {
		assert bucketService
    }
	
	@Test()
	void testAvailableFileSystemServices(){
		Collection<String> services =  bucketService.availableFileSystemServices()
		assert services.contains("localFileSystemService")
	}

    @Test()
    void testGetBucketByNameOrDefaultBucket() {
        //should be no buckets defined at first
		shouldFail{
			bucketService.getBucketByNameOrDefaultBucket("testBucket")
        }
		
		//lets add a default bucket
		Bucket defaultBucket = defaultBucketForTest()
		defaultBucket.save(flush:true)
		
		//should now be one bucket
		assert Bucket.list().size() == 1
		
		//lets grab it by its name
		Bucket retrieved = bucketService.getBucketByNameOrDefaultBucket("testBucket")
		assert retrieved == defaultBucket
		
		//lets grab and miss, just getting the testBucket
		retrieved = bucketService.getBucketByNameOrDefaultBucket("nonexistent-bucket")
		assert retrieved == defaultBucket
		
		Bucket bucket2 = new Bucket(name:'testBucket2', path:'/tmp/testing2', 
			fileSystemServiceName:'localFileSystemService', defaultBucket:false, 
			auditData: new AuditData(1L))
		bucket2.save(flush:true)
		
		//lets grab and miss, just getting the testBucket
		retrieved = bucketService.getBucketByNameOrDefaultBucket("nonexistent-bucket")
		assert retrieved == defaultBucket
		
		//lets grab testBucket2
		retrieved = bucketService.getBucketByNameOrDefaultBucket("testBucket2")
		assert retrieved == bucket2
		
		bucket2.delete()
		defaultBucket.delete(flush:true)
    }
	
	@Test
	void testResolveFileSystemServiceFromBucket(){
		FileSystemService resolved = bucketService.resolveFileSystemServiceFromBucket(defaultBucketForTest())
		assert resolved instanceof LocalFileSystemService
		
		shouldFail{
			bucketService.resolveFileSystemServiceFromBucket(
					new Bucket(name:'bottomlessBucket', path:'/tmp/testing',
						fileSystemServiceName:'bottomlessFileSystemService', 
						defaultBucket:true, auditData: new AuditData(1L)))
		}
	}
	
	Bucket defaultBucketForTest(){
		new Bucket(name:'testBucket', path:'/tmp/testing',
			fileSystemServiceName:'localFileSystemService', defaultBucket:true,
			auditData: new AuditData(1L))
	}
}
