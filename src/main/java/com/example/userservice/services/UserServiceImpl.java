package com.example.userservice.services;

import com.example.userservice.configs.KafkaProducerClient;
import com.example.userservice.dtos.SendEmailDto;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private KafkaProducerClient kafkaProducerClient;
    private ObjectMapper objectMapper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, KafkaProducerClient kafkaProducerClient, ObjectMapper objectMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.kafkaProducerClient = kafkaProducerClient;
        this.objectMapper = objectMapper;
    }

    public User signUp(String email,
                       String name,
                       String password, List<Role> roles) {

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(roles);

        user.setEmailVerified(true);

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setTo(user.getEmail());
        sendEmailDto.setFrom("admin@shopit.com");
        sendEmailDto.setSubject("Welcome to ShopIT");
        sendEmailDto.setBody("Your Favourite Online Shop");

        User storedUser =  userRepository.save(user);
        try {
            kafkaProducerClient.sendMessage("sendEmail",objectMapper.writeValueAsString(sendEmailDto));
        }catch (Exception e){
            System.out.println("Something went wrong while sending a message to Kafka");
        }

        return storedUser;
    }
}
