/**
 * 
 */
package net.brilliance.common;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author  DucBQ
 *
 */
public class ByteCountingOutputStream extends OutputStream {
  private long size = 0; //no of bytes

 @Override
  public void write(int b) throws IOException {
      size++;
  }

 @Override
  public void write(byte[] b, int off, int len) throws IOException {
      size += len;
  }

  public long size() {
      return size;
  }
}
