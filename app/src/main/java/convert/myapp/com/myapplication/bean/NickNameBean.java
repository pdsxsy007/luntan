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

        private int prentId;

        private List<NicknameList> nicknameList ;


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

        public int getPrentId() {
            return prentId;
        }

        public void setPrentId(int prentId) {
            this.prentId = prentId;
        }

        public List<NicknameList> getNicknameList() {
            return nicknameList;
        }

        public void setNicknameList(List<NicknameList> nicknameList) {
            this.nicknameList = nicknameList;
        }
    }

    public class NicknameList {
        private int id;

        private String nicknameName;

        private String nicknameUrl;

        private int prentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNicknameName() {
            return nicknameName;
        }

        public void setNicknameName(String nicknameName) {
            this.nicknameName = nicknameName;
        }

        public String getNicknameUrl() {
            return nicknameUrl;
        }

        public void setNicknameUrl(String nicknameUrl) {
            this.nicknameUrl = nicknameUrl;
        }

        public int getPrentId() {
            return prentId;
        }

        public void setPrentId(int prentId) {
            this.prentId = prentId;
        }
    }
}
