package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Abstract class Statement provides guidelines for its subclasses, requiring an execute method.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public abstract class Statement
{
    /**
     * Abstract method that executes Statement objects. Must be implemented in subclasses.
     * @param env   The environment in which the Statement will be executed.
     */
    public abstract void exec(Environment env);
    
    /**
     * Abstract method that compiles Statement objects. Must be implemented in subclasses.
     * @param   emitter The emitter used to generate the assembly code file 
     */
    public void compile(Emitter emitter)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}