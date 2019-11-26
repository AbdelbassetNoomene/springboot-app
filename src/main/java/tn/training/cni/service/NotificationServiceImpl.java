package tn.training.cni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import tn.training.cni.dto.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Override
    public void sendNotifToSpecificUser(Message message, String user) {
        template.convertAndSendToUser(user,"/queue/reply", message);
    }

    @Override
    public List<SimpUser> getConnectedUsers(String prefix) {
        List<SimpUser> users = new ArrayList<SimpUser>();
        simpUserRegistry.getUsers().forEach(user ->{
            if(user.getName().contains(prefix)) {
                users.add(user);
            }
        } );
        return users;
    }

    @Override
    public List<String> getConnectedUsers() {
        List<String> users = new ArrayList<String>();
        simpUserRegistry.getUsers().forEach(user ->{
            users.add(user.getName());
        } );
        return users;
    }

    @Override
    public void sendNotifToPrefixedUsers(Message message, String prefix) {
        if(prefix == null){
            simpUserRegistry.getUsers().forEach(user ->{
                sendNotifToSpecificUser(message, user.getName());
            } );
        }else{
            simpUserRegistry.getUsers().forEach(user ->{
                if(user.getName().contains(prefix)) {
                    sendNotifToSpecificUser(message, user.getName());
                }
            } );
        }
    }

}
