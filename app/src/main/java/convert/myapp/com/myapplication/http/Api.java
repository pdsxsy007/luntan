package convert.myapp.com.myapplication.http;

public class Api {
    public static String baseUrl = "http://192.168.30.30:9090";

    public static String imgUrl = "http://192.168.30.30:9090";

    public static String nickNameListUrl = "/nickname/list";//昵称列表

    public static String articleListUrl = "/article/list";//帖子列表

    public static String loginUrl = "/user/login";//登录

    public static String registerUrl = "/user/register";//注册

    public static String saveArticleUrl = "/article/save";//发帖

    public static String articleCommentListUrl = "/articleComment/list";//获取评论列表

    public static String articleCommentSaveUrl = "/articleComment/save";//评论

    public static String articleCollectionUrl = "/article/collection";//收藏

    public static String articleGetOneUrl = "/article/getOne";//收藏

    public static String userlistUrl="/user/list";//管理员登录用户列表

    public static String updatauserUrl="/user/update";//管理员修改用户列表

    public static String articleDeletedUrl="/article/deleted";//删除恢复帖子

    public static String articleDeletedListUrl="/article/deletedList";//查看删除帖子列表


}
