package zl.globClass;

import java.util.Date;

/*
 * @Description: 将无符号32位类型与时间类型互转
 * @Param:
 * @Author: zl
 * @Date: 2019/5/13 17:29
 */
public class UnixTime {

    private final long value;

    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime() {
        this(System.currentTimeMillis() / 1000l + 2208988800L);
    }

    public long value() {
        return this.value;
    }

    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }

    public static void main(String[] args) {

    }
}
