package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * While class creates a While object in an While Do statement.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class While extends Statement
{
    Condition cond;
    Statement stmt;
    /**
     * Constructor for While class. Creates While projects using a Condition and Statement.
     * @param   cond    Condition in the while do statement to be repeatedly checked.
     * @param   stmt    The "do" code to be exeecuted while the condition is true.
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond=cond;
        this.stmt=stmt;
    }
    
    /**
     * Compiles this while statement by printing the assembly code to the output file.
     * @param emitter The emitter that prints to the output file
     */
    public void compile(Emitter emitter)
    {
        int temp=emitter.nextLabelID("while");
        emitter.emit("while"+temp+":");
        cond.compile(emitter,"endwhile"+temp);
        stmt.compile(emitter);
        emitter.emit("j while"+temp);
        emitter.emit("endwhile"+temp+":");
    }
    
    /**
     * Method that executes While objects.
     * @param env   The environment in which the If will be executed.
     */
    public void exec(Environment env)
    {
        //System.out.println(cond.eval(env));
        while ((cond.eval(env)))
        {
            stmt.exec(env);
            //System.out.println("Hi");
        }
    }
}
