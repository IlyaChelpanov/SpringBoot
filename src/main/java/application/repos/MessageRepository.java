package application.repos;


import application.domain.Message;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {

 List<Message> findByTag(String tag);

 List<Message> findByTextStartingWith(String starting);
}
