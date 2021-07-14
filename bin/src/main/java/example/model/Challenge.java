package example.model;

public class Challenge {
  String question;
  String answer;

  public Challenge() {}

  public Challenge(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }

  public String getQuestion() {
      return question;
  }

  public void setQuestion(String question) {
      this.question = question;
  }

  public String getAnswer() {
      return answer;
  }

  public void setAnswer(String answer) {
      this.answer = answer;
  }

  @Override
  public int hashCode(){
      return this.question.hashCode() + this.answer.hashCode();
  }

  @Override
  public boolean equals(Object obj){
    Challenge o = (Challenge)obj;
    return this.hashCode() == o.hashCode();
  }

  @Override
  public String toString(){
    return String.format("question=%s, answer=%s", question, answer);
  }
}
