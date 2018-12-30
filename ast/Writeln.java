package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Writeln class objects basically print output.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Writeln extends Statement
{
    private Expression exp;
    
    /**
     * Constructor for Writeln class. Creates an Writeln object.
     * @param   exp The expression of whose value will be printed.
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Compiles the given writeln statement by generating the assembly
     * code to print in the output file 
     * @param emitter the emitter used to generate the output code
     */
    public void compile(Emitter emitter)
    {
        exp.compile(emitter);
        emitter.emit("move $a0 $v0");
        emitter.emit("li $v0 1   #prints value in a0");
        emitter.emit("syscall");
        emitter.emit("la $a0 nl");
        emitter.emit("li $v0 4   #prints new line");
        emitter.emit("syscall");
    }
    
    /**
     * Method that executes Writeln objects (prints the output).
     * @param env   The environment in which the Writeln will be executed.
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
