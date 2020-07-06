package gpse.repoll.download_formats;

import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.Poll;

public class ParticipantsCSV {

    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";

    public String getData(Poll currentPoll, String serverPrefix) {

        StringBuilder csv = new StringBuilder();

        for (Participant participant : currentPoll.getParticipants()) {

            // full name
            csv.append(participant.getFullName());
            csv.append(COMMA);

            // e-mail
            csv.append(participant.getEmail());
            csv.append(COMMA);

            // personalized link
            csv.append(serverPrefix);
            csv.append("/answer/");
            csv.append(currentPoll.getId());
            csv.append("/");
            csv.append(participant.getId());


            csv.append(NEW_LINE);


        }

        return csv.toString();
    }

}
