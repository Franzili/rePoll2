package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.mails.MailConfig;

/**
 * Provides mailing operations to the controller.
 */
public interface MailService {

    /**
     * Sends an e-mail.
     * @param to CSV with the addressees
     * @param subject Subject of the e-mail
     * @param body The message
     * @return A confirmation that the e-mail was sent or an error message
     */
    String sendEmail(String to, String subject, String body);

    /**
     * Sends an e-mail that contains the username and a new randomized password for a new user.
     * @param user The new user
     * @return A confirmation that the e-mail was sent or an error message
     */
    String sendPwdGenMail(User user);

    /**
     * Sets the SMTP server configs.
     * @param smtpServerAddress The server address
     * @param port The server port
     * @param account The account the e-mails should be sent from
     * @param password The corresponding password
     * @return true if the input included a correct internet address, else false
     */
    boolean setServerConfigs(String smtpServerAddress, int port, String account, String password);

    /**
     * Returns the current configurations for the SMTP server.
     * @return {@link MailConfig} with the SMTP server address, the corresponding port and the mail account from which
     * the e-mails are send with the corresponding password
     */
    MailConfig getMailConfigs();
}
