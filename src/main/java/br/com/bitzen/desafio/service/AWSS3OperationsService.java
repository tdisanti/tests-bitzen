package br.com.bitzen.desafio.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import br.com.bitzen.desafio.business.service.IAWSS3OperationsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AWSS3OperationsService implements IAWSS3OperationsService {
	
    protected static final Regions CLIENT_REGION = Regions.US_EAST_1;

    @Override
	public void putObjectToS3(String bucketName, File file, String contentType, Map<String, String> mapMetaData) throws Exception {

		try {
			AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
			builder.setForceGlobalBucketAccessEnabled(true);

			// Cria client AmazonS3
			AmazonS3 s3Client = builder.withRegion(CLIENT_REGION).build();

			// Cria metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(contentType);

			for (Map.Entry<String, String> entry : mapMetaData.entrySet()) {
				metadata.addUserMetadata(entry.getKey(), entry.getValue());
			}

			PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
			request.setMetadata(metadata);
			s3Client.putObject(request);
			log.info("Adicionado ao AWS S3: " + file.getName());

		} catch (AmazonServiceException e) {
			throw new Exception(e.getMessage(), e);

		} catch (SdkClientException e) {
			throw new Exception(e.getMessage(), e);
		}

	}

    @Override
	public String getFullObjectFromS3(String bucketName, String key) throws Exception {

		S3Object fullObject = null;
		StringBuilder sb = new StringBuilder();

		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
		builder.setForceGlobalBucketAccessEnabled(true);

		try {
			// Cria client AmazonS3
			AmazonS3 s3Client = builder.withRegion(CLIENT_REGION).build();

			log.info("Downloading an object {}", key);
			fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
			log.info("Content-Type: " + fullObject.getObjectMetadata().getContentType());

			BufferedReader reader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

		} catch (AmazonServiceException e) {
			throw new Exception(e.getMessage(), e);
		} catch (SdkClientException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			if (fullObject != null) {
				fullObject.close();
			}
		}

		return sb.toString();
	}

}
