package zl.zlClass;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * @Description: IO操作
 * @Param:
 * @Author: zl
 * @Date: 2019/4/14 19:55
 */
public class zlIO {
    public static void testInputKeyboard() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        try {
            do {
                s = br.readLine();
                System.out.println(s);
            } while (!s.equals("stop"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void testOutControl() {
        int b;
        b = 'A';
        System.out.write(b);
        System.out.write('\n');
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out), true);
        pw.println("this is a record");
        pw.println(1);
        pw.println(1.23);

    }

    public static void testReadFile() {
        String s = "./data/words3.txt";
        FileInputStream f = null;
        try {
            f = new FileInputStream(s);
            Integer i = 0;
            byte[] b = new byte[10];
            do {
                try {
                    i = f.read(b);
                    System.out.println(zlTypeConvern.byteArrToString(b, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (i != -1);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } finally {
            try {
                if (f != null)
                    f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void testReadWriteFile() {
        String s = "./data/words3.txt";
        String s1 = "./data/words3back.txt";
        FileInputStream f = null;
        FileOutputStream fWrite = null;
        try {
            f = new FileInputStream(s);
            fWrite = new FileOutputStream(s1);
            Integer i = 0;
            byte[] b = new byte[10];
            do {
                try {
                    i = f.read(b);
                    fWrite.write(b);
                    System.out.println(zlTypeConvern.byteArrToString(b, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (i != -1);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } finally {
            try {
                if (f != null)
                    f.close();
                if (fWrite != null)
                    fWrite.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void testAutoClose() {
        String s = "./data/words3.txt";
        String s1 = "./data/words3back.txt";

        try (FileInputStream f = new FileInputStream(s); FileOutputStream fWrite = new FileOutputStream(s1)) {

            Integer i = 0;
            byte[] b = new byte[10];
            do {
                try {
                    i = f.read(b);
                    if (i == -1) break;
                    fWrite.write(b);
                    System.out.println(zlTypeConvern.byteArrToString(b, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (i != -1);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public static void testAssert() {
        for (int i = 0; i < 100; i++) {
            assert i == 45 : "n is o";
            System.out.println(i);
        }
    }

    public static void testFile() {
        // 获取目录下所有文件。
        String dirName = "./data";
        File f = new File(dirName);
        FilenameFilter fft = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {

                return name.endsWith(".bin");
            }
        };

        if (f.isDirectory()) {
            for (String f1 : f.list(fft)) {
                System.out.println(f1);
            }
        } else {

        }
    }

    public static void testBufferInputStream() {
        String s = "ABCDEFGHI@JKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        byte[] buf = s.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        BufferedInputStream bis = new BufferedInputStream(in, 13);
        int i = 0;
        int k;
        try {
            do {
                k = bis.read();
                System.out.print((char) k + " ");
                i++;

                if (i == 4) {
                    bis.mark(8);
                }
            } while (k != -1);

            System.out.println();

            bis.reset();
            k = bis.read();
            while (k != -1) {

                System.out.print((char) k + " ");
                k = bis.read();
            }

            System.out.println();

            bis.reset();
            k = bis.read();
            while (k != -1) {
                System.out.print((char) k + " ");
                k = bis.read();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testPushbackInputStream() {
        String s = "ABCDEFGHI@JKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        byte[] buf = s.getBytes();
        byte[] buf1 = new byte[10];
        int k = 0;
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        PushbackInputStream bis = new PushbackInputStream(in, 10);
        try {

            while (k != -1) {

                k = bis.read();
                //System.out.print((char) k + " ");
                bis.unread(k);
                k = bis.read();
                System.out.print((char) k + " ");
            }
            bis.unread(2);

            k = bis.read();
            System.out.print((char) k + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testSequenceInput() {

    }

    static class myClass implements Serializable {
        private int i = 10;
        private double d = 0.12;

        public void setI(int value) {
            i = value;
        }

        public int getI() {
            return i;
        }
    }

    public static void testObjSeria() {
        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream("D:\\1.txt"));
            myClass myobj = new myClass();
            myobj.setI(10000);
            obj.writeObject(myobj);
            ObjectInputStream objI = new ObjectInputStream(new FileInputStream("D:\\1.txt"));
            myClass myobj1 = (myClass) objI.readObject();
            System.out.println(myobj1.getI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testMap() {
        Path p = Paths.get("./data/1.txt");
        String s = "";
        try (FileChannel fconn = (FileChannel) Files.newByteChannel(p, StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)) {
            MappedByteBuffer map = fconn.map(FileChannel.MapMode.READ_WRITE, 4, 4);

            for (int i = 0; i < 4; i++) {
                map.put((byte) ('d'));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testBuffer() {
        char [] charArr= new char[100];
        for(int i =0;i<23;i++)
            charArr[i]=(char)('A'+i);
        CharBuffer buffer1 = CharBuffer.wrap(charArr);
        System.out.println(buffer1.get());
        System.out.println(charArr[23]);
        System.out.println(buffer1.get());
        System.exit(0);
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.append('1').append('2').append('3').append('4').append('5').append('6').append('7');
        buffer.flip();
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        buffer.compact();
        buffer.append('1');
        buffer.flip();
        for (int i =0;buffer.hasRemaining();i++){
            System.out.println(buffer.get());
        }


    }


    public static void main(String[] args) {
        testBuffer();
    }
}
