import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.Raster;
import java.awt.event.*;

class Encode  extends JFrame implements ActionListener, Runnable
{	int arrayalpha[][],arrayred[][],arraygreen[][],arrayblue[][],pixrow[],pixcol[],imgheight,imgwidth;
	String username=System.getProperty("user.name");    
	String message=null,imgloc, outputfile="C:\\Users\\"+username+"\\Desktop\\Decode\\stegoimg.png";
    char ch;
    BufferedImage img;
    File f;

    // Decode
    int wordlen=0,pix[],pixind=0,password;
    String keylocation;

    public static void main(String args[]) throws Exception
    {
        Encode frame=new Encode();
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });

    } // end


    public void getdifference(char ch,int upper,int lower) throws IOException
    {	int chartoint=ch;			//character to ASCII value
        int adx=0,smallloc=0;
        int arraydif[]=new int[(upper-lower)*imgheight];
        int arrayrowind[]=new int[(upper-lower)*imgheight];
        int arraycolind[]=new int[(upper-lower)*imgheight];

        /***store the absolute result and their index number subtracting character ASCII value from red values***/
        for(int rowind=lower;rowind<upper;rowind++)
            for(int colind=0;colind<imgheight;colind++)
            {	arraydif[adx]=Math.abs(arrayred[rowind][colind]-chartoint);
                arrayrowind[adx]=rowind;
                arraycolind[adx]=colind;
                adx++;
            }

	/*Arrange in ascending order due to difference with proper position using bubble sort*/
        for(int tempindex=0;tempindex<(((upper-lower)*imgheight)-1);tempindex++)	//arrange in accending order due to difference with proper position using bubble short
        {	for(int tempindex1=0;tempindex1<(((upper-lower)*imgheight)-1-tempindex);tempindex1++)
        {	if(arraydif[tempindex1]>arraydif[tempindex1+1])
        {
            int temp=arrayrowind[tempindex1];			//sorting row value of select pixel
            arrayrowind[tempindex1]=arrayrowind[tempindex1+1];
            arrayrowind[tempindex1+1]=temp;

            temp=arraycolind[tempindex1];			//sorting column value of select pixel
            arraycolind[tempindex1]=arraycolind[tempindex1+1];
            arraycolind[tempindex1+1]=temp;
        }
        }
        }

        /**********change the red, green and blue value with character binary value********/

        /****first 3 bit****/
        int rownumind=arrayrowind[0];
        int colnumind=arraycolind[0];
        int number=arrayred[rownumind][colnumind];
        number=number & 0xf8;
        int temp=chartoint;
        temp=temp>>5;
        number=(number | temp);
        arrayred[rownumind][colnumind]=number;
        pixrow[pixind]=rownumind;
        pixcol[pixind++]=colnumind;

        /****second 3 bit****/
        rownumind=arrayrowind[1];
        colnumind=arraycolind[1];
        number=arrayred[rownumind][colnumind];
        number=number & 0xf8;
        temp=chartoint;
        temp=temp & 0x1f;
        temp=temp>>2;
        number=(number | temp) ;
        arrayred[rownumind][colnumind]=number;
        pixrow[pixind]=rownumind;
        pixcol[pixind++]=colnumind;

        /****last 2 bit****/
        rownumind=arrayrowind[2];
        colnumind=arraycolind[2];
        number=arrayred[rownumind][colnumind];
        number=number & 0xfc;
        temp=chartoint;
        temp=temp & 0x03;
        number=(number | temp);
        arrayred[rownumind][colnumind]=number;
        pixrow[pixind]=rownumind;
        pixcol[pixind++]=colnumind;

    }



    //////////////////////////////////

    JLabel Lfilename, Lmessage, Ldesign, Ltitle, Lmse, Lpsnr;

    JButton  Bopen, Bencrypt, Bdecrypt,  Bclear;

    JTextArea Amessage, Amse, Apsnr;

    JTextField Tfilename, Tmse, Tpsnr;

    Icon  Iopen;

    String Dkey;

    JFileChooser filechooser;

    File Ofilename, tempfilename;


    int Copened, Cencrypt, Cdecrypt, Cplay, Cstop, Csave;

    Thread t;

    public Encode() throws Exception {

        // frame

        super("Steganography Using Image");
        Container con = getContentPane();
        con.setLayout(null);
	con.setBackground(Color.pink);

        // Basic

        Copened = 0;
        Cencrypt = 0;
        Cdecrypt = 0;
        Cplay = 0;
        Csave = 0;
        Cstop = 0;

        t = new Thread(this);
        t.start();

        // Icons


        Iopen = new ImageIcon("c:/Icon/open.gif");

        // file chooser

        filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // comp

        Ltitle = new JLabel("Steganography Using Image");
        Ltitle.setFont(new Font("Arial Rounded MT Bold",Font.PLAIN,20));
	Ldesign = new JLabel("SUVANKAR SANJIB CHAYAN");
        Lfilename = new JLabel("File Name ");
        Lmessage = new JLabel("Message ");
	Lmse = new JLabel("MSE");
	Lpsnr = new JLabel("PSNR");


        Bopen = new JButton("open");
        Bclear = new JButton("Clear");
        Bencrypt = new JButton("Encoding");
        Bdecrypt = new JButton("Decoding");


        Amessage = new JTextArea();
	Amse = new JTextArea();
	Apsnr = new JTextArea();

        Tfilename = new JTextField();
	Tmse = new JTextField();
	Tpsnr = new JTextField();

        // tool tips

        Tfilename.setToolTipText("Opened filename");

        Bopen.setToolTipText("open");


        Tfilename.setEditable(false);

        // Bounds

        Ltitle.setBounds(255, 30, 280, 25);
        Lfilename.setBounds(100, 100, 100, 25);
        Tfilename.setBounds(100, 125, 230, 25);
        Lmse.setBounds(90,350,80,25);
	Tmse.setBounds(140,350,120,25);
	Amse.setBounds(145,353,110,20);
	Lpsnr.setBounds(90,385,80,25);
	Tpsnr.setBounds(140,385,120,25);
	Apsnr.setBounds(145,388,110,20);
	Lmessage.setBounds(450, 100, 100, 25);
        Amessage.setBounds(450, 125, 300, 220);
        Bclear.setBounds(560, 370, 80, 22);

        Bopen.setBounds(340, 125, 70, 25);

        Bencrypt.setBounds(130, 250, 110, 25);
        Bdecrypt.setBounds(250, 250, 110, 25);

      	Ldesign.setBounds(580, 500, 400, 50);

        // add

        con.add(Ltitle);
       	con.add(Ldesign);
        con.add(Lfilename);
        con.add(Tfilename);
        con.add(Lmse);
	con.add(Tmse);
	con.add(Amse);
	con.add(Lpsnr);
	con.add(Tpsnr);
	con.add(Apsnr);
	con.add(Lmessage);
        con.add(Amessage);
        con.add(Bclear);

        con.add(Bopen);

        con.add(Bencrypt);
        con.add(Bdecrypt);


        // actionListener

        Bclear.addActionListener(this);

        Bopen.addActionListener(this);

        Bencrypt.addActionListener(this);
        Bdecrypt.addActionListener(this);


    } // constr of mainframe

    public void run() {
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent ae) {

        try {

            // Action for encryption button

            if (ae.getSource() == Bencrypt) {
                if (Copened == 1) {

                    message = Amessage.getText();

                        Cencrypt = 1;

			//create folder into desktop
		File folder = new File("C:\\Users\\"+username+"\\Desktop\\Decode");
		folder.mkdir();                        


			// get message info
                    String error =new String(" ");
                    System.out.println("Message Length = "+message.length());
                    message=message.concat(error);
                    pixrow=new int[(message.length()+1)*3];
                    pixrow[pixind]=message.length();
                    pixcol=new int[(message.length()+1)*3];
                    pixcol[pixind++]=message.length();
                    //

                    // get rgb
                    int tempwidth,tempheight,pixelvalue;
                    for(tempwidth=0;tempwidth<imgwidth;tempwidth++)
                        for(tempheight=0;tempheight<imgheight;tempheight++)
                        {	pixelvalue=img.getRGB(tempwidth,tempheight);
                            arrayalpha[tempwidth][tempheight]=(pixelvalue>>24) & 0xff;
                            arrayred[tempwidth][tempheight]=(pixelvalue>>16) & 0xff;
                            arraygreen[tempwidth][tempheight]=(pixelvalue>>8) & 0xff;
                            arrayblue[tempwidth][tempheight]=pixelvalue  & 0xff;
                        }
                    //

                    // encode
                    int temp=(imgwidth-2)/message.length(),upper=temp,lower=0;
                    for(int charindex=0;charindex<message.length();charindex++)
                    {	ch=message.charAt(charindex);
                        getdifference(ch,upper,lower);		//send character to select pixel for hiding
                        lower=upper+1;
                        upper=upper+temp;
                        if(charindex!=message.length()-1)
                            System.out.println((charindex+1)+" character is encoded");
                    }
                    System.out.println("encoded is completed");

                    //

                    // key file
                    FileOutputStream fout=new FileOutputStream("C:\\Users\\"+username+"\\Desktop\\Decode\\key");
                    for(int i=0;i<pixind;i++)
                    {	int num=pixrow[i];
                        int j=2;
                        while(j!=0)
                        {	fout.write(num%100);
                            num/=100;
                            j--;
                        }
                    }
                    for(int i=0;i<pixind;i++)
                    {	int num=pixcol[i];
                        int j=2;
                        while(j!=0)
                        {	fout.write(num%100);
                            num/=100;
                            j--;
                        }
                    }
                    fout.close();
                    //

                    //  stego image
                    f=new File("C:\\Users\\"+username+"\\Desktop\\Decode\\stegoimg.png");
                    int finish=10,finishinc=imgwidth/10;
                    for(int pixrownum=0;pixrownum<imgwidth;pixrownum++)
                    {	for(int pixcolnum=0;pixcolnum<imgheight;pixcolnum++)
                    {
                        // int pixelvalue;
                        pixelvalue = (arrayalpha[pixrownum][pixcolnum] << 24) | (arrayred[pixrownum][pixcolnum] << 16) | (arraygreen[pixrownum][pixcolnum] << 8) | arrayblue[pixrownum][pixcolnum];
                        img.setRGB(pixrownum,pixcolnum,pixelvalue);
                    }
                        if(pixrownum==finishinc)
                        {	System.out.println(finish+" % complete");
                            finishinc=finishinc +(imgwidth/10);
                            finish+=10;
                        }
                    }
                    ImageIO.write(img,"png",f);
                    System.out.println("Output file is "+outputfile);
            
		////// testing psnr
	

		BufferedImage img1,img2;
		File f2;
		File f1= new File(imgloc);	//Original image for mse
		f2=new File(outputfile);
		img1=ImageIO.read(f1);
		img2=ImageIO.read(f2);
		Raster r1=img1.getRaster();
		Raster r2=img2.getRaster();
		int width=img1.getWidth();
		int height=img1.getHeight();
		float tmse=0;
		for(int i=0;i<width;i++)
		{	for(int j=0;j<height;j++)
			{	tmse +=((float)(Math.pow((r1.getSample(i,j,0)-r2.getSample(i,j,0)),2)));
			}
		}
		float mse=tmse/(float)(width*height);
		System.out.println("mse="+mse);
		float psnr=10*(float)(Math.log((255*255)/mse)/Math.log(10));
		System.out.println("psnr="+psnr);
Amse.setText(mse+"");
Apsnr.setText(psnr+"");

			/////
                         JOptionPane.showMessageDialog(this, "Encoding is Done. Check Decode Folder In The Desktop. Your Password Is =  "+ (message.length()-1),
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    //

                } else {
                    JOptionPane.showMessageDialog(this, "File NotOpened",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } // end of Bencrypt

            // Action for Clear button

            else if (ae.getSource() == Bclear) {
                Amessage.setText("");
            } // end of clear button

            // Action for Decrypt button

            else if (ae.getSource() == Bdecrypt) {
                if (Copened == 1) {
                    Dkey = JOptionPane
                            .showInputDialog("Enter The Password For  Decryption");
                    // String type
                    if (Dkey.trim().equals(""))
                        JOptionPane.showMessageDialog(this, "Enter the Key",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        // decrypt message
                         password = Integer.parseInt(Dkey);
                        Cdecrypt = 1;
                        ///
                        BufferedImage img;
                        File f;
                        f=new File(imgloc);
                        img=ImageIO.read(f);
                        ///

                        // getwordlength
                        f=new File(keylocation);
                        BufferedInputStream bin=new BufferedInputStream(new FileInputStream(f));
                        password+=1;
                        pix=new int [100000];
                        int s=0;
                        for(int i=0;i<2;i++)
                        {	s=s*100+bin.read();
                        }
                        int num=0;
                        while(s!=0)
                        {	int r=s%100;
                            num=num*100+r;
                            s/=100;
                        }
                        if(password==num)
                        {
                            f = new File(keylocation);
                            bin = new BufferedInputStream(new FileInputStream(f));
                            s = 0;
                            for(int k=0;k<password*12;k=k+2)
                            {	for(int i=0;i<2;i++)
                            {	s=s*100+bin.read();
                            }
                                 num=0;
                                while(s!=0)
                                {	int r=s%100;
                                    num=num*100+r;
                                    s/=100;
                                }
                                pix[pixind++]=num;
                            }
                            bin.close();
                        }
                        else
                        {
                            System.out.println("Wrong password\n------End-----");
                            System.exit(0);
                        }
                        int detect=0;
                        System.out.println("The Message is....");
                        for(int k=0;k<pixind/2;k=k+3)
                        {
                            detect=k;
                        }
                        for(int k=0;k<pixind/2;k=k+3)
                        {	int i=pix[k+1];
                            int j=pix[(k+2)+password*3];
                            int i1=pix[k+2];
                            int j1=pix[(k+3)+password*3];
                            int i2=pix[k+3];
                            int j2=pix[(k+4)+password*3];
                            //System.out.println(i+" "+j+" "+i1+" "+j1+" "+i2+" "+j2);
                            if(k!=detect)
                                pixeldecode(img,i,j,i1,j1,i2,j2);
                        }
                        System.out.println();
                        System.out.println("------End-----");
                        JOptionPane.showMessageDialog(this, "Decoding Done.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        //
                    }
                } else
                    JOptionPane.showMessageDialog(this, "File NotOpened",
                            "Error", JOptionPane.ERROR_MESSAGE);
            } // end of Decrypt button

            else if (ae.getSource() == Bopen) {
                int r = filechooser.showOpenDialog(this);
                tempfilename = filechooser.getSelectedFile(); // File type
                if (r == JFileChooser.CANCEL_OPTION)
                    JOptionPane.showMessageDialog(this, "File Not Selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    imgloc = tempfilename.getAbsolutePath();
                    keylocation = tempfilename.getParent().concat("\\key");

                    if (!(imgloc.endsWith(".png")))

                        JOptionPane.showMessageDialog(this, "Select Only png",
                                "Error", JOptionPane.ERROR_MESSAGE);

                    else {
                        Copened = 1;
                        Ofilename = tempfilename;
                        Tfilename.setEditable(true);
                        Tfilename.setText(String.valueOf(Ofilename));
                        Tfilename.setEditable(false);
                        // get image info
                        f=new File(imgloc);		//Load image file to File object f
			img=ImageIO.read(f);		//read image from file f and load to image obj img
                        imgwidth=img.getWidth();
                        imgheight=img.getHeight();
                        System.out.println("imgheight = "+imgheight + "  imgwidth = "+imgwidth);
                        arrayalpha=new int[imgwidth][imgheight];
                        arrayred=new int[imgwidth][imgheight];
                        arraygreen=new int[imgwidth][imgheight];
                        arrayblue=new int[imgwidth][imgheight];
                        //
                    }

                }
            } // end of Open button

        } // end try
        catch (Exception e) {
            //
            JOptionPane.showMessageDialog(this, e, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    } // end of actionperformed

    private void pixeldecode(BufferedImage img, int i, int j, int i1, int j1, int i2, int j2) {
        int pixelvalue=img.getRGB(i,j);
        int number=(pixelvalue>>16) & 0xff;
        int word=0;
        number=number<<5;
        word=(word | number) & 0xff;

        pixelvalue=img.getRGB(i1,j1);
        number=(pixelvalue>>16) & 0xff;
        number=number & 0x07;
        number=number<<2;
        word=(word | number) & 0xff;

        pixelvalue=img.getRGB(i2,j2);
        number=(pixelvalue>>16) & 0xff;
        number=number & 0x03;
        word=(word | number) & 0xff;
        char ch=(char)word;
        System.out.print(ch);
        Amessage.append(String.valueOf(ch));
    }

}//end of class
