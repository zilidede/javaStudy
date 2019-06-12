package zl.net;

import java.nio.ByteBuffer;

/*
 * @Description: 缓冲区测试
 * @Param:
 * @Author: zl
 * @Date: 2019/5/10 10:25
 */
public class testBuffer {
    public void testCompact(){
        //byte [] bytes ="hello,world".getBytes();
       // ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putChar('z');
        int c =buffer.getChar(1);

        System.out.println(buffer.getChar());
        buffer.putChar('i');
       // buffer.compact();
        System.out.println((char)buffer.get());

        System.out.println((char)buffer.get());
    }
    public static void main(String[] args) {
        testBuffer buffer = new testBuffer();
        buffer.testCompact();
    }
}
