package convert.myapp.com.myapplication.bean;

import java.util.List;

public class AnswerBean {
    private int code;

    private String msg;

    private List<Data> data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }

    public class Data
    {
        private int id;

        private String questionName;

        private int type;

        private List<AnswerList> answerList;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setQuestionName(String questionName){
            this.questionName = questionName;
        }
        public String getQuestionName(){
            return this.questionName;
        }
        public void setType(int type){
            this.type = type;
        }
        public int getType(){
            return this.type;
        }
        public void setAnswerList(List<AnswerList> answerList){
            this.answerList = answerList;
        }
        public List<AnswerList> getAnswerList(){
            return this.answerList;
        }
    }

    public class AnswerList
    {
        private int id;

        private String optiona;

        private int questionId;

        private String answer;

        private String optionDesc;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setOptiona(String optiona){
            this.optiona = optiona;
        }
        public String getOptiona(){
            return this.optiona;
        }
        public void setQuestionId(int questionId){
            this.questionId = questionId;
        }
        public int getQuestionId(){
            return this.questionId;
        }
        public void setAnswer(String answer){
            this.answer = answer;
        }
        public String getAnswer(){
            return this.answer;
        }
        public void setOptionDesc(String optionDesc){
            this.optionDesc = optionDesc;
        }
        public String getOptionDesc(){
            return this.optionDesc;
        }
    }
}
