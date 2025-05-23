package com.ecishifts.appcore.Services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecishifts.appcore.Model.User;
import com.ecishifts.appcore.Repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> list = userRepository.findAll();
        if (list.isEmpty()){
            throw new RuntimeException("The user's list is empty");
        }
        return list;
    }
    
    public User getUserByMail(String mail) {
        User user = userRepository.findById(mail).orElse(null);
        if (user == null){
            throw new RuntimeException("No se encuentra el usuario con id " + mail);
        }
        return user;
    }

    public User getUserByName(String name){
        User user = userRepository.findByName(name);
        if (user == null){
            throw new RuntimeException("No se encuentra el usuario con nombre " + name );
        }
        return user;
    }

    public User addUser(User user) {
        User existingUser = userRepository.findByName(user.getName());
        if (existingUser != null){
            throw new RuntimeException("User " + user.getName() + " exists already.");
        }
        return userRepository.save(user);
    }


    public String deletUser(String mail){
        User userSearch = userRepository.findById(mail).orElse(null);
        if(userSearch == null){
            throw new RuntimeException("No Existe el usuario a eliminar");
        }
        userRepository.deleteById(mail);
        return mail;
    }


}
