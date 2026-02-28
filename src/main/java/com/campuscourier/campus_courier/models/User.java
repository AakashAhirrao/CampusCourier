package com.campuscourier.campus_courier.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "hostel_block", nullable = false, length = 20)
    private String hostelBlock;

    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;
}
