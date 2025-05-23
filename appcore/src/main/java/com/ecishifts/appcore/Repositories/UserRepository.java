package com.ecishifts.appcore.Repositories;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecishifts.appcore.Model.User;


public interface UserRepository extends MongoRepository<User,String> {
    User findByName(String name);
}
