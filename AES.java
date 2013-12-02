import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.String;
import java.awt.image.*;

public class AES extends JApplet implements ActionListener
{
	private Container myContainer;
	private JTextArea inputText;
	private JTextArea outputText;
	private JScrollPane inputTextScroll;
	private JScrollPane outputTextScroll;
	private BufferedImage inputImage;
	private BufferedImage outputImage;
	private JScrollPane inputImageScroll;
	private JScrollPane outputImageScroll;
	private Graphics inputGraphics;
	private Graphics outputGraphics;
	private JPanel inputGraphicsPanel;
	private JPanel outputGraphicsPanel;
	private JButton encryptButton;
	private JButton decryptButton;
	private JButton loadFileButton;
	private JButton saveFileButton;
	private JPanel buttonPanel;
	private JPanel textPanel;
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JPanel labelPanel;
	private JLabel keyLabel;
	private JTextArea keyInput;
	private int i;
	private String cipherText;
	private String keyString;
	private int[] keyInteger;
	private JCheckBox inputHex;
	private JCheckBox outputHex;
	private JCheckBox keyHex;
	private JLabel loopStatus;
	private JLabel originalKeyLabel;
	private JButton clearWindowsButton;
	private final int sBox[] = new int[] 
	{ 99,124,119,123,242,107,111,197, 48,  1,103, 43,254,215,171,118,
	 202,130,201,125,250, 89, 71,240,173,212,162,175,156,164,114,192,
	 183,253,147, 38, 54, 63,247,204, 52,165,229,241,113,216, 49, 21,
	   4,199, 35,195, 24,150,  5,154,  7, 18,128,226,235, 39,178,117,
	   9,131, 44, 26, 27,110, 90,160, 82, 59,214,179, 41,227, 47,132,
	  83,209,  0,237, 32,252,177, 91,106,203,190, 57, 74, 76, 88,207,
	 208,239,170,251, 67, 77, 51,133, 69,249,  2,127, 80, 60,159,168,
	  81,163, 64,143,146,157, 56,245,188,182,218, 33, 16,255,243,210,
	 205, 12, 19,236, 95,151, 68, 23,196,167,126, 61,100, 93, 25,115,
	  96,129, 79,220, 34, 42,144,136, 70,238,184, 20,222, 94, 11,219,
	 224, 50, 58, 10, 73,  6, 36, 92,194,211,172, 98,145,149,228,121,
	 231,200, 55,109,141,213, 78,169,108, 86,244,234,101,122,174,  8,
	 186,120, 37, 46, 28,166,180,198,232,221,116, 31, 75,189,139,138,
	 112, 62,181,102, 72,  3,246, 14, 97, 53, 87,185,134,193, 29,158,
	 225,248,152, 17,105,217,142,148,155, 30,135,233,206, 85, 40,223,
	 140,161,137, 13,191,230, 66,104, 65,153, 45, 15,176, 84,187, 22};
	 
	private final int invsBox[] = new int[]
	{ 82,  9,106,213, 48, 54,165, 56,191, 64,163,158,129,243,215,251,
	 124,227, 57,130,155, 47,255,135, 52,142, 67, 68,196,222,233,203,
	  84,123,148, 50,166,194, 35, 61,238, 76,149, 11, 66,250,195, 78,
	   8, 46,161,102, 40,217, 36,178,118, 91,162, 73,109,139,209, 37,
	 114,248,246,100,134,104,152, 22,212,164, 92,204, 93,101,182,146,
	 108,112, 72, 80,253,237,185,218, 94, 21, 70, 87,167,141,157,132,
	 144,216,171,  0,140,188,211, 10,247,228, 88,  5,184,179, 69,  6,
	 208, 44, 30,143,202, 63, 15,  2,193,175,189,  3,  1, 19,138,107,
	  58,145, 17, 65, 79,103,220,234,151,242,207,206,240,180,230,115,
	 150,172,116, 34,231,173, 53,133,226,249, 55,232, 28,117,223,110,
	  71,241, 26,113, 29, 41,197,137,111,183, 98, 14,170, 24,190, 27,
	 252, 86, 62, 75,198,210,121, 32,154,219,192,254,120,205, 90,244,
	  31,221,168, 51,136,  7,199, 49,177, 18, 16, 89, 39,128,236, 95,
	  96, 81,127,169, 25,181, 74, 13, 45,229,122,159,147,201,156,239,
	 160,224, 59, 77,174, 42,245,176,200,235,187, 60,131, 83,153, 97,
	  23, 43,  4,126,186,119,214, 38,225,105, 20, 99, 85, 33,12,125};
	 
	private final int logTable[] = new int[]
	{  0,  0, 25,  1, 50,  2, 26,198, 75,199, 27,104, 51,238,223,  3,
	 100,  4,224, 14, 52,141,129,239, 76,113,  8,200,248,105, 28,193,
	 125,194, 29,181,249,185, 39,106, 77,228,166,114,154,201,  9,120,
	 101, 47,138,  5, 33, 15,225, 36, 18,240,130, 69, 53,147,218,142,
	 150,143,219,189, 54,208,206,148, 19, 92,210,241, 64, 70,131, 56,
	 102,221,253, 48,191,  6,139, 98,179, 37,226,152, 34,136,145, 16,
	 126,110, 72,195,163,182, 30, 66, 58,107, 40, 84,250,133, 61,186,
	  43,121, 10, 21,155,159, 94,202, 78,212,172,229,243,115,167, 87,
	 175, 88,168, 80,244,234,214,116, 79,174,233,213,231,230,173,232,
	  44,215,117,122,235, 22, 11,245, 89,203, 95,176,156,169, 81,160,
	 127, 12,246,111, 23,196, 73,236,216, 67, 31 ,45,164,118,123,183,
	 204,187, 62, 90,251, 96,177,134, 59, 82,161,108,170, 85, 41,157,
	 151,178,135,144, 97,190,220,252,188,149,207,205, 55, 63, 91,209,
	  83, 57,132, 60, 65,162,109, 71, 20, 42,158, 93, 86,242,211,171,
	  68, 17,146,217, 35, 32, 46,137,180,124,184, 38,119,153,227,165,
	 103, 74,237,222,197, 49,254, 24, 13, 99,140,128,192,247,112, 7};

