/*
Writting a appkicationn to design the front end for the File packer Unpacker Project
*/
import java.lang.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Front
{
    public static void main(String arg[])
    {
        //call the Window class object
        Window wobj = new Window();

    }//End of main function

}//End of class Front

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//class : Window
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


class Window
{
    

    public Window()         //default constructor of class Window
    {
        //jyachi survat J pasun hotey e.g JFrame is part of swing
        JFrame f = new JFrame(" Marvellous Login ");

        JButton bobj = new JButton(" Submit ");
        //bobj.setBounds(130,100,100, 40 );
        bobj.setBounds(150, 150, 140, 40);
        
        // JButton bobj2 = new JButton("Submit");
        // bobj2.setBounds(150, 190, 140, 40);

        JLabel lobj = new JLabel("File Name : ");
        //lobj.setBounds(50,50, 100,30);
        lobj.setBounds(10, 10, 100, 100);

        JTextField tf = new JTextField();
        //tf.setBounds(50,100, 200,30);
        tf.setBounds(110, 50, 130, 30);

        JLabel lobj2 = new JLabel("Folder Name : ");
        lobj2.setBounds(10, 60, 100, 100 );

        JTextField tf2 = new JTextField();
        tf2.setBounds(110, 90, 130, 30);

        f.add(lobj);
        f.add(bobj);
        f.add(lobj2);
       // f.add(bobj2);
        f.add(tf);
        f.add(tf2);

        f.setSize(400, 400);                        //setSize(width,height). setSize() is a predfined method of JFrame class
        f.setLayout(null);                          //method of JFrame class
        f.setVisible(true);                         //method of JFrame class
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //int EXIT_ON_CLOSE = 3.  Terminal closes when the JFrame is closed . Standard  way to do it . Application window is created and closed also
                                                                //method of JFrame class

        bobj.addActionListener( new ActionListener()                //Register the component with List //component.addActionListner(instance of  Listener Class)
        {
            public void actionPerformed(ActionEvent eobj)         //anonymous function,  Override the actionPerformed() method
            {
              System.out.println("Button Clicked");
              System.out.println("Folder Name : "+ tf2.getText());

              System.out.println("File Entered : " + tf.getText());   //retrive the data entered in the TextField when the button is clicked

              PackerX pobj = new PackerX( tf.getText(), tf2.getText() );    //PackerX class object created and it is called implicly when its constructor is created
                                        //FileName    , FolderName
             f.setVisible(false);
             
             NewWindow newObj = new NewWindow();

            }
        }
        );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//class : NewWindow
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class NewWindow
{
    public NewWindow()
    {
        JFrame fobj2 = new JFrame("New Window");                //new window will be opened when FileName and FolderName is passed and submit buttin is clicked in first window
        
        JLabel Lobj = new JLabel("Packed File: ");              //creating a object of the class JLabel
        Lobj.setBounds(10,10, 50,50);                           //setBounds(x, y, width, height)
        
        JTextField TFobj = new JTextField();
        TFobj.setBounds(70, 20, 110, 30);

        JButton Bobj = new JButton("Submit");                   //Text field
        Bobj.setBounds(60, 80, 110, 30);

        fobj2.add(Lobj);                                        //adding lable to the new window
        fobj2.add(Bobj);
        fobj2.add(TFobj);

        fobj2.setSize(350, 350);                                //setSize(width, height);
        fobj2.setLayout(null);
        fobj2.setVisible(true);
        
        fobj2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Bobj.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent eobj2)
            {
                
                System.out.println("Button on New Window clicked ");
                System.out.println("Packed File name entered is : "+ TFobj.getText() );
                UnpackerX unObj = new UnpackerX( TFobj.getText() );
            }
        }
         );

    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Class : PAckerX
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


class PackerX
{
    public FileOutputStream outstream;
    
