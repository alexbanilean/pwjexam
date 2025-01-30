package com.unibuc.pwjexam.controller;

import com.unibuc.pwjexam.dto.AddRoomDto;
import com.unibuc.pwjexam.mapper.RoomMapper;
import com.unibuc.pwjexam.model.Room;
import com.unibuc.pwjexam.repository.RoomRepository;
import com.unibuc.pwjexam.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomMapper roomMapper;
    private RoomService roomService;

    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @PostMapping
    public ResponseEntity<Room> create(
            @RequestBody
            @Valid
            AddRoomDto addRoomDto) {
        Room room = roomMapper.toRoom(addRoomDto);
        Room createdRoom = roomService.add(room);
        return ResponseEntity.created(URI.create("/rooms" + createdRoom.getId()))
                .body(createdRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            roomService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
