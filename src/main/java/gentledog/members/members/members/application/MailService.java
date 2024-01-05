package gentledog.members.members.members.application;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendEmailAuthentication(String email) throws MessagingException;
    boolean verifyEmail(String email);
    void verifyEmailCode(String email, String code);
}
