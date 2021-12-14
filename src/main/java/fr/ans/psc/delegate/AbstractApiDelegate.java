/*
 * (c) Copyright 1998-2021, ANS. All rights reserved.
 */
package fr.ans.psc.delegate;

import fr.ans.psc.exception.PscRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The Class ApiDelegate. Classe mère de tous les delegates. Traitement
 * générique des requêtes
 */
@Slf4j
public abstract class AbstractApiDelegate {

	protected String msgError = "";

	public static final String HEADER_TYPE_APP_WILDCARD = "application/json";
	public static final String HEADER_TYPE_FULL_WILDCARD = "*/*";

	/**
	 * Gets the request
	 *
	 * @return the request
	 */
	public Optional<NativeWebRequest> getRequest() {
		final ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();

		return Optional.of(new ServletWebRequest(attrs.getRequest()));
	}

	/**
	 * Gets the accept header.
	 *
	 * @return the accept header
	 */

	//renvoie une liste de tous les headers 'accept' de la requête
	public List<String> getAcceptHeaders() {
		List<String> acceptes = new ArrayList<>();
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		Enumeration<String> acceptheaders = attrs.getRequest().getHeaders("accept");
		while (acceptheaders.hasMoreElements()) {
			List<String> tmp = Arrays.asList(acceptheaders.nextElement().trim().split(","));
			tmp.replaceAll(String::trim);
			acceptes.addAll(tmp);
		}
		return acceptes;
	}

	protected Boolean isAcceptHeaderPresent(List<String> acceptheaders, String expectedAcceptHeader ) {	
		return ( acceptheaders.contains(expectedAcceptHeader)
				 || acceptheaders.contains(HEADER_TYPE_APP_WILDCARD)
				 || acceptheaders.contains(HEADER_TYPE_FULL_WILDCARD));
	}
}
