package zl.zlClass;

/*
 * @Description: 获取对象内存地址
 * @Param:
 * @Author: zl
 * @Date: 2019-03-21 17:10
 */

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class zlObjAddresser {
    static final Unsafe unsafe = getUnsafe();
    static final boolean is64bit = true;
    public static void printBytes(long objectAddress, int num) {
        for (long i = 0; i < num; i++) {
            int cur = unsafe.getByte(objectAddress + i);
            System.out.print((char) cur);
        }
        System.out.println();
    }
    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ": 0x");
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = is64bit ? 8 : 1;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString( last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }


    public static void main(String[] args) {
        Double[] ascending = new Double[16];
        for(int i=0;i<ascending.length;i++)
            ascending[i] = (double) i;
        String s;
        s="12313";

        System.out.println("Before GC");
        printAddresses("ascending", s);
        s="1231322";
        printAddresses("ascending", s);
        System.gc();
        System.out.println("\nAfter GC");
        printAddresses("ascending", s);


    }
}
