package gpse.umfrato.domain;

public interface PollService {
    Iterable<Poll> findAll();
    Poll addPoll(String title);
}
