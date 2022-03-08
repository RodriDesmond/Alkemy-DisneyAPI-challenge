package org.alkemy.campus.challenge.security.services;
import org.alkemy.campus.challenge.payload.request.SignupRequest;
import org.alkemy.campus.challenge.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService {

	ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) throws IOException;
}
