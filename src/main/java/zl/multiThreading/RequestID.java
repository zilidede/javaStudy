package zl.multiThreading;

/*
 * @Description: 多线程竞争例子,为每个http请求分配唯一编号
 * @Param:
 * @Author: zl
 * @Date: 2019/4/24 11:56
 */
import zl.interfaceDir.circularSeqGenerator;
public class RequestID  implements circularSeqGenerator  {
    //私有变量 单例模式
    private final static RequestID instance = new RequestID();
    private  static short sequence = -1;
    private final static short maxSequence=999;
    //构造函数
    private RequestID(){

    }
    public static RequestID getInstance(){
        return  instance;
    }

    public  short nextSequence(){
        if (sequence>=maxSequence) sequence =0;
        else
            sequence++;
        return sequence;
    }
    public static void main(String[] args) {

    }
}
