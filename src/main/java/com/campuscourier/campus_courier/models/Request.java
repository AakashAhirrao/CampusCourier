package com.campuscourier.campus_courier.models;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // strict mathematical definition to tell that MANY request can belong to ONE user
    @JoinColumn(name = "requester_id", nullable = false) // @JoinColumn indicate foreign key and name shows the name of column
    private User requester;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description", columnDefinition = "TEXT")
    private String itemDescription;

    @Column(name = "tip_amount", nullable = false)
    private int tipAmount;

    @Column(nullable = false, length = 20)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(int tipAmount) {
        this.tipAmount = tipAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
