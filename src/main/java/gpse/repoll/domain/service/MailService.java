package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.mails.MailConfig;

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

    /**
     * Sets the SMTP Server Configs.
     * @param smtpServerAddress server address.
     * @param port server port.
     * @param account The account the mails should be sent from.
     * @param password The corresponding password.
     * @return true, if the input included a correct internet address.
     */
    boolean setHostServer(String smtpServerAddress, int port, String account, String password);

    /**
     * Returns the current Configurations for the SMTP Server.
     * @return A MailConfig Object with the smptServerAddress, the corresponding port and the MailAccount from which
     * the Mails are send with the corresponding password.
     */
    MailConfig getMailConfigs();
}
