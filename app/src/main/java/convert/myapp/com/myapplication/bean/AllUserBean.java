package convert.myapp.com.myapplication.bean;

import java.util.List;

public class AllUserBean {
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
        private int userId;

        private String userAccount;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAdministrator() {
            return administrator;
        }

        public void setAdministrator(int administrator) {
            this.administrator = administrator;
        }

        public int getForbiddenWords() {
            return forbiddenWords;
        }

        public void setForbiddenWords(int forbiddenWords) {
            this.forbiddenWords = forbiddenWords;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        private String nicknameUrl;

        private String userPassword;

        private String userName;

        private int administrator;

        private int forbiddenWords;

        private int deleted;


        public void setNicknameUrl(String nicknameUrl){
            this.nicknameUrl = nicknameUrl;
        }
        public String getNicknameUrl(){
            return this.nicknameUrl;
        }

    }
}
