package example.service;

import example.repository.ChallengeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public List<String> findAllQuestions(){
      return challengeRepository.findAllQuestions();
    }

    @Override
    public String findByQuestion(String questionId){
      String val = challengeRepository.findByQuestion(questionId);
      if (val == null || val.equals("") ) {
        System.out.println("[LOG] Index out of bounds");
        return "";
      }
      return val;
    }

}
