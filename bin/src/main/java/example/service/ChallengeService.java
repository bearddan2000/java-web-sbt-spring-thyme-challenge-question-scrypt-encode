package example.service;

import java.util.List;

public interface ChallengeService {
  List<String> findAllQuestions();
  String findByQuestion(String questionId);
}
