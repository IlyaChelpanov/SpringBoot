package application;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;

import application.domain.Message;
import application.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

  @Autowired
  MessageRepository messageRepository;

  @GetMapping("/greeting")
  public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model) {
    model.put("name", name);
    return "greeting";
  }

  @GetMapping
  public String main(Map<String, Object> model) {
    model.put("messages", getAllMessages());
    return "main";
  }

  @PostMapping
  public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
    Message message = new Message(text, tag);

    messageRepository.save(message);

    model.put("messages", getAllMessages());

    return "main";
  }

  @PostMapping("/delete")
  public String delete(Map<String, Object> model) {
    messageRepository.deleteAll();
    return "main";
  }

  @PostMapping("/filter")
  public String filter(@RequestParam String tag, Map<String, Object> model){
    Iterable<Message> messages;
    if(tag !=null && !tag.isEmpty()) {
    messages = messageRepository.findByTag(tag);}
    else {
      messages = getAllMessages();
    }

    model.put("messages", messages);
    return "main";
  }


  private Iterable<Message> getAllMessages() {
    Iterable<Message> messages =  messageRepository.findAll();
    return messages;
  }
}