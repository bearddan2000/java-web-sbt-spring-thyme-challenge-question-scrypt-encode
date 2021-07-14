package example.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChallengeRepositoryImpl implements ChallengeRepository {

    final String[] QUESTIONS = {
      "Year you were born?"
      , "Last 4 digits of your SSN?"
    };

    @Override
    public List<String> findAllQuestions(){
      return Arrays.asList(QUESTIONS);
    }

    @Override
    public String findByQuestion(String questionId){
      int idx = Integer.valueOf(questionId);
      if (idx < 0 || idx >= QUESTIONS.length ) {
        return "";
      }
      return QUESTIONS[idx];
    }
}
