package com.rachit.bytebuzz.backend.controllers;


import com.rachit.bytebuzz.backend.config.AppConstants;
import com.rachit.bytebuzz.backend.entities.Message;
import com.rachit.bytebuzz.backend.entities.Room;
import com.rachit.bytebuzz.backend.payload.MessageRequest;
import com.rachit.bytebuzz.backend.repositories.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@CrossOrigin(AppConstants.FRONT_END_BASE_URL)
public class ChatController
{
    private final RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }

    // For sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}") // app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}") // Subscribe
    public Message sendMessage(
            @DestinationVariable("roomId") String roomId,
            @Payload MessageRequest request
    ) {
        Room room = roomRepository.findByRoomId(roomId);

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());
        if (room != null)
        {
            if (room.getMessages() == null) {
                room.setMessages(new ArrayList<>());
            }
            room.getMessages().add(message);
            roomRepository.save(room);
        }
        else
        {
            throw new RuntimeException("Room not found !");
        }
        return message;
    }
}
