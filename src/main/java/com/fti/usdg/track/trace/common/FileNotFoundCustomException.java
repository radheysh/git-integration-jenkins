/**
 * 
 */
package com.fti.usdg.track.trace.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Anup
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundCustomException extends RuntimeException {
	public FileNotFoundCustomException(String message) {
		super(message);
	}

	public FileNotFoundCustomException(String message, Throwable cause) {
		super(message, cause);
	}

}
