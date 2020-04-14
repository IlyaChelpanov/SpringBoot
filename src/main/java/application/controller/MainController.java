package application.controller;

import application.domain.Message;
import application.domain.User;
import application.repos.MessageRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

  @Autowired
  MessageRepository messageRepository;

  @Value("${upload.path}")
  private String uploadPath;

  @GetMapping("/")
  public String greeting(Map<String, Object> model) {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(Model model,
      @RequestParam(required = false, defaultValue = "") String filter) {

    Iterable<Message> messages = messageRepository.findAll();

    if (filter != null && !filter.isEmpty()) {
      messages = messageRepository.findByTag(filter);
    } else {
      messages = getAllMessages();
    }

    model.addAttribute("messages", messages);
    model.addAttribute("filter", filter);
    return "main";
  }

  @PostMapping("/main")
  public String main(@AuthenticationPrincipal User user,
      @RequestParam String text, @RequestParam String tag, Map<String, Object> model,
      @RequestParam("file")
          MultipartFile file) throws IOException {
    return add(user, text, tag, model, file);
  }

  @PostMapping("/add")
  public String add(@AuthenticationPrincipal User user, @RequestParam String text,
      @RequestParam String tag, Map<String, Object> model, @RequestParam("file")
      MultipartFile file) throws IOException {
    Message message = new Message(text, tag, user);

    if (file != null) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();

      String resultFilename = uuidFile + "." + file.getOriginalFilename();

      file.transferTo(new File(uploadPath + "/" + resultFilename));

      message.setFilename(resultFilename);

    }

    messageRepository.save(message);

    model.put("messages", getAllMessages());

    return "main";
  }

  @PostMapping("/delete")
  public String delete(Map<String, Object> model) {
    messageRepository.deleteAll();
    model.put("messages", getAllMessages());
    return "main";
  }

  private Iterable<Message> getAllMessages() {
    Iterable<Message> messages = messageRepository.findAll();
    return messages;
  }
}