package convert.myapp.com.myapplication.bean;

import java.util.List;

public class CommentBean {
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

        private int articleId;

        private String commentContent;

        private int collectNumber;

        private String creatTime;

        private int nicknameId;

        private int userId;

        private String replyUserId;

        private String replyNicknameId;

        private String nicknameName;

        private String nicknameUrl;

        private String replyNicknameName;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setArticleId(int articleId){
            this.articleId = articleId;
        }
        public int getArticleId(){
            return this.articleId;
        }
        public void setCommentContent(String commentContent){
            this.commentContent = commentContent;
        }
        public String getCommentContent(){
            return this.commentContent;
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
        public void setNicknameId(int nicknameId){
            this.nicknameId = nicknameId;
        }
        public int getNicknameId(){
            return this.nicknameId;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setReplyUserId(String replyUserId){
            this.replyUserId = replyUserId;
        }
        public String getReplyUserId(){
            return this.replyUserId;
        }
        public void setReplyNicknameId(String replyNicknameId){
            this.replyNicknameId = replyNicknameId;
        }
        public String getReplyNicknameId(){
            return this.replyNicknameId;
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
        public void setReplyNicknameName(String replyNicknameName){
            this.replyNicknameName = replyNicknameName;
        }
        public String getReplyNicknameName(){
            return this.replyNicknameName;
        }

    }
}