    public PackerX(String FileName, String DirName)
    {
        System.out.println("Inside class Packer");
        try
        {
            File outfile = new File(FileName);

            outstream = new FileOutputStream(FileName);

        }
        catch(Exception obj)
        {
            System.out.println(obj);
        }
        
        //call TravelDirectory Function
        TravelDirectoryX(DirName);

    }//End of PackerX class Constructor

    public void TravelDirectoryX(String DirName)
    {
        File dirObj = new File(DirName);

        File arr[] = dirObj.listFiles();

        for(File filename : arr)
        {
            
            if(filename.getName().endsWith(".txt") )
            {
                
                System.out.println(filename.getName());
                
                //call PackFile() function
                PackFile( filename.getAbsolutePath() );

            }
        
        }

    }//End of TravelDirectoryX function

    public void PackFile(String FilePath)
    {
        byte Header[] = new byte[100];                      //will contain the files Absolutepath and length of the data in the file
        byte Buffer[] = new byte[1024];                    //
        
        int length = 0;

        FileInputStream instream;

        File fobj = new File(FilePath);

        String temp = FilePath+" "+fobj.length();
        //String temp = FilePath;

        System.out.println("The length of the string when FilePath and files data length is added is : "+temp.length());

        //Adding white spaces to the string temp which will further converted into the 100 bytes Header
        for(int i = temp.length(); i < 100; i++)
        {
            temp = temp + " ";
        }

        System.out.println("The length of the String ' temp ' when WhiteSpaces is added to it along with FilePath and files data is : "+temp.length());

        Header = temp.getBytes();
        System.out.println("The length of the Header in bytes when converted from the String is : "+Header.length);

        // for(int j = 0; j < Header.length; j++)
        // {
        //     System.out.println(Header[j]);
        // }

       try
       {
            

            instream = new FileInputStream(FilePath);

            outstream.write( Header, 0, Header.length );

            while( ( length = instream.read(Buffer) ) != -1)
            {
                outstream.write( Buffer, 0, length );
            }
            
            instream.close();
            
       }
       catch(Exception obj)
       {
           System.out.println(obj);
       }
       
    }//End of Funtion PackFile

}//End of class Packer

////////////////////////////////////////////////////////////////////////////////////////////////////////////
//class : unPackerX
///////////////////////////////////////////////////////////////////////////////////////////////////////////

class UnpackerX
{
    public UnpackerX(String PackFileName)
    {
        unPacking(PackFileName);
    }

    public void unPacking(String FileSrc)
    {
        byte Header[] = new byte[100];
        int length = 0;

        FileInputStream instream;

        try
        {
            instream = new FileInputStream(FileSrc);

            while( ( length = instream.read(Header, 0, 100) ) > 0)
            {
                //convert the data writen from file from bytes to string
                String str = new String(Header);

                //Herer we get files absulute path and lengthof data in the file as a whole string
                //so find a extension in string form where we would recive only the files name and length of the data in the file
                
                String ext = str.substring(str.lastIndexOf("/"));
                System.out.println("The extension string got is  : " + ext);
                System.out.println("");

                ext = ext.substring(1);
                System.out.println("The extension string when / is removed from it is : " + ext);
                System.out.println("");

                //now we have have name of the file and length of data in the file together as a string.
                //split this string data into array of string 
                String arr[] = ext.split("\\s");

                //Now we have in a array name of file at begin index and length of file in the next index
                //we assign the name of the file to new String 'name' 
                String name = arr[0];

                //we convert the length from string to integer
                int size = Integer.parseInt(arr[1]);

                //Now we create new file from the packed File using FileOutputStream class
                FileOutputStream outstream = new FileOutputStream( name );

                ///now we will read the data from the file in the newly created file
                byte buffer[] = new byte[size];

                instream.read(buffer);
                //now this data read from file is to be wriiten in the newly created file
                outstream.write(buffer, 0, size);
            }
            instream.close();
        }
        catch(Exception obj)
        {
            System.out.println(obj);
        }
    }
}