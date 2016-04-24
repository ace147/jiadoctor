package com.jiadoctor.common.model.decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;

import com.jiadoctor.common.model.encoder.CEStreamExhausted;


public abstract class CharacterDecoder
{
  protected abstract int bytesPerAtom();

  protected abstract int bytesPerLine();

  protected void decodeBufferPrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
    throws IOException
  {
  }

  protected void decodeBufferSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
    throws IOException
  {
  }

  protected int decodeLinePrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    return bytesPerLine();
  }

  protected void decodeLineSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream)
    throws IOException
  {
  }

  protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    throw new CEStreamExhausted();
  }

  protected int readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    for (int i = 0; i < paramInt2; i++)
    {
      int j = paramInputStream.read();
      if (j == -1)
        return i == 0 ? -1 : i;
      paramArrayOfByte[(i + paramInt1)] = ((byte)j);
    }
    return paramInt2;
  }

  @SuppressWarnings("unused")
public void decodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    int j = 0;
    PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramInputStream);
    decodeBufferPrefix(localPushbackInputStream, paramOutputStream);
    while (true)
      try
      {
        int k = decodeLinePrefix(localPushbackInputStream, paramOutputStream);
        int i = 0;
        if (i + bytesPerAtom() < k)
        {
          decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
          j += bytesPerAtom();
          i += bytesPerAtom();
        }
        else
        {
          if (i + bytesPerAtom() == k)
          {
            decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
            j += bytesPerAtom();
          }
          else
          {
            decodeAtom(localPushbackInputStream, paramOutputStream, k - i);
            j += k - i;
          }
          decodeLineSuffix(localPushbackInputStream, paramOutputStream);
        }
      }
      catch (CEStreamExhausted localCEStreamExhausted)
      {
        break;
      }
    decodeBufferSuffix(localPushbackInputStream, paramOutputStream);
  }

  @SuppressWarnings("deprecation")
public byte[] decodeBuffer(String paramString)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramString.length()];
    paramString.getBytes(0, paramString.length(), arrayOfByte, 0);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    decodeBuffer(localByteArrayInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public byte[] decodeBuffer(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    decodeBuffer(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public ByteBuffer decodeBufferToByteBuffer(String paramString)
    throws IOException
  {
    return ByteBuffer.wrap(decodeBuffer(paramString));
  }

  public ByteBuffer decodeBufferToByteBuffer(InputStream paramInputStream)
    throws IOException
  {
    return ByteBuffer.wrap(decodeBuffer(paramInputStream));
  }
}
