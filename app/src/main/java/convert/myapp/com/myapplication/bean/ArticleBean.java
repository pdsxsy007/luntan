package convert.myapp.com.myapplication.bean;

import java.util.List;

public class ArticleBean {
    private int code;

    private String msg;

    private List<Data> data ;

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


    public class Data {
        private int id;

        private int userId;

        private String articleTitle;

        private String articleContent;

        private int collectNumber;

        private String creatTime;

        private int repliesNumber;

        private int nicknameId;

        private String nicknameName;

        private String nicknameUrl;

        private int deleted;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setArticleTitle(String articleTitle){
            this.articleTitle = articleTitle;
        }
        public String getArticleTitle(){
            return this.articleTitle;
        }
        public void setArticleContent(String articleContent){
            this.articleContent = articleContent;
        }
        public String getArticleContent(){
            return this.articleContent;
        }
        public void setCollectNumber(int collectNumber){
            this.collectNumber = collectNumber;
        }
        public int getCollectNumber(){
            return this.collectNumber;
        }
        public void setCreatTime(String creatTime){
            this.creatTime = creatTime;
        }
        public String getCreatTime(){
            return this.creatTime;
        }
        public void setRepliesNumber(int repliesNumber){
            this.repliesNumber = repliesNumber;
        }
        public int getRepliesNumber(){
            return this.repliesNumber;
        }
        public void setNicknameId(int nicknameId){
            this.nicknameId = nicknameId;
        }
        public int getNicknameId(){
            return this.nicknameId;
        }
        public void setNicknameName(String nicknameName){
            this.nicknameName = nicknameName;
        }
        public String getNicknameName(){
            return this.nicknameName;
        }
        public void setNicknameUrl(String nicknameUrl){
            this.nicknameUrl = nicknameUrl;
        }
        public String getNicknameUrl(){
            return this.nicknameUrl;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }
    }
}
