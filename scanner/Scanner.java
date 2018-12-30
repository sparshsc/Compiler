package scanner;

import java.io.*;

/**
 * The input scanner is responsible for reading the input string, determining
 * the individual lexemes according to a given set of rules, and producing a string of tokens.
 * 
 * @author Sparsh Chauhan 
 * @version 9/7/17
 */

public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        try
        {
            getNextChar();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        try
        {
            getNextChar();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * @throws IOException when end of file.
     */
    private void getNextChar() throws IOException
    {
        try
        {
            int aaA0=in.read();
            if (aaA0==-1)
                eof=true;
            else
                currentChar=(char)(aaA0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }           

    }

    /**
     * This method compares a given String to the String currentChar. If these Strings match, then
     * eat advances the input stream by calling getNextChar.
     * @param expected is the String that will be compared to currentChar
     * @throws ScanErrorException thrown if the values expected and currentChar differ.
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected==currentChar)
        {
            try
            {
                getNextChar();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new ScanErrorException("Illegal character - expected <"+currentChar+
                ">and found <"+expected);
        }
    }

    /**
     * This method returns whether or not the Scanner has come across the end of the file
     * @return true if it has not, false if it has
     */
    public boolean hasNext()
    {
        if (eof)
            return false;
        return true;
    }

    /**
     * This method checks to see if a given Char is a digit
     * @param aaA0 is the Char that will be checked to see if it is a digit
     * @return true if aaA0 is a digit, false otherwise
     */
    public static boolean isDigit(char aaA0)
    {
        return (Character.toString(aaA0)).matches("[0-9]");
    }

    /**
     * This method checks to see if a given Char is a letter
     * @param aaA0 is the Char that will be checked to see if it is a letter
     * @return true if aaA0 is a letter, false otherwise
     */
    public static boolean isLetter(char aaA0)
    {
        return (((aaA0>='A') && (aaA0<='Z'))||((aaA0>='a') && (aaA0<='z')));
    }

    /**
     * This method checks to see if a given Char is a whitespace such as " "
     * @param aaA0 is the Char that will be checked to see if it is a white space
     * @return true if aaA0 is a white space, false otherwise
     */
    public static boolean isWhiteSpace(char aaA0)
    {
        return (Character.toString(aaA0)).matches("['''\t' '\r' '\n']");
    }

    /**
     * This method scans currentChar and if and while it is a Digit adds it to a 
     * temp String.
     * @return temp The String of digit or digits in a row.
     */
    private String scanNumber()
    {
        try
        {
            String temp = "";
            while (hasNext() && isDigit(currentChar))
            {
                temp+=currentChar;
                eat(currentChar);
            }
            return temp;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method scans currentChar and if and while it is an Identifier
     * (special character) adds it to a temp String
     * @return temp The String of Identifier or Identifiers in a row.
     */
    private String scanIdentifier()
    {
        try
        {
            String temp = "";
            if (isLetter(currentChar))
            {
                while (hasNext() && ((isDigit(currentChar)) || (isLetter(currentChar)))) 
                {
                    
                    temp+=currentChar;
                    eat(currentChar);
                    //System.out.println(temp);
                }   
            }
            return temp;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method scans currentChar and if and while it is an Operand (Letter then 
     * (Digit or Letter)*) (special character) adds it to a temp String. Also handles
     * cases of single line comments and block comments.
     * @return temp The String of Operand or Operands in a row.
     * @throws ScanErrorException thrown if the values expected and currentChar differ.
     */
    private String scanOperand() throws ScanErrorException
    {
        try
        {
            String temp = "";
            String currentchar = Character.toString(currentChar);
            if (hasNext() && (currentchar.equals(".")))
            {
                eof=true;
                return ".";
            }
            if (hasNext() && currentchar.equals("/"))
            {
                eat(currentChar);
                currentchar = Character.toString(currentChar);
                if (currentchar.equals("/"))
                {
                    while (hasNext() && !currentchar.matches("\\n"))
                    {
                        eat(currentChar);
                        currentchar = Character.toString(currentChar);
                    }
                    if (hasNext() && currentchar.matches("\\n"))
                    {
                        eat(currentChar);
                        return temp;
                    }
                    else if (!hasNext())
                        return temp;

                }
                else if (currentchar.equals("*"))
                {
                    if (hasNext())
                    {
                        eat(currentChar);
                        currentchar = Character.toString(currentChar);
                    }
                    else
                        return "/*";
                    while (hasNext() && !currentchar.matches("\\*"))
                    {
                        eat(currentChar);
                        currentchar = Character.toString(currentChar);
                        if (currentchar.matches("\\*"))
                        {
                            eat(currentChar);
                            currentchar = Character.toString(currentChar);
                            if (currentchar.matches("/"))
                            {
                                eat(currentChar);
                                return temp;
                            }
                        }
                    }
                    if (!hasNext())
                        return temp;
                }
                else
                    return "/";
            }
            if (hasNext() && (currentchar.equals(":")||(currentchar.equals("<"))
                ||(currentchar.equals(">"))))
            {
                String tempcurrentchar=currentchar;
                eat(currentChar);
                currentchar = Character.toString(currentChar);
                if (hasNext() && (currentchar.equals(" ")))
                {
                    eat(currentChar);
                    return tempcurrentchar;
                }
                if (hasNext() && (currentchar.equals("=")))
                {
                    eat(currentChar);
                    return (tempcurrentchar+"=");
                }
                if (hasNext() && (tempcurrentchar.equals("<")) && (currentchar.equals(">")))
                {
                    eat(currentChar);
                    return ("<>");
                }
                else
                    throw new ScanErrorException 
                    (("Illegal character - expected <=,+,-,*,/,%,(,),;,."+
                            "> and found <"+currentChar+">"));
            }
            if (hasNext() && ((currentchar.equals("="))||(currentchar.equals("+"))||
                (currentchar.equals("-"))||(currentchar.equals("*"))||
                (currentchar.equals("%"))||(currentchar.equals("("))||
                (currentchar.equals(")"))||(currentchar.equals(";"))||
                (currentchar.equals("."))||(currentchar.equals(","))))
            {
                temp+=currentChar;
                eat(currentChar);   
            }
            else
                throw new ScanErrorException (("Illegal character - expected <=,+,-,*,/,%,(,),;,."+
                        "> and found <"+currentChar+">"));
            return temp;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method returns the next Token that the Scanner comes across. A token can be a word that 
     * consists of a String of letter, numbers, or SpecialCharacter. The token returned can also be
     * a single digit, a sentence, terminator, a phrase terminator, an unknown character. In the
     * event that currentChar is a white space, it advances currentChar until it is not a white
     * space. If it comes across the end of the File, it returns a String with value of "END".
     * @return a String that is the next String that the Scanner comes across
     */
    public String nextToken() throws ScanErrorException
    {
        try
        {
            while (hasNext() && isWhiteSpace(currentChar))
            {
                eat(currentChar);
            }
            if (eof)
                return "END";
            String currentchar = Character.toString(currentChar);
            if (isDigit(currentChar))
                return (scanNumber());
            else if (isLetter(currentChar))
                return (scanIdentifier());
            else
                return (scanOperand());
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
        }
        return "";
    }    
}
