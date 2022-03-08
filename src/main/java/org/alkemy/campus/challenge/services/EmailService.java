package org.alkemy.campus.challenge.services;

import java.io.IOException;

public interface EmailService {

	String sendTextEmail(String receiver) throws IOException;
}
