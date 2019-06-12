package zl.multiThreading;

/*
 * @Description: 多线程竞争例子,为每个http请求分配唯一编号
 * @Param:
 * @Author: zl
 * @Date: 2019/4/24 11:56
 */
import zl.interfaceDir.circularSeqGenerator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestID  implements circularSeqGenerator  {
    //私有变量 单例模式
    private final static RequestID instance = new RequestID();
    private  static short sequence = -1;
    private final static short maxSequence=999;
    //构造函数
    private RequestID(){

    }
    public static  RequestID getInstance(){
        return  instance;
    }

    public synchronized   short nextSequence(){
        if (sequence>=maxSequence) sequence =0;
        else
            sequence++;
        return sequence;
    }
    public String nextId(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String sNow = dateFormat.format(new Date());
        DecimalFormat decimalFormat = new DecimalFormat("000");
        short nextSe= instance.nextSequence();
        return "0049  " +sNow+ "  " + decimalFormat.format(nextSe);
    }
    public static void main(String[] args) {

    }
}
