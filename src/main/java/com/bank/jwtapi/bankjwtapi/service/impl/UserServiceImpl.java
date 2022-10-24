package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.dto.AuthenticationRequestDto;
import com.bank.jwtapi.bankjwtapi.dto.UserDto;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.Role;
import com.bank.jwtapi.bankjwtapi.models.Status;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtTokenProvider;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    @Value("{$spring.mail.username}")
//    private String sender;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final JavaMailSender mailSender;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;



//    @Override
//    public void sendVerificationEmail(JwtUser jwtUser) throws MessagingException {
//        String toAddress = jwtUser.getEmail();
////        String fromAddress = sender;
//        String subject = "Please verify your registration";
//        String content = "Dear [[name]],<br>"
//                + "Please click the link below to verify your registration:<br>"
//                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                + "Thank you,<br>"
//                + "Your company name.";
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
//
//        messageHelper.setFrom(sender);
//        messageHelper.setTo(toAddress);
//        messageHelper.setSubject(subject);
//
//        content = content.replace("[[name]]", jwtUser.getName());
//        messageHelper.setText(content, true);
//        mailSender.send(message);
//    }

    @Override
    public void register(UserDto userDto) {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.CLIENT);
        List<Status> userStatuses = new ArrayList<>();
        userStatuses.add(Status.ACTIVE);
        userRepository.save(User.builder()
                        .name(userDto.getName() != null ? userDto.getName(): "UserName")
                        .surname(userDto.getSurname() != null ? userDto.getSurname(): "UserSurname")
                        .birthdate(userDto.getBirthdate())
                        .debitCards(new ArrayList<>())
                        .sex(userDto.getSex())
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .roles(userRoles)
                        .statuses(userStatuses)
                        .loans(new ArrayList<>())
                .build());
    }

    @Override
    public User getUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with id " + email + " does not exist"));
    }

    @Override
    public ResponseEntity<Map<String, String>> login(AuthenticationRequestDto authenticationRequestDto) {
        try {
            String username = authenticationRequestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
            User user = getUser(username);

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User registerAdmin(User user) {
        Role userRole = Role.ADMIN;
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        Status userStatus = Status.ACTIVE;
        List<Status> userStatuses = new ArrayList<>();
        userStatuses.add(userStatus);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatuses(userStatuses);

        return userRepository.save(user);
    }

    @Override
    public User findById(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id" + id + "not found"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

}