	private final int alogTable[] = new int[]
	{  1,  3,  5, 15, 17, 51, 85,255, 26, 46,114,150,161,248, 19, 53,
	  95,225, 56, 72,216,115,149,164,247,  2,  6, 10, 30, 34,102,170,
	 229, 52, 92,228, 55, 89,235, 38,106,190,217,112,144,171,230, 49,
	  83,245,  4, 12, 20, 60, 68,204, 79,209,104,184,211,110,178,205,
	  76,212,103,169,224, 59, 77,215, 98,166,241,  8, 24, 40,120,136,
	 131,158,185,208,107,189,220,127,129,152,179,206, 73,219,118,154,
	 181,196, 87,249, 16, 48, 80,240, 11, 29, 39,105,187,214, 97,163,
	 254, 25, 43,125,135,146,173,236, 47,113,147,174,233, 32, 96,160,
	 251, 22, 58, 78,210,109,183,194, 93,231, 50, 86,250, 21, 63, 65,
	 195, 94,226, 61, 71,201, 64,192, 91,237, 44,116,156,191,218,117,
	 159,186,213,100,172,239, 42,126,130,157,188,223,122,142,137,128,
	 155,182,193, 88,232, 35,101,175,234, 37,111,177,200, 67,197, 84,
	 252, 31, 33, 99,165,244,  7,  9, 27, 45,119,153,176,203, 70,202,
	  69,207, 74,222,121,139,134,145,168,227, 62, 66,198, 81,243, 14,
	  18, 54, 90,238, 41,123,141,140,143,138,133,148,167,242, 13, 23,
	  57, 75,221,124,132,151,162,253, 28, 36,108,180,199, 82,246, 1};

