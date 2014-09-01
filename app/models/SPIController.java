package models;

import com.pi4j.wiringpi.Spi;

public class SPIController {
	
    private static byte StartCmd = (byte) 0x01;
    private static byte ModeSgl =  (byte) 0x80;
    private static byte ModeDiff = (byte) 0x00;
    public  static byte CH0 = (byte) 0x00;
    public  static byte CH1 = (byte) 0x10;
    public  static byte CH2 = (byte) 0x20;
    public  static byte CH3 = (byte) 0x30;
    public  static byte CH4 = (byte) 0x40;
    public  static byte CH5 = (byte) 0x50;
    public  static byte CH6 = (byte) 0x60;
    public  static byte CH7 = (byte) 0x70;
	
	public static int MinReading = 0;
	public static  int MaxReading = 1023; 
	
	
	public  SPIController (Integer ch){
		
		super();
		
		if (ch == null) {
			ch = 0;
			System.out.println(" ==>> Defaulting to SPI Channel 0");
		}
			
			// setup SPI for communication
	    int fd = Spi.wiringPiSPISetup(ch, 10000000);
	    if (fd <= -1) {
	        System.out.println(" ==>> SPI SETUP FAILED");
	        return;
	    }	
	    System.out.println("==>> SPI Setup Succeeded");
	}
	

    
    public int readAnalogSensor (byte CH){
    	
    	System.out.println("Reading Channel [" + CH + "]");
    	
        // send test ASCII message
        byte packet[] = new byte[3];
        packet[0] = StartCmd;    // address byte
        
        byte ModeCHByte = (byte) ((byte) ModeSgl ^ (byte) CH);
        
 //       System.out.println("Byte 1:" + Integer.toBinaryString((ModeCHByte & 0xFF) + 0x100).substring(1));
        
        packet[1] = ModeCHByte;    // register byte
        packet[2] = 0x00;  // data byte
        
//        System.out.println("Send Byte 0 [" + packet[0] + "]");
//        System.out.println("Send Byte 1 [" + packet[1] + "]");
//        System.out.println("Send Byte 2 [" + packet[2] + "]");

        
        Spi.wiringPiSPIDataRW(Spi.CHANNEL_0, packet, 3);
        
//        System.out.println("Receive Byte 0 [" + packet[0] + "]");
//        System.out.println("Receive Byte 1 [" + packet[1] + "]");
//        System.out.println("Receive Byte 2 [" + packet[2] + "]");
        
//        System.out.println("Packet 1:" + Integer.toBinaryString((packet[1] & 0xFF) + 0x100).substring(1));
//        System.out.println("Packet 2:" + Integer.toBinaryString((packet[2] & 0xFF) + 0x100).substring(1));
        
  
        
        return  (( packet[1] &  0x3) << 8) +  (packet[2] & 0xFF);
        
        
       
    
    }// class readAnalogSensor
		
		
	}

