package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Block objects basically has a list of Statements to execute.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Block extends Statement
{
    private List<Statement> statements;
    /**
     * Constructor for Assignment class. Creates an Assignment object.
     * @param   statements   The list of statements to execute.
     */
    public Block(List<Statement> statements)
    {
        this.statements=statements;
    }
    
    /**
     * Compiles this block by compiling all of its statements in its list.
     * @param emitter the emitter used to generate the assembly code
     */
    public void compile(Emitter emitter)
    {
        for (int i=0; i<statements.size(); i++)
            statements.get(i).compile(emitter);
    }
    
    /**
     * Method that executes Block objects.
     * @param env   The environment in which the Block will be executed.
     */
    public void exec(Environment env)
    {
        for (int i=0; i<statements.size(); i++)
            statements.get(i).exec(env);
    }
}
