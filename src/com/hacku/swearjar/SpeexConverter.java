package com.hacku.swearjar;


import java.io.File;

import org.xiph.*;
import org.xiph.speex.spi.SpeexAudioFileWriter;
import org.xiph.speex.spi.SpeexAudioFileReader;
import org.xiph.speex.spi.SpeexFormatConvertionProvider;
import org.tritonus.*;
import org.tritonus.sampled.convert.MpegFormatConversionProvider;
import org.tritonus.sampled.file.MpegAudioFileReader;

import android.media.AudioFormat;


public class SpeexConverter {

	public void convertToSpeex(File fileToConvert)
	{
		MpegAudioFileReader reader;
		MpegFormatConversionProvider provider;
		SpeexAudioFileReader speexReader;
		SpeexFormatConvertionProvider speexProvider;
		
		reader.getAudioInputStream(fileToConvert);
		speexProvider.getAudioInputStream(new AudioFormat(), new AudioInputS);
		SpeexAudioFileWriter speexWriter;
		AudioInputStream audioInputStream = null;
	       try {
	            audioInputStream = AudioSystem.getAudioInputStream(srcFile);
	        }
	        catch (Exception e) {
	            System.out.println("Exception: " + e.getMessage());
	            e.printStackTrace ();
	            return;
	       }
	        AudioFormat srcFormat = audioInputStream.getFormat();
	
	        AudioFormat targetFormat =
	            new AudioFormat(SpeexEncoding.SPEEX_Q3 ,
	                            srcFormat.getSampleRate(),
	                            -1, // sample size in bits
	                            srcFormat.getChannels(),
	                            -1, // frame size
	                            -1, // frame rate
	                            srcFormat.isBigEndian());
	        AudioInputStream audioInputStream2 =
	            AudioSystem.getAudioInputStream(targetFormat,
	 audioInputStream);

	}

	
	        
	
}
