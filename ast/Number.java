package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * The Number class creates Number objects which just have an integer value.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Number extends Expression
{
    private int value;
    /**
     * Constructor for Number class. Creates Number objects which just have an integer value.
     * @param   value  The integer value of the Number object.
     */
    public Number(int value)
    {
        this.value = value;
    }
    
    /**
     * Compiles this number by setting value of v0 to the integer (value).
     * @param emitter the emitter that emits the output code to the file
     */
    public void compile(Emitter emitter)
    {
        emitter.emit("li $v0 "+value);
    }
    
    /**
     * Method that evaluates the value of the Number.
     * @param env   The environment in which the operation will be executed.
     * @return  The integer value of the Number.
     */
    public int eval(Environment env)
    {
        return value;
    }
}
