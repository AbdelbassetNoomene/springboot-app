package tn.training.cni.service;

import org.springframework.messaging.simp.user.SimpUser;
import tn.training.cni.dto.Message;

import java.util.List;

public interface NotificationService {

    void sendNotifToSpecificUser(Message message, String user);

    List<SimpUser> getConnectedUsers(String prefix);

    List<String> getConnectedUsers();

    void sendNotifToPrefixedUsers(Message message, String prefix);

}
