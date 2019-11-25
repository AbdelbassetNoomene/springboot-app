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
    public Map<String, String> useSocketCommunication(String message){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> messageConverted = null;
        try {
            messageConverted = mapper.readValue(message, Map.class);
        } catch (IOException e) {
            messageConverted = null;
        }
        if(messageConverted!=null){
            if(messageConverted.containsKey("toId") && messageConverted.get("toId")!=null && !messageConverted.get("toId").equals("")){
                this.simpMessagingTemplate.convertAndSend("/topic/"+messageConverted.get("toId"),messageConverted);
                this.simpMessagingTemplate.convertAndSend("/topic/"+messageConverted.get("fromId"),message);
            }else{
                this.simpMessagingTemplate.convertAndSend("/topic",messageConverted);
            }
        }
        return messageConverted;
    }

    @GetMapping("/api/users")
    @ResponseBody
    List<String> getListConnectedUsers(){
        return this.notificationService.getConnectedUsers();
    }

}
