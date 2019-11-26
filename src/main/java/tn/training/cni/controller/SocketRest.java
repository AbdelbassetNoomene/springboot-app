package tn.training.cni.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.training.cni.dto.Message;
import tn.training.cni.service.NotificationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
public class SocketRest {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NotificationService notificationService;
    @PostMapping(value = "/api/socket")
    public ResponseEntity<?> useSimpleRest(@RequestBody Message message){

            notificationService.sendNotifToPrefixedUsers(message, "");
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }

    @MessageMapping("/send/message")
    public void useSocketCommunication(String message){
        ObjectMapper mapper = new ObjectMapper();
        Message messageConverted = null;
        try {
            messageConverted = mapper.readValue(message, Message.class);
        } catch (IOException e) {
            messageConverted = null;
        }
        if(messageConverted!=null){
            if(messageConverted.getDestination() !=null) {
                notificationService.sendNotifToSpecificUser(messageConverted, messageConverted.getDestination());
            }else{
                notificationService.sendNotifToPrefixedUsers(messageConverted, "");
            }
        }
    }

    @GetMapping("/api/users")
    @ResponseBody
    List<String> getListConnectedUsers(){
        return this.notificationService.getConnectedUsers();
    }

}
