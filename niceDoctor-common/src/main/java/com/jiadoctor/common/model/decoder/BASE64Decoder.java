package com.jiadoctor.common.model.decoder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;

import com.jiadoctor.common.model.encoder.CEStreamExhausted;


public class BASE64Decoder extends CharacterDecoder {
	private static final char[] pem_array = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
		'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
		'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
	private static final byte[] pem_convert_array = new byte[256];
	byte[] decode_buffer = new byte[4];

	protected int bytesPerAtom() {
		return 4;
	}

	protected int bytesPerLine() {
		return 72;
	}

	protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt)
		throws IOException {
		int j = -1;
		int k = -1;
		int m = -1;
		int n = -1;
		int ii = 0;
		if (paramInt < 2)
			throw new CEFormatException("BASE64Decoder: Not enough bytes for an atom.");
		do {
			ii = paramPushbackInputStream.read();
			if (ii == -1)
				throw new CEStreamExhausted();
		} while ((ii == 10) || (ii == 13));
		this.decode_buffer[0] = ((byte) ii);
		int i = readFully(paramPushbackInputStream, this.decode_buffer, 1, paramInt - 1);
		if (i == -1)
			throw new CEStreamExhausted();
		if ((paramInt > 3) && (this.decode_buffer[3] == 61))
			paramInt = 3;
		if ((paramInt > 2) && (this.decode_buffer[2] == 61))
			paramInt = 2;
		switch (paramInt) {
		case 4:
			n = pem_convert_array[(this.decode_buffer[3] & 0xFF)];
		case 3:
			m = pem_convert_array[(this.decode_buffer[2] & 0xFF)];
		case 2:
			k = pem_convert_array[(this.decode_buffer[1] & 0xFF)];
			j = pem_convert_array[(this.decode_buffer[0] & 0xFF)];
		}
		switch (paramInt) {
		case 2:
			paramOutputStream.write((byte) (j << 2 & 0xFC | k >>> 4 & 0x3));
			break;
		case 3:
			paramOutputStream.write((byte) (j << 2 & 0xFC | k >>> 4 & 0x3));
			paramOutputStream.write((byte) (k << 4 & 0xF0 | m >>> 2 & 0xF));
			break;
		case 4:
			paramOutputStream.write((byte) (j << 2 & 0xFC | k >>> 4 & 0x3));
			paramOutputStream.write((byte) (k << 4 & 0xF0 | m >>> 2 & 0xF));
			paramOutputStream.write((byte) (m << 6 & 0xC0 | n & 0x3F));
		}
	}

	static {
		for (int i = 0; i < 255; i++)
			pem_convert_array[i] = -1;
		for (int i = 0; i < pem_array.length; i++)
			pem_convert_array[pem_array[i]] = ((byte) i);
	}
}
