package TcpSocketCommProto.src;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TCPUtils {
    public static void Log(String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(String.format("[%s] %s", simpleDateFormat.format(calendar.getTime()), message));
    }
}
