package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Abstract class Expression provides guidelines for its subclasses, requiring an execute method.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public abstract class Expression
{
    /**
     * Abstract method that executes Expression objects. Must be implemented in subclasses.
     * @param env   The environment in which the Expression will be executed.
     * @return      The integer value of the expression.
     */
    public abstract int eval(Environment env);
    
    /**
     * Abstract method that compiles Expression objects. Must be implemented in subclasses.
     * @param   emitter The emitter used to generate the assembly code file 
     */
    public void compile(Emitter emitter)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
