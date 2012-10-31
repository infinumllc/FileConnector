package com.infinum.connector.file.services

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import com.infinum.connector.file.api.FileException
import com.infinum.connector.file.domain.Bucket

class BucketService implements ApplicationContextAware {
	ApplicationContext applicationContext
	
	public Bucket getBucketByNameOrDefaultBucket(final String name){
		Bucket bucket
		
		println 'Number of Buckets ' + Bucket.list().size()
		
		if(name){
			bucket = Bucket.findByName(name)
		}
		
		//if it wasn't found or wasn't specified, use the default
		if(!bucket){
			bucket = Bucket.findWhere(defaultBucket:true)
		}
		
		//if it wasn't found, no default has been set
		if(!bucket){
			throw new FileException("Default Bucket has not been set")
		}
		
		return bucket
	}
	
	public Collection<String> availableFileSystemServices(){
		return applicationContext.getBeansOfType(FileSystemService).keySet()
	}
	
	public FileSystemService resolveFileSystemServiceFromBucket(final Bucket bucket){
		FileSystemService fsService
		for(Map.Entry<String,FileSystemService> entry :
				applicationContext.getBeansOfType(FileSystemService)){
			if(entry.key == bucket.fileSystemServiceName){
				fsService = entry.value
				break;
			}
		}
		if(!fsService){
			throw new FileException("Invalid File System registered to bucket $bucket")
		}
		return fsService
	}
}
