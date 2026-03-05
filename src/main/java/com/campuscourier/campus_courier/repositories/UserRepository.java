package com.campuscourier.campus_courier.repositories;

import com.campuscourier.campus_courier.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository is the 3rd layer in 3 tier architecture
// Repository handle database operations
// @Repository annotation indicate that this class contain all the database operations
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
