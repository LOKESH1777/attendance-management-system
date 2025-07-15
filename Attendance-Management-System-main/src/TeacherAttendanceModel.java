import java.util.Date;

public class TeacherAttendanceModel {
    private int attendanceId;
    private int teachId;
    private Date attendanceDate;
    private String presentAbsent;

    public TeacherAttendanceModel() {}

    public int getAttendanceId(){
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId){
        this.attendanceId = attendanceId;
    }

    public int getTeachId(){
        return teachId;
    }

    public void setTeachId(int teachId){
        this.teachId = teachId;
    }

    public /*Date*/ java.sql.Date getAttendanceDate() {
        return (java.sql.Date) attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getPresentAbsent(){
        return presentAbsent;
    }

    public void setPresentAbsent(String presentAbsent) {
        this.presentAbsent = presentAbsent;
    }
}
