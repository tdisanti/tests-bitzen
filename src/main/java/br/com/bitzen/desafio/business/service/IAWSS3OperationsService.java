package br.com.bitzen.desafio.business.service;

import java.io.File;
import java.util.Map;

public interface IAWSS3OperationsService {
	
	void putObjectToS3(String bucketName, File file, String contentType, Map<String, String> mapMetaData) throws Exception;
	
	String getFullObjectFromS3(String bucketName, String key) throws Exception;

}
