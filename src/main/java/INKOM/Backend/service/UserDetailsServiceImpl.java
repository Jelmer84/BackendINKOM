package INKOM.Backend.service;

import INKOM.Backend.domain.User;
import INKOM.Backend.exceptions.RecordNotFoundException;
import INKOM.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)  {
        //User user = userRepository.findByUsername(username)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
        return UserDetailsImpl.build(user);
    }


//    @Override
//    public Collection<User> getUsers() {
//        return userRepository.findAll();
//    }

    //@Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }
//
//    @Override
//    public boolean userExists(String username) {
//        return userRepository.existsById(username);
//    }
//
//
//    @Override
//    public void deleteUser(String username) {
//        userRepository.deleteById(username);
//    }
//
//    @Override
//    public void updateUser(String username, User newUser) {
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
//        User user = userRepository.findById(username).get();
//        user.setPassword(newUser.getPassword());
//        userRepository.save(user);
//    }
//
//    @Override
//    public Set<Authority> getAuthorities(String username) {
//        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        return user.getAuthorities();
//    }
//
//    @Override
//    public void addAuthority(String username, String authority) {
//        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        user.addAuthority(new Authority(username, authority));
//        userRepository.save(user);
//    }
//
//    @Override
//    public void removeAuthority(String username, String authority) {
//        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
//        user.removeAuthority(authorityToRemove);
//        userRepository.save(user);
//    }

}