	public void init()
	{
		myContainer = getContentPane();
		myContainer.setLayout(new BorderLayout(0,0));	
		//Verify no borders
		//Take care of button choices first
		encryptButton = new JButton();
		encryptButton.setText("Encrypt Input");
		encryptButton.addActionListener(this);
		decryptButton = new JButton();
		decryptButton.setText("Decrypt Input");
		decryptButton.addActionListener(this);
		keyLabel = new JLabel();
		keyLabel.setText("Enter key here:");
		keyLabel.setHorizontalAlignment(JLabel.RIGHT);
		keyInput = new JTextArea();
//		keyInput.setText("2b7e151628aed2a6abf7158809cf4f3c");
		keyInput.setText("00000000000000000000000000000000");	
		//Initialize key to 0
		inputHex = new JCheckBox("Input in Plain Text Format", true);
		outputHex = new JCheckBox("Output in Plain Text Format", true);
		keyHex = new JCheckBox("Key entered in Plain Text Format", false);
		loopStatus = new JLabel("Loop Status");
		loopStatus.setHorizontalAlignment(JLabel.CENTER);
		originalKeyLabel = new JLabel("");
		originalKeyLabel.setHorizontalAlignment(JLabel.CENTER);
		originalKeyLabel.setText("Original Key Empty");
		loadFileButton = new JButton();
		loadFileButton.setText("Load Input from File");
		loadFileButton.addActionListener(this);
		saveFileButton = new JButton();
		saveFileButton.setText("Save Output to File");
		saveFileButton.addActionListener(this);
		clearWindowsButton = new JButton();
		clearWindowsButton.setText("Clear windows");
		clearWindowsButton.addActionListener(this);
		buttonPanel = new JPanel();		
		//Initialize panel for buttons
		buttonPanel.setLayout(new GridLayout(3,4,5,5));
		buttonPanel.add(encryptButton);
		buttonPanel.add(decryptButton);
		buttonPanel.add(loadFileButton);
		buttonPanel.add(saveFileButton);
		buttonPanel.add(inputHex);
		buttonPanel.add(outputHex);
		buttonPanel.add(keyHex);
		buttonPanel.add(originalKeyLabel);
		buttonPanel.add(keyLabel);
		buttonPanel.add(keyInput);
		buttonPanel.add(loopStatus);
		buttonPanel.add(clearWindowsButton);
		myContainer.add(buttonPanel, BorderLayout.SOUTH);
		//Now that buttons are added, go with text entry areas
		inputText = new JTextArea();
		outputText = new JTextArea();
		inputText.setText("");
		outputText.setText("");
		inputText.setLineWrap(true);
		outputText.setLineWrap(true);
		inputTextScroll = new JScrollPane(inputText);
		outputTextScroll = new JScrollPane(outputText);
		outputText.setEditable(false);
		inputImage = null;
		outputImage = null;
	
		inputGraphicsPanel = new JPanel();
		inputGraphicsPanel.setBackground(new java.awt.Color(255, 255, 255));
		inputImageScroll = new JScrollPane(inputGraphicsPanel);
//		inputImageScroll = new JScrollPane(inputGraphicsPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		inputGraphicsPanel.setPreferredSize(new Dimension(200,200));
		outputGraphicsPanel = new JPanel();
		outputGraphicsPanel.setBackground(new java.awt.Color(255, 255, 255));
		outputImageScroll = new JScrollPane(outputGraphicsPanel);
		
		//Now that basics are defined, add them to the window
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(2,2,5,5));
		textPanel.add(inputTextScroll);
		textPanel.add(outputTextScroll);
		textPanel.add(inputImageScroll);
		textPanel.add(outputImageScroll);
//		textPanel.add(inputGraphics);
		//textPanel.add(inputImageLabel);
		myContainer.add(textPanel, BorderLayout.CENTER);
		//Add labels to the top so that windows are clearly defined
		inputLabel = new JLabel();
		outputLabel = new JLabel();
		inputLabel.setText("Input");
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		outputLabel.setText("Output");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1,2,5,5));
		labelPanel.add(inputLabel);
		labelPanel.add(outputLabel);
		myContainer.add(labelPanel, BorderLayout.NORTH);
		myContainer.setSize(600,400);
		
 	}


	public String processAnyInputText(String a)
	{
		while (a.length() % 32 != 0)
		{
			a = a + "0";
		}
		return a;
	}

	public String textToHex(String a)
	{
		int j = 0;
		int k = 0;
		int l = 0;
		int counter = 0;
		String b = "";
		char[] c = a.toCharArray();
		for (i = 0; i < a.length(); i++)
		{
			loopStatus.setText("Now processing byte " + i);
			j = c[i];
			while (j > 15)
			{
				if (j > 15)
				{
					j = j - 16;
					k++;
				}
			}
			if (k == 0) {b = b + '0';}
			else if (k == 1) {b = b + '1';}
			else if (k == 2) {b = b + '2';}
			else if (k == 3) {b = b + '3';}
			else if (k == 4) {b = b + '4';}
			else if (k == 5) {b = b + '5';}
			else if (k == 6) {b = b + '6';}
			else if (k == 7) {b = b + '7';}
			else if (k == 8) {b = b + '8';}
			else if (k == 9) {b = b + '9';}
			else if (k == 10) {b = b + 'A';}
			else if (k == 11) {b = b + 'B';}
			else if (k == 12) {b = b + 'C';}
			else if (k == 13) {b = b + 'D';}
			else if (k == 14) {b = b + 'E';}
			else {b = b + 'F';}
			if (j == 0) {b = b + '0';}
			else if (j == 1) {b = b + '1';}
			else if (j == 2) {b = b + '2';}
			else if (j == 3) {b = b + '3';}
			else if (j == 4) {b = b + '4';}
			else if (j == 5) {b = b + '5';}
			else if (j == 6) {b = b + '6';}
			else if (j == 7) {b = b + '7';}
			else if (j == 8) {b = b + '8';}
			else if (j == 9) {b = b + '9';}
			else if (j == 10) {b = b + 'A';}
			else if (j == 11) {b = b + 'B';}
			else if (j == 12) {b = b + 'C';}
			else if (j == 13) {b = b + 'D';}
			else if (j == 14) {b = b + 'E';}
			else {b = b + 'F';}
			k = 0;
		
		}		
		return b;
	}

	public String hexToText(String a)
	{
		String b = "";
		char[] c = a.toCharArray();
		int j = 0;
		Character k;
		for (i = 0; i < a.length(); i++)
		{
			loopStatus.setText("Now processing byte " + i);
			if (c[i] == '0') {j = j + 0;}
			else if (c[i] == '1') {j = j + 16;}
			else if (c[i] == '2') {j = j + 32;}
			else if (c[i] == '3') {j = j + 48;}
			else if (c[i] == '4') {j = j + 64;}
			else if (c[i] == '5') {j = j + 80;}
			else if (c[i] == '6') {j = j + 96;}
			else if (c[i] == '7') {j = j + 112;}
			else if (c[i] == '8') {j = j + 128;}
			else if (c[i] == '9') {j = j + 144;}
			else if (c[i] == 'A') {j = j + 160;}
			else if (c[i] == 'B') {j = j + 176;}
			else if (c[i] == 'C') {j = j + 192;}
			else if (c[i] == 'D') {j = j + 208;}
			else if (c[i] == 'E') {j = j + 224;}
			else {j = j + 240;} 
			i++;
			if (c[i] == '0') {j = j + 0;}
			else if (c[i] == '1') {j = j + 1;}
			else if (c[i] == '2') {j = j + 2;}
			else if (c[i] == '3') {j = j + 3;}
			else if (c[i] == '4') {j = j + 4;}
			else if (c[i] == '5') {j = j + 5;}
			else if (c[i] == '6') {j = j + 6;}
			else if (c[i] == '7') {j = j + 7;}
			else if (c[i] == '8') {j = j + 8;}
			else if (c[i] == '9') {j = j + 9;}
			else if (c[i] == 'A') {j = j + 10;}
			else if (c[i] == 'B') {j = j + 11;}
			else if (c[i] == 'C') {j = j + 12;}
			else if (c[i] == 'D') {j = j + 13;}
			else if (c[i] == 'E') {j = j + 14;}
			else {j = j + 15;}
			k = new Character((char)j);
			b = b + k;
			j = 0;
		}
		return b;
	}

	//Assume at this point that ciphertext is in binary format and 
	//has been processed to be in a multiple of 16 bytes
	public int[] textPrep(String a)
	{
		int i;
		int j = 0;
//		int k = 0;
		int counter = 0;
		int[] tempCipher = new int[a.length()/2];
		char[] b = a.toCharArray();
		for (i = 0; i < a.length(); i++)
		{
			if (b[i] == '0') {j = j + 0;}
			else if (b[i] == '1') {j = j + 16;}
			else if (b[i] == '2') {j = j + 32;}
			else if (b[i] == '3') {j = j + 48;}
			else if (b[i] == '4') {j = j + 64;}
			else if (b[i] == '5') {j = j + 80;}
			else if (b[i] == '6') {j = j + 96;}
			else if (b[i] == '7') {j = j + 112;}
			else if (b[i] == '8') {j = j + 128;}
			else if (b[i] == '9') {j = j + 144;}
			else if (b[i] == 'A' || b[i] == 'a') {j = j + 160;}
			else if (b[i] == 'B' || b[i] == 'b') {j = j + 176;}
			else if (b[i] == 'C' || b[i] == 'c') {j = j + 192;}
			else if (b[i] == 'D' || b[i] == 'd') {j = j + 208;}
			else if (b[i] == 'E' || b[i] == 'e') {j = j + 224;}
			else {j = j + 240;}
			i++;
			if (b[i] == '0') {j = j + 0;}
			else if (b[i] == '1') {j = j + 1;}
			else if (b[i] == '2') {j = j + 2;}
			else if (b[i] == '3') {j = j + 3;}
			else if (b[i] == '4') {j = j + 4;}
			else if (b[i] == '5') {j = j + 5;}
			else if (b[i] == '6') {j = j + 6;}
			else if (b[i] == '7') {j = j + 7;}
			else if (b[i] == '8') {j = j + 8;}
			else if (b[i] == '9') {j = j + 9;}
			else if (b[i] == 'A' || b[i] == 'a') {j = j + 10;}
			else if (b[i] == 'B' || b[i] == 'b') {j = j + 11;}
			else if (b[i] == 'C' || b[i] == 'c') {j = j + 12;}
			else if (b[i] == 'D' || b[i] == 'd') {j = j + 13;}
			else if (b[i] == 'E' || b[i] == 'e') {j = j + 14;}
			else {j = j + 15;}
			
			if (counter % 16 == 0) {tempCipher[counter] = j;}
			else if (counter % 16 == 1) {tempCipher[counter+3] = j;}
			else if (counter % 16 == 2) {tempCipher[counter+6] = j;}
			else if (counter % 16 == 3) {tempCipher[counter+9] = j;}
			else if (counter % 16 == 4) {tempCipher[counter-3] = j;}
			else if (counter % 16 == 5) {tempCipher[counter] = j;}
			else if (counter % 16 == 6) {tempCipher[counter+3] = j;}
			else if (counter % 16 == 7) {tempCipher[counter+6] = j;}
			else if (counter % 16 == 8) {tempCipher[counter-6] = j;}
			else if (counter % 16 == 9) {tempCipher[counter-3] = j;}
			else if (counter % 16 == 10) {tempCipher[counter] = j;}
			else if (counter % 16 == 11) {tempCipher[counter+3] = j;}
			else if (counter % 16 == 12) {tempCipher[counter-9] = j;}
			else if (counter % 16 == 13) {tempCipher[counter-6] = j;}
			else if (counter % 16 == 14) {tempCipher[counter-3] = j;}
			else {tempCipher[counter] = j;}
			j = 0;
			counter++;
		}
		return tempCipher;
	}
 	
 	public int[] createKeySet(int[] a)
 	{ 	
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
 		int rCon[];
		rCon = new int[] {1,2,4,8,16,32,64,128,27,54};
 		for (i = 0; i < 10; i++)
 		{
 			//Rotate the byte
 			m = a[(16*i) + 3];
			j = a[(16*i) + 7];
			k = a[(16*i) + 11];
			l = a[(16*i) + 15];
//			c[(16*i) + 3] = k;
//			c[(16*i) + 7] = l;
//			c[(16*i) + 11] = m;
//			c[(16*i) + 15] = j;
			//Substitute the last column with s-box values
			j = sBox[j];
			k = sBox[k];
			l = sBox[l];
			m = sBox[m];
			//Now it's time to do XOR operations
			//First column XOR last column XOR special matrix
			a[(16*i) + 16] = a[(16*i)] ^ j ^ rCon[i];
			a[(16*i) + 20] = a[(16*i)+4] ^ k ^ 0;
			a[(16*i) + 24] = a[(16*i)+8] ^ l ^ 0;
			a[(16*i) + 28] = a[(16*i)+12] ^ m ^ 0;
			//The second column XORs only with previous data now
			a[(16*i) + 17] = a[(16*i)+1] ^ a[(16*i)+16];
			a[(16*i) + 21] = a[(16*i)+5] ^ a[(16*i)+20];
			a[(16*i) + 25] = a[(16*i)+9] ^ a[(16*i)+24];
			a[(16*i) + 29] = a[(16*i)+13] ^ a[(16*i)+28];
			//The third column does the same as the second
			a[(16*i) + 18] = a[(16*i)+2] ^ a[(16*i)+17];
			a[(16*i) + 22] = a[(16*i)+6] ^ a[(16*i)+21];
			a[(16*i) + 26] = a[(16*i)+10] ^ a[(16*i)+25];
			a[(16*i) + 30] = a[(16*i)+14] ^ a[(16*i)+29];
			//Now the fourth column which works as the third did
			a[(16*i) + 19] = a[(16*i)+3] ^ a[(16*i)+18];
			a[(16*i) + 23] = a[(16*i)+7] ^ a[(16*i)+22];
			a[(16*i) + 27] = a[(16*i)+11] ^ a[(16*i)+26];
			a[(16*i) + 31] = a[(16*i)+15] ^ a[(16*i)+30];
 		}
 		return a;
 	}
 	
 	public int[] subBytesRound(int[] a)
 	{
 		for (i = 0; i < a.length; i++)
 		{
 			a[i] = sBox[a[i]];
 		}
 		return a;
 	}
 	
	public int[] invSubBytesRound(int[] a)
	{
		for (i = 0; i < a.length; i++)
		{
			a[i] = invsBox[a[i]];
		}
		return a;
	}
 	
 	public int[] shiftRows(int[] a)
 	{
 		int j = 0;
 		int k = 0;
 		int l = 0;
 		int m = 0;
 		int counter = 0;
 		while (counter < a.length)
 		{
 			//For first row, change nothing
 			//Begin by incrementing counter to 2nd row
 			counter = counter + 4;
 			j = a[counter];
 			k = a[counter+1];
 			l = a[counter+2];
 			m = a[counter+3];
 			a[counter] = k;
 			a[counter+1] = l;
 			a[counter+2] = m;
 			a[counter+3] = j;
 			//Now change third row
 			counter = counter + 4;
			j = a[counter];
			k = a[counter+1];
			l = a[counter+2];
			m = a[counter+3];
			a[counter] = l;
			a[counter+1] = m;
			a[counter+2] = j;
			a[counter+3] = k;
			//Now change fourth row
			counter = counter + 4;
			j = a[counter];
			k = a[counter+1];
			l = a[counter+2];
			m = a[counter+3];
			a[counter] = m;
			a[counter+1] = j;
			a[counter+2] = k;
			a[counter+3] = l;		
			//Reset counter for start of next block
			counter = counter + 4;
 		}
 		return a;
 	}
 	
	public int[] invShiftRows(int[] a)
	{
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		int counter = 0;
		while (counter < a.length)
		{
			//For first row, change nothing
			//Begin by incrementing counter to 2nd row
			counter = counter + 4;
			//Change second row back
			j = a[counter];
			k = a[counter+1];
			l = a[counter+2];
			m = a[counter+3];
			a[counter] = m;
			a[counter+1] = j;
			a[counter+2] = k;
			a[counter+3] = l;
			//Now change third row back
			counter = counter + 4;
			j = a[counter];
			k = a[counter+1];
			l = a[counter+2];
			m = a[counter+3];
			a[counter] = l;
			a[counter+1] = m;
			a[counter+2] = j;
			a[counter+3] = k;
			//Now change fourth row back
			counter = counter + 4;
			j = a[counter];
			k = a[counter+1];
			l = a[counter+2];
			m = a[counter+3];
			a[counter] = k;
			a[counter+1] = l;
			a[counter+2] = m;
			a[counter+3] = j;		
			//Reset counter for start of next block
			counter = counter + 4;
		}
		return a;
	}
 	
 	public int mixMul(int a, int b)
 	{
 		if (a > 0 && b > 0) 
 		{
/* 			int i;
 			int j;
 			int k;
 			int l;
 			i = logTable[a];
 			j = logTable[b];
 			k = (i + j) % 255;
 			l = alogTable[k];
*/
 			return alogTable[(logTable[a] + logTable[b])%255];
 		}
 		else return 0;
 	}
 	
 	public int[] mixColumns(int[] a)
 	{
 		int counter = 0;
 		//temp variables
 		int j = 0;
 		int k = 0;
 		int l = 0;
 		int m = 0;
// 		int temp1 = 0;
// 		int temp2 = 0;
 		while (counter < a.length)
 		{
 			for (i = 0; i < 4; i++)
 			{
// 				temp1 = mixMul(2,a[counter]);
// 				temp2 = mixMul(3,a[counter+4]);
				j = mixMul(2,a[counter]) ^ mixMul(3,a[counter+4]) ^ a[counter+8] ^ a[counter+12];
				k = a[counter] ^ mixMul(2,a[counter+4]) ^ mixMul(3,a[counter+8]) ^ a[counter+12];
				l = a[counter] ^ a[counter+4] ^ mixMul(2,a[counter+8]) ^ mixMul(3,a[counter+12]);
				m = mixMul(3,a[counter]) ^ a[counter+4] ^ a[counter+8] ^ mixMul(2,a[counter+12]);
				a[counter] = j;
				a[counter+4] = k;
				a[counter+8] = l;
				a[counter+12] = m;
				counter++;
				if (counter % 4 == 0) {counter = counter + 12;}
 			}
 		}
 		return a;
 	}
 	
	public int[] invMixColumns(int[] a)
	{
		int counter = 0;
		//temp variables
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
//		int temp1 = 0;
//		int temp2 = 0;
//		int temp3 = 0;
//		int temp4 = 0;
		while (counter < a.length)
		{
			for (i = 0; i < 4; i++)
			{
//				temp1 = mixMul(14,a[counter]);
//				temp2 = mixMul(11,a[counter+4]);
//				temp3 = mixMul(13,a[counter+8]);
//				temp4 = mixMul( 9,a[counter+12]);
				j = mixMul(14,a[counter]) ^ mixMul(11,a[counter+4]) ^ mixMul(13,a[counter+8]) ^ mixMul( 9,a[counter+12]);
				k = mixMul( 9,a[counter]) ^ mixMul(14,a[counter+4]) ^ mixMul(11,a[counter+8]) ^ mixMul(13,a[counter+12]);
				l = mixMul(13,a[counter]) ^ mixMul( 9,a[counter+4]) ^ mixMul(14,a[counter+8]) ^ mixMul(11,a[counter+12]);
				m = mixMul(11,a[counter]) ^ mixMul(13,a[counter+4]) ^ mixMul( 9,a[counter+8]) ^ mixMul(14,a[counter+12]);
				a[counter] = j;
				a[counter+4] = k;
				a[counter+8] = l;
				a[counter+12] = m;
				counter++;
				if (counter % 4 == 0) {counter = counter + 12;}
			}
		}
		return a;
	}
 	
 	
 	public int[] addRoundKey(int[] a, int i, int[] b)
 	{
 		int j = 0;
 		while (j < a.length)
 		{
 			a[j] = a[j] ^ b[(16*i)+(j%16)];
 			j++;
 		}
 		return a;
 	}
 	
 	public String textOutput(int[] a, int textLength)
 	{
 		int i = 0;
 		int j = 0;
 		char k = 0;
 		char l = 0;
 		int counter = 0;
 		char[] b = new char[textLength];
 		for (i = 0; i < textLength/2; i++)
 		{
 			j = 0;
 			while (a[i] > 15)
 			{
 				j++;
 				a[i] = a[i] - 16;
 			}
 			if (j == 0) {k = '0';}
 			else if (j == 1) {k = '1';}
			else if (j == 2) {k = '2';}
			else if (j == 3) {k = '3';}
			else if (j == 4) {k = '4';}
			else if (j == 5) {k = '5';}
			else if (j == 6) {k = '6';}
			else if (j == 7) {k = '7';}
			else if (j == 8) {k = '8';}
			else if (j == 9) {k = '9';}
			else if (j == 10) {k = 'A';}
			else if (j == 11) {k = 'B';}
			else if (j == 12) {k = 'C';}
			else if (j == 13) {k = 'D';}
			else if (j == 14) {k = 'E';}
			else if (j == 15) {k = 'F';}
			if (a[i] == 0) {l = '0';}
			else if (a[i] == 1) {l = '1';}
			else if (a[i] == 2) {l = '2';}
			else if (a[i] == 3) {l = '3';}
			else if (a[i] == 4) {l = '4';}
			else if (a[i] == 5) {l = '5';}
			else if (a[i] == 6) {l = '6';}
			else if (a[i] == 7) {l = '7';}
			else if (a[i] == 8) {l = '8';}
			else if (a[i] == 9) {l = '9';}
			else if (a[i] == 10) {l = 'A';}
			else if (a[i] == 11) {l = 'B';}
			else if (a[i] == 12) {l = 'C';}
			else if (a[i] == 13) {l = 'D';}
			else if (a[i] == 14) {l = 'E';}
			else if (a[i] == 15) {l = 'F';}
			
			if (counter % 16 == 0) 
				{
					b[2*counter] = k;
					b[2*counter + 1] = l;
				}
			else if (counter % 16 == 1)
			{
				b[2*counter+6] = k;
				b[2*counter+7] = l;
			}
			else if (counter % 16 == 2) 
			{
				b[2*counter+12] = k;
				b[2*counter+13] = l;
			}
			else if (counter % 16 == 3) 
			{
				b[2*counter+18] = k;
				b[2*counter+19] = l;
			}
			else if (counter % 16 == 4)
			{	
				b[2*counter-6] = k;
				b[2*counter-5] = l;
			}
			else if (counter % 16 == 5) 
			{
				b[2*counter] = k;
				b[2*counter+1] = l;
			}
			else if (counter % 16 == 6) 
			{
				b[2*counter+6] = k;
				b[2*counter+7] = l;
			}
			else if (counter % 16 == 7) 
			{
				b[2*counter+12] = k;
				b[2*counter+13] = l;
			}
			else if (counter % 16 == 8) 
			{
				b[2*counter-12] = k;
				b[2*counter-11] = l;
			}
			else if (counter % 16 == 9) 
			{
				b[2*counter-6] = k;
				b[2*counter-5] = l;
			}
			else if (counter % 16 == 10) 
			{
				b[2*counter] = k;
				b[2*counter+1] = l;
			}
			else if (counter % 16 == 11) 
			{
				b[2*counter+6] = k;
				b[2*counter+7] = l;}
			else if (counter % 16 == 12) 
			{
				b[2*counter-18] = k;
				b[2*counter-17] = l;
			}
			else if (counter % 16 == 13) 
			{
				b[2*counter-12] = k;
				b[2*counter-11] = l;
			}
			else if (counter % 16 == 14) 
			{
				b[2*counter-6] = k;
				b[2*counter-5] = l;
			}
			else if (counter % 16 == 15) 
			{
				b[2*counter] = k;
				b[2*counter+1] = l;
			}
			counter++;
 		}
//		String c = new String(b.toString());
//		c = b.toString();
		String c = new String(b);
 		return c;
 	}
 	
 	public void createImage(int cipherBytes[], JPanel graphicsPanel)
 	{
 		//Create image of correct size to fit into area given
 		int pixelSize = 1;
 		int width = 480;
 		int height = ((((cipherBytes.length)*pixelSize)/(width))*pixelSize)+pixelSize;
// 		if ((((((cipherBytes.length)*pixelSize)/(width))*pixelSize) % pixelSize) > 0)
// 		{
// 			height = height - pixelSize;
// 		}
 		//Parameters established; create the image
		BufferedImage outputImage = new BufferedImage(width,
		   height,BufferedImage.TYPE_BYTE_GRAY);
		outputImage.createGraphics();
		//Draw the image
		int column = 0;
		int row = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;
		int counter = 0;
		for (i = 0; i < cipherBytes.length; i=i+16)
		{
			int pixelValue[] = {(cipherBytes[i]+(256*cipherBytes[i])+(65536*cipherBytes[i])+(16777216*cipherBytes[i])),
								(cipherBytes[i+4]+(256*cipherBytes[i+4])+(65536*cipherBytes[i+4])+(16777216*cipherBytes[i+4])),
								(cipherBytes[i+8]+(256*cipherBytes[i+8])+(65536*cipherBytes[i+8])+(16777216*cipherBytes[i+8])),
								(cipherBytes[i+12]+(256*cipherBytes[i+12])+(65536*cipherBytes[i+12])+(16777216*cipherBytes[i+12])),
								(cipherBytes[i+1]+(256*cipherBytes[i+1])+(65536*cipherBytes[i+1])+(16777216*cipherBytes[i+1])),
								(cipherBytes[i+5]+(256*cipherBytes[i+5])+(65536*cipherBytes[i+5])+(16777216*cipherBytes[i+5])),
								(cipherBytes[i+9]+(256*cipherBytes[i+9])+(65536*cipherBytes[i+9])+(16777216*cipherBytes[i+9])),
								(cipherBytes[i+13]+(256*cipherBytes[i+13])+(65536*cipherBytes[i+13])+(16777216*cipherBytes[i+13])),
								(cipherBytes[i+2]+(256*cipherBytes[i+2])+(65536*cipherBytes[i+2])+(16777216*cipherBytes[i+2])),
								(cipherBytes[i+6]+(256*cipherBytes[i+6])+(65536*cipherBytes[i+6])+(16777216*cipherBytes[i+6])),
								(cipherBytes[i+10]+(256*cipherBytes[i+10])+(65536*cipherBytes[i+10])+(16777216*cipherBytes[i+10])),
								(cipherBytes[i+14]+(256*cipherBytes[i+14])+(65536*cipherBytes[i+14])+(16777216*cipherBytes[i+14])),
								(cipherBytes[i+3]+(256*cipherBytes[i+3])+(65536*cipherBytes[i+3])+(16777216*cipherBytes[i+3])),
								(cipherBytes[i+7]+(256*cipherBytes[i+7])+(65536*cipherBytes[i+7])+(16777216*cipherBytes[i+7])),
								(cipherBytes[i+11]+(256*cipherBytes[i+11])+(65536*cipherBytes[i+11])+(16777216*cipherBytes[i+11])),
								(cipherBytes[i+15]+(256*cipherBytes[i+15])+(65536*cipherBytes[i+15])+(16777216*cipherBytes[i+15]))};
			for (m = 0; m < 16; m++)
			{
				for (j = 0; j < pixelSize; j++)
				{
					for (k = 0; k < pixelSize; k++)
					{
						outputImage.setRGB((column),(row+k),pixelValue[counter]);
					}
					column++;
				}
				if (column == width)
				{
					column = 0;
					row = row + pixelSize;
				}
				counter++;
			}
			counter = 0;
		}
		//Ensure that white space exists at the end of the blocks.
		while (column > -1)
		{
			for (j = 0; j < pixelSize; j++)
			{
				for (k = 0; k < pixelSize; k++)
				{
					outputImage.setRGB((column),(row+k),0xFFFFFFFF);
				}
				column++;
				if (column == width)
				{
					column = 0;
					row = row + pixelSize;
				}
				if (column == 0)
				{
					column--;
				}
			}
		}
		Graphics g = graphicsPanel.getGraphics();
		g.drawImage(outputImage,0,0,null);
		g.dispose();
 	}
 	
	public void actionPerformed(ActionEvent eListener)
	{
		if (eListener.getSource() == encryptButton) 
		{
			long startTime = System.currentTimeMillis();
			//Initialize stuff
			Graphics g = inputGraphicsPanel.getGraphics();
			g.clearRect(0,0,inputGraphicsPanel.getWidth(),inputGraphicsPanel.getHeight());
			g = outputGraphicsPanel.getGraphics();
			g.clearRect(0,0,outputGraphicsPanel.getWidth(),outputGraphicsPanel.getHeight());		
			g.dispose();
			int loop = 0;
			int[] completeKey;
			completeKey = new int[176];
			//See if plaintext was entered for the key
			keyString = keyInput.getText();
			originalKeyLabel.setText("K:" + keyString);
			if (keyHex.isSelected() == true)
			{
				keyString = textToHex(keyString);
			}
			//Verify that key is not less than 16 bytes, else pad with 0s
			if (keyString.length() == 0)
			{
				keyString = "0";
			}
			while (keyString.length() < 32)
			{
				keyInput.setText(keyString + "0");
				keyString = keyInput.getText();
			}
			keyInteger = textPrep(keyString);
			//Populate the beginning key
			for (i = 0; i < 16; i++)
			{
				completeKey[i] = keyInteger[i];
			}
			//Now that beginning key has been created, 
			//create the rest of the keys
			
			completeKey = createKeySet(completeKey);
			outputText.setText("");
			cipherText = inputText.getText();
			if (inputHex.isSelected() == true)
			{
				cipherText = textToHex(cipherText);
			}
			//Verify that the length is in a block of 16
			if (cipherText.length() == 0)
			{
				cipherText = "0";
			}
			if (cipherText.length() % 32 != 0)
			{
				cipherText = processAnyInputText(cipherText); 
			}
			//inputText.setText(cipherText);
			int cipherBytes[] = new int[cipherText.length()/2];
			cipherBytes = textPrep(cipherText);
			//Create the key schedule now so that it is done
			//before we ever need to use it
			
			//Create the input image
			createImage(cipherBytes, inputGraphicsPanel);
			
			//Now that we have the data stored into an int array, encrypt!
			for (loop = 1; loop < 10; loop++)	//Change back to 10 later
			{
				loopStatus.setText("Now in Round " + loop + ", Part 1");
				cipherBytes = subBytesRound(cipherBytes);
				loopStatus.setText("Now in Round " + loop + ", Part 2");
				cipherBytes = shiftRows(cipherBytes);
				loopStatus.setText("Now in Round " + loop + ", Part 3");
				cipherBytes = mixColumns(cipherBytes);
				loopStatus.setText("Now in Round " + loop + ", Part 4");
				cipherBytes = addRoundKey(cipherBytes, loop, completeKey);
			}
			//Final loop
			loopStatus.setText("Now in Round 10, Part 1");
			cipherBytes = subBytesRound(cipherBytes);
			loopStatus.setText("Now in Round 10, Part 2");
			cipherBytes = shiftRows(cipherBytes);
			loopStatus.setText("Now in Round 10, Part 3");
			cipherBytes = addRoundKey(cipherBytes,10,completeKey);
			createImage(cipherBytes,outputGraphicsPanel);
			cipherText = textOutput(cipherBytes, cipherText.length());
			outputText.setText(cipherText);
			if (outputHex.isSelected() == true)
			{
				cipherText = hexToText(cipherText);
				outputText.setText(cipherText);
			}

			loopStatus.setText("Data Complete");
			//Testing for load times
			long endTime = System.currentTimeMillis();
			loopStatus.setText("Time:  " + (endTime - startTime) + "ms");
//			for (i = 0; i < 256; i++)
//			{
//				outputText.setText(outputText.getText() + "," + sBox[i]);
//			}
//			inputText.setText("Button 1 is active");
//			outputText.setText("");
		}
		else if (eListener.getSource() == decryptButton)
		{
			long startTime = System.currentTimeMillis();
//			Create the key schedule using the given key
			//Initialize stuff
			Graphics g = inputGraphicsPanel.getGraphics();
			g.clearRect(0,0,inputGraphicsPanel.getWidth(),inputGraphicsPanel.getHeight());
			g = outputGraphicsPanel.getGraphics();
			g.clearRect(0,0,outputGraphicsPanel.getWidth(),outputGraphicsPanel.getHeight());
			g.dispose();		
			int loop = 0;
			int[] completeKey;
			completeKey = new int[176];
			outputText.setText("");
			//See if plaintext was entered for the key
			keyString = keyInput.getText();
			originalKeyLabel.setText("K: " + keyString);
			if (keyHex.isSelected() == true)
			{
				keyString = textToHex(keyString);
			}
			//Verify that key is not less than 16 bytes, else pad with 0s
			
			if (keyString.length() == 0)
			{
				keyString = "0";
			}
			//keyString = keyInput.getText();
			while (keyString.length() < 32)
			{
				keyInput.setText(keyString + "0");
				keyString = keyInput.getText();
			}
			keyInteger = textPrep(keyString);
			//Populate the beginning key
			for (i = 0; i < 16; i++)
			{
				completeKey[i] = keyInteger[i];
			}
			//Now that beginning key has been created, 
			//create the rest of the keys
			
			completeKey = createKeySet(completeKey);
			outputText.setText("");
			cipherText = inputText.getText();
			if (inputHex.isSelected() == true)
			{
				cipherText = textToHex(cipherText);
			}
			//Verify that the length of input is in a block of 16
			if (cipherText.length() == 0)
			{
				cipherText = "0";
			}
			if (cipherText.length() % 32 != 0)
			{
				cipherText = processAnyInputText(cipherText); 
			}
			//inputText.setText(cipherText);
			int cipherBytes[] = new int[cipherText.length()/2];
			cipherBytes = textPrep(cipherText);
			createImage(cipherBytes,inputGraphicsPanel);
			//Now that key is in place and text is in, decrypt
			cipherBytes = addRoundKey(cipherBytes,10,completeKey);
			cipherBytes = invShiftRows(cipherBytes);
			cipherBytes = invSubBytesRound(cipherBytes);
			//Now that the special round is over, let's decrypt 9 rounds!
			for (loop = 9; loop > 0; loop--)
			{
				cipherBytes = addRoundKey(cipherBytes,loop,completeKey);
				cipherBytes = invMixColumns(cipherBytes);
				cipherBytes = invShiftRows(cipherBytes);
				cipherBytes = invSubBytesRound(cipherBytes);
			}
			createImage(cipherBytes,outputGraphicsPanel);
			cipherText = textOutput(cipherBytes, cipherText.length());
			outputText.setText(cipherText);
			//outputText.setText(textOutput(cipherBytes, cipherText.length()));
			keyInput.setText(textOutput(completeKey, 32));
			if (outputHex.isSelected() == true)
			{
				cipherText = hexToText(cipherText);
				outputText.setText(cipherText);
			}
		//Testing for load times
		long endTime = System.currentTimeMillis();
		loopStatus.setText("Time:  " + (endTime - startTime) + "ms");

//			inputText.setText("");
//			outputText.setText("Button 2 is active");
		}
		else if (eListener.getSource() == loadFileButton)
		{
			FileInputStream in;
			DataInputStream dataRead;
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			long startTime = System.currentTimeMillis();
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				//Initialize the text buffer
				inputText.setText("");
				File inputFile = fc.getSelectedFile();
				try 
				{
					in = new FileInputStream(inputFile);
					dataRead = new DataInputStream(in);
					int c = 0;
					Character d;
					String tempString;
					try 
					{
						byte[] buffer = new byte[1024*32];
						int ln;
						while((ln = in.read(buffer)) != -1)
						{
						tempString = new String(buffer, 0, ln, "ISO-8859-1");
						inputText.setText(inputText.getText() + tempString);
						}
						//For conversion into a readable format in the applet window
						if (inputHex.isSelected() == false)
						{
							inputText.setText(textToHex(inputText.getText()));
						}

					} catch (IOException e1) {
						loopStatus.setText("Read failed");
					}
					try {
						in.close();
					} catch (IOException e2) {
						loopStatus.setText("Close of read file failed");;
					}
				} catch (FileNotFoundException e) {
					loopStatus.setText("File not found");
				}
			//Testing for load times
			long endTime = System.currentTimeMillis();
			loopStatus.setText("Time:  " + (endTime - startTime) + "ms");
			}
		}
		else if (eListener.getSource() == saveFileButton)
		{
			FileOutputStream out;
			DataOutputStream dataWrite;
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);
			long startTime = System.currentTimeMillis();
			File outputFile = fc.getSelectedFile();
			String saveText;
			saveText = outputText.getText();
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					byte[] outputInt = saveText.getBytes();
					out = new FileOutputStream(outputFile);
					dataWrite = new DataOutputStream( out );
                	dataWrite.writeBytes(saveText);
					dataWrite.close();
				}
				catch (Exception e)
				{
					loopStatus.setText("Error writing to file");
				}
			//Testing for load times
			long endTime = System.currentTimeMillis();
			loopStatus.setText("Time:  " + (endTime - startTime) + "ms");

			}
		}
		else if (eListener.getSource() == clearWindowsButton)
		{
			inputText.setText("");
			outputText.setText("");
			Graphics g = inputGraphicsPanel.getGraphics();
			g.clearRect(0,0,inputGraphicsPanel.getWidth(),inputGraphicsPanel.getHeight());
			g.dispose();
			g = outputGraphicsPanel.getGraphics();
			g.clearRect(0,0,outputGraphicsPanel.getWidth(),outputGraphicsPanel.getHeight());
			g.dispose();		
			originalKeyLabel.setText("Original Key Empty");
			keyInput.setText("00000000000000000000000000000000");
		}
		else
		{
			loopStatus.setText("This is not a good situation.  It doesn't work.");
		}
	}
}