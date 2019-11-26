package tn.training.cni.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String subject;
    private String text;
    private String url;
    private String destination;
    private String from;
    private String type;
    private Date date;
}
