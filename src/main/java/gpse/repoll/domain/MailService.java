package gpse.repoll.domain;

/**
 * Provides operations with Mailing to the Controller.
 */
public interface MailService {

    /**
     * Send Mail.
     * @param to CSV with the addressees.
     * @param subject Subject of the Mail.
     * @param body Message.
     * @return Confirmation that the Mail was sent or an error message.
     */
    String sendEmail(String to, String subject, String body);

    /**
     * Sends a Mail that contains the username and a new randomized password for a new user.
     * @param to CSV with the addressees.
     * @return Confirmation that the Mail was sent or an error message.
     */
    String sendPwdGenMail(String to);
}
