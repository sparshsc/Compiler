package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Assignment objects basically assign the value of Expression exp to the String var 
 * in the HashMap.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Assignment extends Statement
{
    private Expression exp;
    private String var;
    /**
     * Constructor for Assignment class. Creates an Assignment object.
     * @param   var The String keyname in the map.
     * @param   exp The actual value corresponding to var in the map.
     */
    public Assignment(String var, Expression exp)
    {
        this.exp = exp;
        this.var = var;
    }
    
    /**
     * Compiles this assignment by setting the value pf expression to the var label.
     * @param emitter the emitter used to generate the assembly code file
     */
    public void compile(Emitter emitter)
    {
        emitter.emit("lw $t0 "+"var"+var);
        exp.compile(emitter);
        emitter.emit("move $t0 $v0");
        emitter.emit("sw $t0 "+"var"+var); 
    }
    
    /**
     * Method that executes Assignment objects.
     * @param env   The environment in which the Assignment will be executed.
     */
    public void exec(Environment env)
    {
        env.setVariable(var,exp.eval(env));
    }
}
