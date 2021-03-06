package main.Services;

import main.Entities.User;
import main.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        if (!StringUtils.isEmpty(user.getEmail())){
            String message = "Здравствуй, "+user.getUsername()+"! Приветствуем в нашем магазине стройматериалов";
            emailService.sendSimpleMessage(user.getEmail(), message);
        }

        return user;
    }

    @Transactional
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public boolean saveUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null){
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    @Transactional
    public void resaveUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public void resaveUserPassword(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    public boolean deleteUser(int userId){
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    @Transactional
    public User getUserAuth()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
