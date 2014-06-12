package com.linuxense.javadbf;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.GregorianCalendar;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;

import pl.topteam.common.io.MazoviaCharset;

public final class MazoviaDbfFileReader extends DBFReader implements Serializable {
	
	private static final long serialVersionUID = 4155160026699171153L;
	private CharsetDecoder charsetDecoder;
	
	public MazoviaDbfFileReader( InputStream in) throws DBFException {
		super(in);
		
		charsetDecoder = new MazoviaCharset().newDecoder();
	}

	/**
	 * Reads the returns the next row in the DBF stream.
	 * @returns The next row as an Object array. Types of the elements 
	 * 			these arrays follow the convention mentioned in the class description.
	 */
	@Override
	public Object[] nextRecord() throws DBFException {
		if( isClosed) {
			throw new DBFException( "Source is not open");
		}

		Object recordObjects[] = new Object[ this.header.fieldArray.length];
		try {
			boolean isDeleted = false;
			do {
				if( isDeleted) {
					dataInputStream.skip( this.header.recordLength-1);
				}
	
				int t_byte = dataInputStream.readByte();
				if( t_byte == END_OF_DATA) {
					return null;
				}

				isDeleted = (  t_byte == '*');
			} while( isDeleted);
	
			for( int i=0; i<this.header.fieldArray.length; i++) {
				switch( this.header.fieldArray[i].getDataType()) {
					// zmiana dekodowania Stringów
					case 'C':
						byte b_array[] = new byte[ this.header.fieldArray[i].getFieldLength()];
						dataInputStream.read( b_array);
						recordObjects[i] =
								charsetDecoder.decode(
										ByteBuffer.wrap(b_array)).toString().replaceAll("┬", "").trim();
						break;
	
					case 'D':
						byte t_byte_year[] = new byte[ 4];
						dataInputStream.read( t_byte_year);
	
						byte t_byte_month[] = new byte[ 2];
						dataInputStream.read( t_byte_month);
	
						byte t_byte_day[] = new byte[ 2];
						dataInputStream.read( t_byte_day);
	
						try {
							GregorianCalendar calendar = new GregorianCalendar( 
								Integer.parseInt( new String( t_byte_year)),
								Integer.parseInt( new String( t_byte_month)) - 1,
								Integer.parseInt( new String( t_byte_day))
							);
	
							recordObjects[i] = calendar.getTime();
						}
						catch ( NumberFormatException e) {
							/* this field may be empty or may have improper value set */
							recordObjects[i] = null;
						}
						break;
	
					case 'F':
						try {
							byte t_float[] = new byte[ this.header.fieldArray[i].getFieldLength()];
							dataInputStream.read( t_float);
							t_float = Utils.trimLeftSpaces( t_float);
							if( t_float.length > 0 && !Utils.contains( t_float, (byte)'?')) {
								recordObjects[i] = new Float( new String( t_float));
							}
							else {
								recordObjects[i] = null;
							}
						}
						catch( NumberFormatException e) {
							throw new DBFException( "Failed to parse Float: " + e.getMessage());
						}
						break;
	
					case 'N':
						try {
							byte t_numeric[] = new byte[ this.header.fieldArray[i].getFieldLength()];
							dataInputStream.read( t_numeric);
							t_numeric = Utils.trimLeftSpaces( t_numeric);

							if( t_numeric.length > 0 && !Utils.contains( t_numeric, (byte)'?')) {
								recordObjects[i] = new Double( new String( t_numeric));
							}
							else {
								recordObjects[i] = null;
							}
						}
						catch( NumberFormatException e) {
							throw new DBFException( "Failed to parse Number: " + e.getMessage());
						}
						break;
	
					case 'L':
	
						byte t_logical = dataInputStream.readByte();
						if( t_logical == 'Y' || t_logical == 't' || t_logical == 'T' || t_logical == 't') {
							recordObjects[i] = Boolean.TRUE;
						}
						else {
							recordObjects[i] = Boolean.FALSE;
						}
						break;
	
					case 'M':
						recordObjects[i] = new String( "null");
						break;

					default:
						recordObjects[i] = new String( "null");
				}
			}
		}
		catch( EOFException e) {
			return null;
		}
		catch( IOException e) {
			throw new DBFException( e.getMessage());
		}

		return recordObjects;
	}
}