package com.unibuc.pwjexam.mapper;

import com.unibuc.pwjexam.dto.AddRoomDto;
import com.unibuc.pwjexam.model.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toRoom(AddRoomDto addRoomDto);

}
