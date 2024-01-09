package gentledog.members.members.members.application;

import gentledog.members.global.common.response.BaseResponseStatus;
import gentledog.members.global.common.exception.BaseException;
import gentledog.members.global.common.util.RedisUtil;
import gentledog.members.members.members.entity.Members;
import gentledog.members.members.members.infrastructure.MembersRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImple implements MailService{

    private final MembersRepository membersRepository;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String configEmail;

    /**
     * 0~9와 a~z까지의 숫자와 문자를 섞어서 6자리 난수를 만든
     * @return
     */
    public String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <=57 || i >=65) && (i <= 90 || i>= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * MimeMessage를 이용해서 메일을 만들고, 이메일을 보내는 메서드
     * 기간만료 시 레디스에서 삭제
     * @param email
     * @return
     * @throws MessagingException
     */

    public MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createdCode();
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setText("<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid {$point_color}; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">\n" +
                        "   <h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">\n" +
                        "      <span style=\"font-size: 15px; margin: 0 0 10px 3px;\">GentleDog</span><br />\n" +
                        "      <span style=\"color: {$point_color};\">메일인증</span> 안내입니다.\n" +
                        "   </h1>\n" +
                        "   <p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">\n" +
                        "      안녕하세요.<br />\n" +
                        "      GentleDog에 가입해 주셔서 진심으로 감사드립니다.<br />\n" +
                        "      아래 <b style=\"color: {$point_color};\">'메일 인증 번호'</b>를 입력하여 메일인증을 완료해 주세요.<br />\n" +
                        "      감사합니다.<br/><br/>" +
                        "   </p>\n" +
                        "   <b style=\" font-size : 40px ; color: {$point_color};\">메일 인증 번호 : "+authCode+"</b>" +
                        "   <br/><br/><br/></p>\n" +
                        "   <div style=\"border-top: 1px solid #DDD; padding: 5px;\">\n" +
                        "      <p style=\"font-size: 13px; line-height: 21px; color: #555;\">\n" +
                        "         만약 인증번호가 정상적으로 보이지않거나 인증이 지속적으로 실패된다면 고객센터로 연락주시면 감사하겠습니다.<br />\n" +
                        "      </p>\n" +
                        "   </div>\n" +
                        "</div>"
                , "UTF-8", "html");
        message.setFrom(configEmail);

        redisUtil.createDataExpire(email, authCode, 60 * 30L);
        return message;
    }


    /**
     * 만약 Redis에 해당 이메일로 된 값이 있다면 db에서 이를 삭제하고 진행한다.
     * @param email
     */
    public void sendEmailAuthentication(String email) throws MessagingException {

        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }

        MimeMessage emailForm = createEmailForm(email);
        mailSender.send(emailForm);
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        // 이메일이 존재한다면 이미 가입된 유저
        Optional<Members> members = membersRepository.findByMembersEmail(email);

        // 해당 이메일이 DB에 존재하면 체크 결과를 true로 리턴
        if(members.isPresent()){
            return true;
        }

        // 해당 이메일이 DB에 존재하지 않으면 체크 결과를 false로 리턴
        return false;
    }

    /**
     * Redis에서 키와 값을 꺼내 봐서 일치하면 인증 성공
     *
     * @param email
     * @param code
     */
    public void verifyEmailCode(String email, String code) {
        log.info("email : {}", email);

        String codeFoundByEmail = redisUtil.getData(email);


        if (codeFoundByEmail != null && codeFoundByEmail.equals(code)) {
            redisUtil.deleteData(email);
        } else if (codeFoundByEmail == null) {
            throw new BaseException(BaseResponseStatus.EXPIRED_AUTH_CODE);
        } else {
            throw new BaseException(BaseResponseStatus.WRONG_AUTH_CODE);
        }

    }
}
