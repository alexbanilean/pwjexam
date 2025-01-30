package com.unibuc.pwjexam.model;

import com.unibuc.pwjexam.enums.ComfortType;
import com.unibuc.pwjexam.enums.RoomType;
import com.unibuc.pwjexam.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer roomNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ComfortType comfort;

    @NotNull
    @Positive
    private Double pricePerNight;

    @NotNull
    @Positive
    @Max(4)
    private Integer capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusType status;

    private String description;

    public Room() {}

    public Room(Long id, Integer roomNumber, RoomType type, ComfortType comfort, Double pricePerNight, Integer capacity, StatusType status, String description) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.comfort = comfort;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.status = status;
        this.description = description;
    }

    public Room(Long id, Integer roomNumber, RoomType type, ComfortType comfort, Double pricePerNight, Integer capacity, StatusType status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.comfort = comfort;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(@NotNull Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public @NotNull RoomType getType() {
        return type;
    }

    public void setType(@NotNull RoomType type) {
        this.type = type;
    }

    public @NotNull ComfortType getComfort() {
        return comfort;
    }

    public void setComfort(@NotNull ComfortType comfort) {
        this.comfort = comfort;
    }

    public @NotNull @Positive Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull @Positive Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @NotNull @Positive @Max(4) Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull @Positive @Max(4) Integer capacity) {
        this.capacity = capacity;
    }

    public @NotNull StatusType getStatus() {
        return status;
    }

    public void setStatus(@NotNull StatusType status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
