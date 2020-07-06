package convert.myapp.com.myapplication.bean;

import java.util.List;

public class NickNameBean {
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

        private String nicknameName;

        private String nicknameUrl;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
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

    }
}
