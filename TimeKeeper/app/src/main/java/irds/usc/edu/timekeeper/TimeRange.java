package irds.usc.edu.timekeeper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by madhav on 05/11/16.
 */

public class TimeRange {
    private Date begin = null;
    private Date end = null;

    TimeRange(Date begin){
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public String  getString(SimpleDateFormat sdf){
        String beginStr = "";
        if (begin != null) {
            beginStr = sdf.format(begin);
        }

        String endStr = "";
        if (end != null) {
            endStr = sdf.format(end);
        }

        final StringBuilder sb = new StringBuilder("");
        sb.append( beginStr );
        sb.append(" , ");
        sb.append( endStr );
        return sb.toString();
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

        System.out.println(new TimeRange(new Date()));
    }
}
