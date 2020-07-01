package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private static Comparator<User> userComparatorByName = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private static Comparator<User> userComparatorByEmail = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getEmail().compareTo(o2.getEmail());
        }
    };

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
            if (user.isNew()) {
                user.setId(counter.incrementAndGet());
                repository.put(user.getId(), user);
                return user;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
        }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = new ArrayList<>(repository.values());
        userList.sort(userComparatorByName.thenComparing(userComparatorByEmail));
        return userList;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        List<User> userList = getAll();
        User user = userList.stream()
                .filter(user1 -> user1.getEmail().equals(email))
                .findAny()
                .orElse(null);
        return user;
    }
}
