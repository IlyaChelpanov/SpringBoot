package application.controller;

import application.domain.Message;
import application.domain.User;
import application.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

  @Autowired
  MessageRepository messageRepository;

  @GetMapping("/")
  public String greeting(Map<String, Object> model) {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(Map<String, Object> model) {
    model.put("messages", getAllMessages());
    return "main";
  }

  @PostMapping("/main")
  public String add(@AuthenticationPrincipal User user,
          @RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
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