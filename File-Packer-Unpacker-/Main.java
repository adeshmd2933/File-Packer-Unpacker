/*
Write a application to Demonstarte Packing And Unpacking option on Files and its corresponding Data
*/

import java.lang.*;
import java.util.*;



import java.io.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Main
{
    public static void main(String arg[])
    {
        Scanner sobj = new Scanner(System.in);

        String PackFileName;
        String FileName;
        String DirName;
    
        int choice = 0;

        

        while(true)
        {
            System.out.println("Enter your Choice ");
            
            System.out.println("1 : Packing");
            System.out.println("2 : Unpacking" );
            System.out.println("3 : Exit ");
            
            System.out.println("Choice : ");
            choice = sobj.nextInt();
            
            
            
            
            switch(choice)
            {
                case 1 :
                        System.out.println("Enter the name of the file ");
                        FileName = sobj.next();
                        
                        System.out.println("Enter the name of the Directory ");
                        DirName = sobj.next();

                        //call Packer class object and pass FileName and DirName to it
                        PackerX pobj = new PackerX(FileName, DirName);

                        break;

                case 2 :
                        
                        //Call unpacker class by creating its object
                        System.out.println("Enter the name of the Packed File");
                        PackFileName = sobj.next();

                        UnpackerX Uobj = new UnpackerX(PackFileName);
                        break;

                case 3 :
                        System.out.println("ThankYou for Using Marvellous Packer-Unpacker Application ");
                        System.exit(0);
                        break;

                default : 
                            System.out.println("Error : Wrong Choice Entered ");

            }
        }

    }//End of main method

}//End of class Demo

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
