package com.github.cnkeep.dubbo.common.util;

/**
 * @description: 字节操作
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-11 22:8
 * @version: v0.0.1
 **/
public final class Bytes {
    private Bytes() {
    }


    public static void writeShort(short n, byte[] dest, int offset) {
        dest[offset + 0] = (byte) (n >>> 8);
        dest[offset + 1] = (byte) n;
    }

    public static short readShort(byte[] dest, int offset) {
        return (short) (((dest[offset + 0] & 0xFFL) << 8)
                + ((dest[offset + 1] & 0xFFL)) << 0);
    }


    public static void writeInt(int n, byte[] dest, int offset) {
        dest[offset + 0] = (byte) (n >>> 24);
        dest[offset + 1] = (byte) (n >>> 16);
        dest[offset + 2] = (byte) (n >>> 8);
        dest[offset + 3] = (byte) n;
    }

    public static int readInt(byte[] dest, int offset) {
        return ((dest[offset + 0] & 0xFF) << 24)
                + ((dest[offset + 1] & 0xFF) << 16)
                + ((dest[offset + 2] & 0xFF) << 8)
                + ((dest[offset + 3] & 0xFF) << 0);
    }

    public static void writeFloat(float f, byte[] dest, int offset) {
        int n = Float.floatToIntBits(f);
        dest[offset + 0] = (byte) (n >>> 24);
        dest[offset + 1] = (byte) (n >>> 16);
        dest[offset + 2] = (byte) (n >>> 8);
        dest[offset + 3] = (byte) n;
    }

    public static float readFloat(byte[] dest, int offset) {
        int n = ((dest[offset + 0] & 0xFF) << 24)
                + ((dest[offset + 1] & 0xFF) << 16)
                + ((dest[offset + 2] & 0xFF) << 8)
                + ((dest[offset + 3] & 0xFF) << 0);
        return Float.intBitsToFloat(n);
    }

    public static void writeLong(int n, byte[] dest, int offset) {
        dest[offset + 0] = (byte) (n >>> 56);
        dest[offset + 1] = (byte) (n >>> 48);
        dest[offset + 2] = (byte) (n >>> 40);
        dest[offset + 3] = (byte) (n >>> 32);
        dest[offset + 4] = (byte) (n >>> 24);
        dest[offset + 5] = (byte) (n >>> 16);
        dest[offset + 6] = (byte) (n >>> 8);
        dest[offset + 7] = (byte) n;
    }

    public static long readLong(byte[] dest, int offset) {
        return ((dest[offset + 0] & 0xFF) << 56)
                + ((dest[offset + 1] & 0xFF) << 48)
                + ((dest[offset + 2] & 0xFF) << 40)
                + ((dest[offset + 3] & 0xFF) << 32)
                + ((dest[offset + 4] & 0xFF) << 24)
                + ((dest[offset + 5] & 0xFF) << 16)
                + ((dest[offset + 6] & 0xFF) << 8)
                + ((dest[offset + 7] & 0xFF) << 0);
    }

    public static void writeDouble(double d, byte[] dest, int offset) {
        long n = Double.doubleToLongBits(d);
        dest[offset + 0] = (byte) (n >>> 56);
        dest[offset + 1] = (byte) (n >>> 48);
        dest[offset + 2] = (byte) (n >>> 40);
        dest[offset + 3] = (byte) (n >>> 32);
        dest[offset + 4] = (byte) (n >>> 24);
        dest[offset + 5] = (byte) (n >>> 16);
        dest[offset + 6] = (byte) (n >>> 8);
        dest[offset + 7] = (byte) n;
    }

    public static double readDoule(byte[] dest, int offset) {
        long n = ((dest[offset + 0] & 0xFF) << 56)
                + ((dest[offset + 1] & 0xFF) << 48)
                + ((dest[offset + 2] & 0xFF) << 40)
                + ((dest[offset + 3] & 0xFF) << 32)
                + ((dest[offset + 4] & 0xFF) << 24)
                + ((dest[offset + 5] & 0xFF) << 16)
                + ((dest[offset + 6] & 0xFF) << 8)
                + ((dest[offset + 7] & 0xFF) << 0);

        return Double.longBitsToDouble(n);
    }
}
