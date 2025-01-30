package com.unibuc.pwjexam.service;

import com.unibuc.pwjexam.enums.StatusType;
import com.unibuc.pwjexam.exception.RoomAlreadyExistsException;
import com.unibuc.pwjexam.model.Room;
import com.unibuc.pwjexam.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room add(Room room) {
        Optional<Room> otherRoom = roomRepository.findByRoomNumber(room.getRoomNumber());

        if (otherRoom.isPresent()) {
            throw new RoomAlreadyExistsException("There is already an item with the same number!");
        }

        return roomRepository.save(room);
    }

    @Transactional
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!room.getStatus().equals(StatusType.AVAILABLE)) {
            throw new IllegalStateException("The room is occupied, it cannot be deleted.");
        }

        roomRepository.deleteById(id);
    }

}
