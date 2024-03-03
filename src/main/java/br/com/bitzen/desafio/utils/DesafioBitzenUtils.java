package br.com.bitzen.desafio.utils;

import org.apache.commons.validator.routines.UrlValidator;

public class DesafioBitzenUtils {
	
    public static boolean isWebsiteValid(String url) {
        UrlValidator validator = new UrlValidator();
        return url != null && validator.isValid(url);
    }

}
