import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class AttendenceUI {
    static int teachid;
    static int studid;
    static int subid;
    static String preorabs;
    static String techemail;
    static String teachname;
    static String choices = null;
    static Scanner input= new Scanner(System.in);
    boolean isTeacherLoggedIn = false;
    boolean isPrincipalLoggedIn = false;
    public AttendenceUI(){
        do
        {
            Connection con = GetConnection.getConnection();
            System.out.println("Enter your choice \n 1. Teacher Login \n 2. Student Registration \n 3. Student Attendance \n 4. Overall Attendance \n 5. Particualar Subject Attendance \n 6. Teacher Registration \n 7. Principal Login \n 8. Mark Teacher's Absence \n 9. View Teacher Attendance \n 10. Exit");

            int choice = input.nextInt();
            input.nextLine();

            if (choice < 1 || choice > 10){
                System.out.println("Invalid choice. Please select correct choice");
                break;
            }

            switch (choice){
                case 1:
                    System.out.println("Enter the Teacher ID and Emailid to login:");
                    System.out.print("ID: ");
                    int tid = input.nextInt();
                    input.nextLine();
                    System.out.print("Email ID: ");
                    String email = input.nextLine();

                    AttendenceModel attendenceModel = new AttendenceModel();
                    attendenceModel.setTeachid(tid);
                    attendenceModel.setTechemail(email);

                    con = GetConnection.getConnection();
                        int result = AttendenceDAO.selectQuery(con, attendenceModel);

                        if (result == 1) {
                            System.out.println("Successfully Logged in");
                            isTeacherLoggedIn = true;
                            teachid = tid;

                            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

                            if (TeacherAttendanceDAO.isAttendanceMarked(con, teachid, today)) {
                                if (TeacherAttendanceDAO.isPresent(con, teachid, today)) {
                                    System.out.println("Attendance already marked as present today.");
                                } else {
                                    System.out.println("Login Unsuccessful");
                                    System.out.println("You have been marked as absent today.  Login is not permitted.");
                                    isTeacherLoggedIn = false;
                                    teachid = 0;
                                }
                            } else {
                                TeacherAttendanceModel attendance = new TeacherAttendanceModel();
                                attendance.setTeachId(teachid);
                                attendance.setAttendanceDate(today);
                                attendance.setPresentAbsent("P");

                                int attendanceResult = TeacherAttendanceDAO.markAttendance(con, attendance);
                                if (attendanceResult > 0) {
                                    System.out.println("Attendance Marked Successfully");
                                } else {
                                    System.out.println("Attendance marking failed.");
                                }
                            }

                        } else {
                            System.out.println("Invalid Credentials");
                        }
                    System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                    choices = input.next();
                    break;
                case 2:
                    if(isTeacherLoggedIn){
                        System.out.println(("Enter the Student Details"));
                        System.out.println("Student ID");
                        String sidStr = input.next();
                        input.nextLine();
                        int sid = 0;
                        if(!sidStr.matches("^[0-9]+$")){
                            System.out.println("Invalid Student ID. Please enter Digits only");
                            break;
                        } else {
                            sid = Integer.parseInt(sidStr);
                            if(sid < 0){
                                System.out.println("Invalid Student ID. Please enter positive digits only.");
                                break;
                            }
                        }

                        System.out.println(("Student Name"));
                        String sname = input.nextLine();

                        if(!sname.matches("^[\\p{L} \\-\\']+$")){
                            System.out.println("Invalid Student Name. Please Enter Valid Name");
                            break;
                        }

                        System.out.println(("Student Address"));
                        String saddress = input.nextLine();
                        if(saddress.isEmpty()){
                            System.out.println("Invalid Address details");
                            break;
                        }

                        System.out.println(("Student Phone number"));
                        String phoneStr = input.nextLine();
                        long phono = 0;
                        if (!phoneStr.matches("^[0-9]{10}$")){
                            System.out.println("Invalid phone number. Please enter 10 digits and numbers only.");
                            break;
                        }
                        else {
                            phono =Long.parseLong(phoneStr);
                        }

                        System.out.println(("Student email"));
                        String semail = input.nextLine();
                        if (!semail.matches("^[\\w-\\.]+@gmail.com$")){
                            System.out.println("Invalid email address. Please enter a valid email address.");
                            break;
                        }

                        System.out.println(("Student Course"));
                        String scourse = input.nextLine();
                        if(scourse.isEmpty()){
                            System.out.println("Invalid course. Course cannot be empty.");
                            break;
                        }

                        RegistrationModel registrationModel = new RegistrationModel();
                        registrationModel.setSid(sid);
                        registrationModel.setName(sname);
                        registrationModel.setAddress(saddress);
                        registrationModel.setPhonenumber(phono);
                        registrationModel.setEmail(semail);
                        registrationModel.setCourse(scourse);
                        int sregistration = RegistrationDAO.insertQuery(con,registrationModel);
                        if(sregistration ==0){
                            System.out.println("Not Inserted");
                        }else{
                            System.out.println("Inserted Successful");
                        }
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }else{
                        System.out.println("You must log in as a teacher first!");
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }
                case 3:
                    if(isTeacherLoggedIn) {
                        System.out.println("Enter the Student Id");
                        int id = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter the Subject Id");
                        int subid = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter the Subject");
                        String subject = input.nextLine();
                        System.out.println("Enter the Attendance (Present/Absent)");
                        String preabs = input.next();
                        AttendenceModel attenModel = new AttendenceModel();
                        attenModel.setStudid(id);
                        attenModel.setSubject(subject);
                        attenModel.setPreorabs(preabs);
                        int insertAttendance = AttendenceDAO.insertAttendance(con,attenModel);
                        if(insertAttendance == 1){
                            System.out.println("Inserted Successfully");
                        }else{
                            System.out.println("Not Inserted");
                        }
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }else{
                        System.out.println("You must log in as a teacher first!");
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }
                case 4:
                    if(isTeacherLoggedIn) {
                        System.out.println("Enter the Student ID");
                        int stuid = input.nextInt();
                        AttendenceModel atteModel = new AttendenceModel();
                        atteModel.setStudid(stuid);
                        Map<String, Integer> overallresult = AttendenceDAO.countOverallAttendance(con,atteModel);
                        if (overallresult != null) {
                            System.out.println("Total Present Days: " + overallresult.get("total_present_days"));
                            System.out.println("Subjects Attended: " + overallresult.get("subjects_attended"));
                        } else {
                            System.out.println("Error retrieving attendance data.");
                        }
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }else{
                        System.out.println("You must log in as a teacher first!");
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }
                case 5:
                    if(isTeacherLoggedIn) {
                        System.out.println("Enter the Student ID");
                        int studid= input.nextInt();
                        System.out.println("Enter the Subject ID");
                        int subid = input.nextInt();
                        AttendenceModel attedaModel = new AttendenceModel();
                        attedaModel.setStudid(studid);
                        attedaModel.setSubid(subid);
                        int count = AttendenceDAO.countParticularAttendance(con,attedaModel);
                        if(count == -1){
                            System.out.println("Invalid Student ID");
                        }else{
                            System.out.println("Total Number of Attendence: "+ count);
                        }
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;

                    }else{
                        System.out.println("You must log in as a teacher first!");
                        System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                        choices = input.next();
                        break;
                    }
                case 6:
                    if (isPrincipalLoggedIn)
                    {
                        System.out.println("Enter the New-Staff details: ");
                        System.out.print("Enter the Teacher ID: ");
                        int teachId = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter the Teacher Name: ");
                        String teachName = input.nextLine();
                        System.out.print("Enter the Teacher Mail-id: ");
                        String teachEmail = input.nextLine();
                        System.out.print("Teacher Course: ");
                        String teachCourse = input.nextLine();
                        System.out.print("Teacher Role(Designation): ");
                        String teachRole = input.nextLine();

                        TeacherModel teacher = new TeacherModel(teachId,teachName,teachEmail,teachCourse, teachRole);
                        int teacherRegResult = TeacherDAO.registerTeacher(con,teacher);
                        if(teacherRegResult == 1){
                            System.out.println("Staff details registered successfully");
                        }
                        else {
                            System.out.println("Staff registration failed");
                        }
                    }else{
                        System.out.println("Principal Login is mandatory to register new staff details");
                    }
                    System.out.println("Do you want to continue.... \n press Y/y => YES or N?n => NO ");
                    choices = input.next();
                    break;
                case 7:
                    System.out.println("Enter Principal Credentials");
                    System.out.print("Principal ID: ");
                    int principalId = input.nextInt();
                    input.nextLine();
                    System.out.print("Principal Password: ");
                    String principalPassword = input.nextLine();

                    PrincipalModel principal = new PrincipalModel(principalId,principalPassword);
                    int principalLoginResult = PrincipalDAO.principalLogin(con,principal);

                    if(principalLoginResult == 1){
                        System.out.println("Principal logged in successfully");
                        isPrincipalLoggedIn = true;
                    }else {
                        System.out.println("Invalid Principal Credentials.");
                    }
                    System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                    choices = input.next();
                    break;
                case 8:
                    if (isPrincipalLoggedIn) {
                        System.out.println("Mark Absence:");
                        System.out.print("Enter Teacher ID: ");
                        int teachIdToMarkAbsent = input.nextInt();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String dateStr = input.next();
                        input.nextLine();

                        try {
                            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr); // Correct format: yyyy-MM-dd
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                            if (TeacherAttendanceDAO.isAttendanceMarked(con, teachIdToMarkAbsent, sqlDate)) { // Any record exists
                                if (TeacherAttendanceDAO.isPresent(con, teachIdToMarkAbsent, sqlDate)) { // Check if PRESENT
                                    System.out.println("This ID is already been marked as present today.");
                                } else { 
                                    System.out.println("This ID is already been marked as absent today.");
                                }
                            } else { // No record exists yet
                                System.out.print("Enter the Reason For Absent: ");
                                String reason = input.nextLine();

                                int result1 = TeacherAttendanceDAO.markAbsence(con, teachIdToMarkAbsent, sqlDate, reason);

                                if (result1 > 0) {
                                    System.out.println("Absence marked successfully");
                                } else {
                                    System.out.println("Absence marking failed");
                                }
                            }

                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD this format"); // Correct format
                        }
                    } else {
                        System.out.println("You must login as prinicpal");
                    }
                    System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                    choices = input.next();
                    break;
                case 9:
                    if(isPrincipalLoggedIn){
                        System.out.println("View Teacher Attendance");
                        System.out.print("Enter Teacher ID:");
                        int teacherIdToView = input.nextInt();
                        input.nextLine();

                        con = GetConnection.getConnection();
                        ResultSet rs = TeacherAttendanceDAO.getTeacherAttendance(con,teacherIdToView);

                        if (rs != null) {
                            try {
                                System.out.println("Teacher Attendance: ");
                                System.out.println("-----------------------------------------------------------------------------------------");
                                System.out.printf("%-15s %-15s %-10s %-10s %-20s\n","Teacher ID", "Attendance ID", "Date", "Present/Absent"," Reason");
                                System.out.println("--------------------------------------------------------------------------------------------");

                                while (rs.next()) {
                                    System.out.printf("%-15d %-15d %-15s %-10s  %-20s\n", rs.getInt("teachid"), rs.getInt("attendance_id"), rs.getDate("attendance_date"), rs.getString("present_absent"),rs.getString("reason"));
                                }
                                System.out.println("------------------------------------------------------------------------------------------");

                                rs.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Error retrieving attendance data.");
                        }
                    } else {
                        System.out.println("You must log in as a principal first!");
                    }
                    System.out.print("Do You Want to continue...\npress Y/y => YES or N/n => NO  ");
                    choices = input.next();
                    break;
                case 10:
                //Exit operation
                    return;
                default:
                    System.out.println("choose correct choice");
            }

        }while (choices.equals("Y")|| choices.equals("y"));
    }
}
