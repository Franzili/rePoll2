package gpse.repoll.domain;

public interface MailService {



    /**
     * Send Mail.
     * @param subject Subject of the Mail.
     * @param text Message.
     * @param addressee CSV with the addressees.
     * @return Confirmation that the Mail was sent or an error message.
     */
    String sendEmail(String subject, String text, String addressee);
}
