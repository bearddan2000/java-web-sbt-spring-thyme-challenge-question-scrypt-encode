package example.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("challengeRepository")
public interface ChallengeRepository {
  List<String> findAllQuestions();
  String findByQuestion(String questionId);
}
