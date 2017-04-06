package connect.shopping.akshay.tutorials.Comments;

/**
 * Created by Akshay on 30-03-2017.
 */

public class Comment {

    private int user_id;
    private int vedio_id;
    private String user_name;
    private String comment;
    private String time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVedio_id() {
        return vedio_id;
    }

    public void setVedio_id(int vedio_id) {
        this.vedio_id = vedio_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
