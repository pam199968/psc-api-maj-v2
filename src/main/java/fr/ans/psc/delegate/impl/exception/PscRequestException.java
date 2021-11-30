/**
 * (c) Copyright 1998-2021, ANS. All rights reserved.
 */
package fr.ans.psc.delegate.impl.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class PscRequestException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	//transient pour Sonar. 'Error' est généré par OpneAPI (serializable). 
	//Cette exception est intercepté par un contrôleur Spring qui renverra une Http  en erreur
	//avec l'objet sérializé
	private transient fr.ans.psc.model.Error erreur;

	@Getter
	@Setter
	private HttpStatus statusARetourner = HttpStatus.INTERNAL_SERVER_ERROR;

	public PscRequestException(fr.ans.psc.model.Error erreur, HttpStatus statusARetourner) {
		super();
		this.erreur = erreur;
		this.statusARetourner = statusARetourner;
	}
}
