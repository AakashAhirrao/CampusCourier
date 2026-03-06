package com.campuscourier.campus_courier.repositories;

import com.campuscourier.campus_courier.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Hibernate in java is responsible for translating java tags into actual raw SQL query
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
