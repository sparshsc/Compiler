package ast;
import java.io.*;
import java.util.List;

/**
 * Emitter class creates an emitter that takes high level code and prints out its generated
 * assembly code.
 * @author Sparsh Chauhan
 * @version 12/1/17
 */
public class Emitter
{
    private int countif;
    private int countwhile;
    private PrintWriter printer;
    private ProcedureDeclaration current;
    /**
     * Creates an emitter object for writing to a new file with name outputname
     * @param outputname The name of the output file to print to
     */
    public Emitter(String outputname)
    {
        countif=0;
        countwhile=0;
        try
        {
            printer=new PrintWriter(new FileWriter(outputname), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the next # id label for if and while labels
     * @param looptype represents what type of statement it is (if or while)
     * @return the next # id label of while or if statement
     */
    public int nextLabelID(String looptype)
    {
        if(looptype.equals("if"))
        {
            countif=countif+1;
            return countif;
        }
        else
        {
            countwhile=countwhile+1;
            return countwhile;
        }
    }
    
    /**
     * Prints the code to push the value of the parameter register to the stack
     * to the outputfile.
     * @param register the register to push to the stack
     */
    public void emitPush(String register)
    {
        printer.println("\t"+"subu $sp $sp 4");
        printer.println("\t"+"sw "+register+" ($sp) #pushes var to stack");
    }

    /**
     * Prints the code to pop the value of the parameter register on the stack
     * to the outputfile.
     * @param register the register to pop from the stack
     */
    public void emitPop(String register)
    {
        printer.println("\t"+"lw "+register+" ($sp)");
        printer.println("\t"+"addu $sp $sp 4 #pops var from stack");  
    }
    
    /**
     * Prints one line of code to file with proper indentation indented
     * @param mips the converted mips code for printing to the file
     */
    public void emit(String mips)
    {
        if (!mips.startsWith(".") && !mips.endsWith(":"))
            mips="\t"+mips;
        printer.println(mips);
    }

    /**
     * Closes the file.
     */
    public void close()
    {
        printer.close();
    }
    
    /**
     * Remember proc as current procedure context.
     * @param proc the procedure to be remembered
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        current = proc;
    }
    
    /**
     * Clear current procedure context.
     */
    public void clearProcedureContext()
    {
        current = null;
    }
    
    /**
     * Determines whether or not the given variable is a local one.
     * @param varName the name of the variable
     * @return true if the given variable corresponds to a local variable name
     *    else false
     */
    public boolean isLocalVariable(String varName)
    {
        if (current != null)
        {
            List<String> vars = current.getParameters();
            for (int i = 0; i < vars.size(); i++)
            {
                if (varName.equals(vars.get(i)))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Determines the offset of the parameter from $sp
     * @param param the parameter being whose offset is being checked
     */
    public int getOffset(String param)
    {
        List<String> vars = current.getParameters();
        for (int i = 0; i < vars.size(); i++)
        {
            if (param.equals(vars.get(i)))
                return 4 * i;
        }
        return 0;
    }
}