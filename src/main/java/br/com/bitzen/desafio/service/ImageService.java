package br.com.bitzen.desafio.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IAWSS3OperationsService;
import br.com.bitzen.desafio.business.service.IImageService;

@Service
public class ImageService implements IImageService {

	private static final String BUCKET_NAME = "CapasAlbuns";
	
	@Autowired
    private IAWSS3OperationsService awsS3OperationsService;
	
	@Override
	public void saveImage(File file) throws Exception {
		String contentType = MediaType.IMAGE_JPEG_VALUE;
		awsS3OperationsService.putObjectToS3(BUCKET_NAME, file, contentType, null);
	}
	
	@Override
	public String getFullObjectFromS3() throws Exception {
		String key = "Capa1";
		return awsS3OperationsService.getFullObjectFromS3(BUCKET_NAME, key);
	}
    
}
