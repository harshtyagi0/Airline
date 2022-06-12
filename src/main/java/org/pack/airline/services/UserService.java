package org.pack.airline.services;

import java.util.List;

import org.pack.airline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	public abstract Page<User> getAllUsersPaged(int pageNum);
	public abstract List<User> getAllUser();

	public abstract User getUserById(int uid);

	public abstract User saveUser(User user);

	public abstract void deleteUser(int uid);

}
