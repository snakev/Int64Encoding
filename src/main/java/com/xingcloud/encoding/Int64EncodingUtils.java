package com.xingcloud.encoding;

/**
 * Created with IntelliJ IDEA.
 * User: Wang Yufei
 * Date: 13-8-4
 * Time: 下午11:09
 * To change this template use File | Settings | File Templates.
 */
public class Int64EncodingUtils {
  private final static int SIZEOF_LONG = 64;

  public static byte[] encode(long n) {
    byte[] value = null;
    long zz = zigzag(n);
    byte[] numBytes = toBytes(zz);
    int i = 0;
    for (; i<numBytes.length; i++) {
      if (numBytes[i] != 0) {
        int size = numBytes.length - i;
        value = new byte[size];
        System.arraycopy(numBytes, i, value, 0, size);
        break;
      }
    }
    if (i == numBytes.length) {
      value = new byte[1];
    }
    return value;
  }

  public static long decode(byte[] val) {
    byte[] value = new byte[SIZEOF_LONG];
    System.arraycopy(val, 0, value, SIZEOF_LONG - val.length, val.length);
    return rZigZag(toLong(value, 0, value.length));
  }

  private static long zigzag(long n) {
    return (n << 1) ^ (n >> 63);
  }

  private static long rZigZag(long n) {
    return (n >> 1) ^ (-(n & 1));
  }

  private static long toLong(byte[] bytes, int offset, final int length) {
    if (length != SIZEOF_LONG || offset + length > bytes.length) {
      throw explainWrongLengthOrOffset(bytes, offset, length, SIZEOF_LONG);
    }
    long l = 0;
    for(int i = offset; i < offset + length; i++) {
      l <<= 8;
      l ^= bytes[i] & 0xFF;
    }
    return l;
  }

  private static byte[] toBytes(long val) {
    byte [] b = new byte[8];
    for (int i = 7; i > 0; i--) {
      b[i] = (byte) val;
      val >>>= 8;
    }
    b[0] = (byte) val;
    return b;
  }

  private static IllegalArgumentException
  explainWrongLengthOrOffset(final byte[] bytes,
                             final int offset,
                             final int length,
                             final int expectedLength) {
    String reason;
    if (length != expectedLength) {
      reason = "Wrong length: " + length + ", expected " + expectedLength;
    } else {
      reason = "offset (" + offset + ") + length (" + length + ") exceed the"
              + " capacity of the array: " + bytes.length;
    }
    return new IllegalArgumentException(reason);
  }
}
