package com.unibuc.pwjexam.controller;

import com.unibuc.pwjexam.dto.AddRoomDto;
import com.unibuc.pwjexam.exception.RoomAlreadyExistsException;
import com.unibuc.pwjexam.mapper.RoomMapper;
import com.unibuc.pwjexam.model.Room;
import com.unibuc.pwjexam.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;

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
    public ResponseEntity<?> add(
            @RequestBody
            @Valid
            AddRoomDto addRoomDto) {
        try {
            Room room = roomMapper.toRoom(addRoomDto);
            Room addedRoom = roomService.add(room);
            return ResponseEntity.created(URI.create("/rooms/" + addedRoom.getId()))
                    .body(addedRoom);
        } catch (RoomAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            roomService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
