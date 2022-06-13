package org.pack.airline.services.serviceImp;

import java.util.Collection;
import java.util.List;
import org.pack.airline.model.User;
import org.pack.airline.repository.UserRepository;
import org.pack.airline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService {

	private UserRepository userRepo;

	@Autowired
	public UserServiceImp(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	public User getUserById(int uid) {

		return userRepo.findById(uid).orElse(null);
	}

	// save or update
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	// delete a specific record

	public void deleteUser(int uid) {
		userRepo.deleteById(uid);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	@Override
	public Page<User> getAllUsersPaged(int pageNum) {
		return userRepo.findAll(PageRequest.of(pageNum, 5, Sort.by("userName")));
	}

}
