package convert.myapp.com.myapplication.bean;

/**
 * Created by Cuilei on 2020/7/6.
 */

public class LoginBean {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    private String msg;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }



        private User user;


        public class User{

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

            public String getAdministrator() {
                return administrator;
            }

            public void setAdministrator(String administrator) {
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
            private String userPassword;
            private String userName;
            private String administrator;
            private int forbiddenWords;
            private int deleted;
        }

    }

}
