package co.luisjavm3.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final CreditClient client;

	private static List<User> users = new ArrayList<>(
			Arrays.asList(new User(1l, "alberto", LocalDate.now(), LocalDate.now()),
					new User(2l, "benicio", LocalDate.now(), LocalDate.now()),
					new User(3l, "carla", LocalDate.now(), LocalDate.now()),
					new User(4l, "daniela", LocalDate.now(), LocalDate.now()),
					new User(5l, "emilio", LocalDate.now(), LocalDate.now())));

	private Long generateId() {
		Long maxId = users.stream().max(Comparator.comparing(User::getId)).get().getId();

		return maxId + 1;
	}

	public User getUserById(Long id) {
		return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
	}

	public List<User> getAllUsers() {
		return users;
	}

	public void deleteUser(Long id) {
		User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);

		if (user == null)
			return;

		users.remove(user);
		client.deleteCreditsByUserId(id);
	}

	public User addUser(User user) {
		User userDb = users.stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().orElse(null);

		if (userDb != null)
			return null;

		user.setId(generateId());
		user.setCreatedAt(LocalDate.now());
		user.setUpdatedAt(LocalDate.now());
		users.add(user);
		return user;
	}

	public User updateUser(Long id, User user) {
		User userDb = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);

		if (userDb == null)
			return null;

		boolean usernameExists = users.stream().filter(u -> u.getUsername().equals(user.getUsername()))
				.anyMatch(u -> true);

		if (!user.getUsername().equals(userDb.getUsername()) && usernameExists)
			return null;

		userDb.setUpdatedAt(LocalDate.now());
		userDb.setUsername(user.getUsername());
		return userDb;
	}
}
