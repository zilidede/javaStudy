package zl.zlClass;

/*
 * @Description: 线程安全的日志类
 * @Param:
 * @Author: zl
 * @Date: 2019/4/11 21:45
 */
public class zlLog {
    //0 提示 -1 错误 1 警告  level
    static final int ErrorLevel = -1;
    static final int WarmLevel = 1;
    static final int HintLevel = 0;
    private int bufferMaxsize = 1024 * 50;//缓存最大值
    private StringBuffer buffer;

    private zlDate de;
    private String logFilename;

    public zlLog() {
        buffer = new StringBuffer();
        de = new zlDate();
        SetLogFile("./data/zllog.txt");
       // bufferMaxsize = 1;
    }

    public static void testWrite(final  zlLog mylog) {
        for (int i = 0; i < 100; i++) {
            Runnable rb = new Runnable() {
                @Override
                public void run() {
                    synchronized (this)
                    {for (int i = 0; i < 10000000; i++) {
                        mylog.Append(Integer.toString(i) + "这是一个测试信息", HintLevel);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    }
                }
            };
            Thread th = new Thread(rb);

            th.start();

        }
        //*/
        // mylog.write();

    }

    public static void main(String[] args) {
        zlLog mylog = new zlLog();
        zlFileIo.DeteleFile(mylog.GetLogFiles());
        testWrite(mylog);

    }

    private  void write() {
        zlFileIo.WriteFile(logFilename, buffer.toString(), "utf-8");
    }

    private String formatLogs(String writrLog, int logLevel) {
        String s = "";
        Thread th = Thread.currentThread();
        if (logLevel == HintLevel) {
           // s = String.format("线程%s %s Log: Hint %s", th.getName(), de.DateToStr(de.GetNow()), writrLog);
        } else if (logLevel == WarmLevel) {
           // s = String.format("线程%s %s Log: Warming %s", th.getName(), de.DateToStr(de.GetNow()), writrLog);
        } else if (logLevel == ErrorLevel) {
           // s = String.format("线程%s %s Log: Error %s", th.getName(), de.DateToStr(de.GetNow()), writrLog);
        }
        return s;

    }

    public void SetLogFile(String fileName) {
        zlFileIo.CreateFile(fileName);
        logFilename = fileName;
    }

    public String GetLogFiles() {
        return logFilename;
    }

    public  void  Append(String writrLog, int logLevel) {
        synchronized (this) {
            String writeLog = formatLogs(writrLog, logLevel);
            buffer.append(writeLog + '\n');
            if (buffer.length() > bufferMaxsize) {
                write();
                buffer.setLength(0);
                ;
            }

        }
    }

    public enum LogType {
        HINT, WARMING, ERROR
    }

    class RunWrite implements Runnable {
        public void run() {

        }
    }
}
