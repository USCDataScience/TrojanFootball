package irds.usc.edu.timekeeper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeRecord {
    private Date throwTime = null;


    TimeRecord(Date throwTime){
        this.throwTime = throwTime;
    }


    public Date getThrowTime() {
        return throwTime;
    }


    public String  getString(SimpleDateFormat sdf){

        return sdf.format(throwTime);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

        return  getString(sdf);
    }

    public String fileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return  getString(sdf);
    }

    public static void main(String[] args) {

        System.out.println(new TimeRecord(new Date()));
    }
}
