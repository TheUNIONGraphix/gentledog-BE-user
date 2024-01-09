package gentledog.members.members.members.application;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendEmailAuthentication(String email) throws MessagingException;
    boolean isDuplicateEmail(String email);
    void verifyEmailCode(String email, String code);
}
