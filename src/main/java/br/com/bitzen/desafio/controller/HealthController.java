package br.com.bitzen.desafio.controller;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/health")
public class HealthController {

	@Autowired
	private ProjectInfoAutoConfiguration props;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resourceStatus() {
		log.debug("HealthController: resourceStatus");

		StringBuilder sb = new StringBuilder("Bitzen - Desafio 2024 - Health Check");

		try {
			BuildProperties bp = props.buildProperties();
			sb.append("\n\n Artifact: " + bp.getArtifact())
				.append("\n Group: " + bp.getGroup())
				.append("\n Name: " + bp.getName())
				.append("\n Version: " + bp.getVersion())
				.append("\n Build Time: " + bp.getTime().minus(3L, ChronoUnit.HOURS))
				.append("\n Java Version: " + bp.get("java.version"));

			return new ResponseEntity<>(sb.toString(), HttpStatus.OK);

		} catch (Exception e) {
			sb.append("\n\n Erro: " + e.getMessage());
			return new ResponseEntity<>(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
