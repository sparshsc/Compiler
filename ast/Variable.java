package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * The Variable class creates Variable objects which are the cover up names of
 * the keys in the map in the environment.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Variable extends Expression
{
    private String name;
    /**
     * Constructor for Variable class. Creates Variable objects and gives them a String value.
     * @param   name  The location name of a key in the map.
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Method that returns the key at location.
     * @param env   The environment in which the operation will be executed.
     * @return  The integer value at location name.
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }

    /**
     * Returns the name of this variable
     * @return name of this variable
     */
    public String getName()
    {
        return name;
    }

    /**
     * Compiles a variable by generating the assembly code for the output file
     * by setting the value of v0 to the value of the variable.
     * @param emitter the emitter used to generate the assembly code file
     */
    public void compile(Emitter emitter)
    {
        //if (!emitter.isLocalVariable(this))
        //{
            emitter.emit("la $t0 "+"var"+name);
            emitter.emit("lw $v0 ($t0)");
        //}
        //else
            //e.emit("
    }
}
