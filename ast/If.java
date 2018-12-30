package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * If class creates an If object in an If Then statement.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class If extends Statement
{
    Condition cond;
    Statement stmt;
    /**
     * Constructor for If class. Creates If projects using a Condition and Statement.
     * @param   cond    Condition in the if-then statement.
     * @param   stmt    The "then" code to be exeecuted if the if condition is true.
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond=cond;
        this.stmt=stmt;
    }
    
    /**
     * Compiles this if statement by emitting the assembly code to the output file.
     * @param emitter The emitter that prints to the output file
     */
    public void compile(Emitter emitter)
    {
        int temp = emitter.nextLabelID("if");
        cond.compile(emitter,"endif"+temp);
        stmt.compile(emitter);
        emitter.emit("endif"+temp+":");
    }
    
    /**
     * Method that executes If objects.
     * @param env   The environment in which the If will be executed.
     */
    public void exec(Environment env)
    {
        if (cond.eval(env))
            stmt.exec(env);
    }
}
