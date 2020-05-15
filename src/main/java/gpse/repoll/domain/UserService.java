package gpse.repoll.domain;

import java.util.List;

public interface UserService {
    public Iterable<User> getAll();

    public User addUser(String userName, String fullName, String email);

    public User updateUser(Long userId, String userName, String fullName, String email);

    public List<Poll> getOwnedPolls(Long userId);
}
