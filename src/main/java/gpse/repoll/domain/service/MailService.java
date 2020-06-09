package gpse.repoll.domain.service;

import gpse.repoll.domain.User;

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
     * @param user The new User.
     * @return Confirmation that the Mail was sent or an error message.
     */
    String sendPwdGenMail(User user);
}
