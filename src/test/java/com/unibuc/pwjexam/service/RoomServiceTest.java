package com.unibuc.pwjexam.service;

import com.unibuc.pwjexam.enums.ComfortType;
import com.unibuc.pwjexam.enums.RoomType;
import com.unibuc.pwjexam.enums.StatusType;
import com.unibuc.pwjexam.exception.RoomAlreadyExistsException;
import com.unibuc.pwjexam.model.Room;
import com.unibuc.pwjexam.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setRoomNumber(101);
        room.setType(RoomType.SINGLE);
        room.setStatus(StatusType.AVAILABLE);
        room.setComfort(ComfortType.STANDARD);
        room.setCapacity(4);
        room.setPricePerNight(100.0);
        room.setDescription("This is a test");
    }

    // Test Case 1: Successfully add a room
    @Test
    void addRoom_WhenRoomDoesNotExist_ShouldSaveRoom() {
        when(roomRepository.findByRoomNumber(room.getRoomNumber())).thenReturn(Optional.empty());
        when(roomRepository.save(room)).thenReturn(room);

        Room savedRoom = roomService.add(room);

        assertNotNull(savedRoom);
        assertEquals(room.getRoomNumber(), savedRoom.getRoomNumber());
        verify(roomRepository).save(room);
    }

    // Test Case 2: Add a room that already exists -> should throw RoomAlreadyExistsException
    @Test
    void addRoom_WhenRoomAlreadyExists_ShouldThrowException() {
        when(roomRepository.findByRoomNumber(room.getRoomNumber())).thenReturn(Optional.of(room));

        assertThrows(RoomAlreadyExistsException.class, () -> roomService.add(room));
        verify(roomRepository, never()).save(any());
    }

    // Test Case 3: Successfully delete an available room
    @Test
    void deleteRoom_WhenRoomIsAvailable_ShouldDeleteRoom() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        roomService.delete(1L);

        verify(roomRepository).deleteById(1L);
    }

    // Test Case 4: Delete a room that does not exist -> should throw IllegalArgumentException
    @Test
    void deleteRoom_WhenRoomDoesNotExist_ShouldThrowException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> roomService.delete(1L));
        verify(roomRepository, never()).deleteById(anyLong());
    }

    // Test Case 5: Delete a room that is occupied -> should throw IllegalStateException
    @Test
    void deleteRoom_WhenRoomIsOccupied_ShouldThrowException() {
        room.setStatus(StatusType.OCCUPIED);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        assertThrows(IllegalStateException.class, () -> roomService.delete(1L));
        verify(roomRepository, never()).deleteById(anyLong());
    }

}
