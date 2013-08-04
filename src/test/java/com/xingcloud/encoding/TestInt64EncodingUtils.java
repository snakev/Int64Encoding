package com.xingcloud.encoding;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Wang Yufei
 * Date: 13-8-4
 * Time: 下午11:31
 * To change this template use File | Settings | File Templates.
 */
public class TestInt64EncodingUtils {

  @Test
  public void testEncoding() {
    long[] numbers = {0l, -1l, 1l, -2l, 2l, -3l, 3l, -10l, 10l, 2147483647l, -2147483648l, 4294967294l, -Long.MAX_VALUE/2, -Long.MAX_VALUE/2, -320l, 320};
    for (long n : numbers) {
      byte[] encodeVal = Int64EncodingUtils.encode(n);
      long decodeVal = Int64EncodingUtils.decode(encodeVal);
      assertEquals(n, decodeVal);
    }
  }
}
