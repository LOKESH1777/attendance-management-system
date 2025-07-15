public class TeacherModel {
    private int teachId;
    private String teachName;
    private String teachEmail;
    private String teachCourse;
    private String teachRole;


    public TeacherModel(int teachId, String teachName, String teachEmail, String teachCourse, String teachRole){
        this.teachId = teachId;
        this.teachName = teachName;
        this.teachEmail = teachEmail;
        this.teachCourse = teachCourse;
        this.teachRole = teachRole;
    }

    public int getTeachId(){
        return teachId;
    }

    public String getTeachName(){
        return teachName;
    }

    public String getTeachEmail(){
        return teachEmail;
    }

    public String getTeachCourse(){
        return teachCourse;
    }

    public String getTeachRole(){
        return teachRole;
    }
}
