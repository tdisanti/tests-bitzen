package br.com.bitzen.desafio.business.service;

import java.io.File;

public interface IImageService {

	void saveImage(File file) throws Exception;
	
	String getFullObjectFromS3() throws Exception;

}
