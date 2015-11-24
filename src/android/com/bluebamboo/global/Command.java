package com.bluebamboo.global;

public class Command {

	public Command(){

	}


	public static final int START_FRAME = -64;
	public static final int END_FRAME = -63;

	public static final byte[] ENQ = new byte[]{-64,5,-63};
	public static final byte[] ACK = new byte[]{-64,-64,-64,6,-63,-63,-63,};



	//public static final byte[] RSP_PHONE = new byte[]
	public static final String OK_RSP_START = "D1"; //OK
	public static final String OK_RSP_END = "11";//end of the frame
	public static final String CANCEL_RSP = "D1 01 10";//cancel



	//C0 C0 C0 C0 48 32 30 30 30 32 20 20 20 20 C1 C1 C1 C1

	//public static final String LOAD_CARD = "C0 C0 C0 C0 48 32 30 30 30 32 20 20 20 20 C1 C1 C1 C1"; //20 SECONDS TIMEOUT

	public static final String LOAD_CARD_P25 = "C0 48 32 30 30 30 32 20 20 20 20 C1"; //20 SECONDS TIMEOUT


	public static final String INPUT_PWD = "02 0d 34 30 31 32 33 34 35 36 37 38 39 30 39 09 49 6e 70 75 74 20 50 49 4e 01 10 23 b1 02 ef 07 b0 c6 12 fb d5 0c fd b7 f6 a2 b0";

	public static final String PROCESS_DATA = "01 00 01 00 08 87 D9 CD F9 28 AB 0C 95 00 08 5E 5D 23 39 72 B3 E2 C9";

	//0x00 --- PCI_ONLINE
	//0x01 --- PCI_OFFLINE, ( CUP Reserved for Old Version)
	//0x02 --- PCI_ONLINE (Reserved for Old version)
	//0x03 --- PCI_OFFLINE (Reserved for Old version)
	//0x04 - 0x7F --- Reserved for future use
	//0x80 --- PCI_ONLINE with PIN change
	//0x81 --- CUP

	public static  byte[] appType_PCI_ONLINE = {(byte)0x00};
	public static  byte[] appType_PCI_OFFLINE = {(byte)0x01};
	public static  byte[] appType_PCI_ONLINE_OLD = {(byte)0x02};
	public static  byte[] appType_PCI_OFFLINE_OLD = {(byte)0x03};
	//public static final byte[] appType_PCI_OFFLINE_OLD = {(byte)0x03};
	public static  byte[] appType_PCI_ONLINE_PIN_CHANGE = {(byte)0x80};
	public static  byte[] appType_CUP = {(byte)0x81};
}
