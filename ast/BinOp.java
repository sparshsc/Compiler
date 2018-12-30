package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * The BinOp class creates BinOp objects which applies an operator between 2 expressions.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;
    /**
     * Constructor for BinOp class. Creates BinOp objects which applies an operator to
     * 2 expressions.
     * @param   op  The operator to be applied to the 2 expressions/
     * @param   exp1    One of the expressions in the operation.
     * @param   exp2    The other expression in the operation.
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1=exp1;
        this.exp2=exp2;
    }

    /**
     * Compiles a BinOp by emitting the assembly code to the output file. 
     * @param emitter the emitter used to generate the output code
     */
    public void compile(Emitter emitter)
    {
        exp1.compile(emitter);
        emitter.emitPush("$v0");
        exp2.compile(emitter);
        emitter.emitPop("$t0");

        if (op.equals("+"))
            emitter.emit("addu $v0 $t0 $v0");
        else if (op.equals("-"))
            emitter.emit("subu $v0 $t0 $v0"); 
        else if (op.equals("*"))
        {
            emitter.emit("mult $t0 $v0");
            emitter.emit("mflo $v0");
        }
        else
        {
            emitter.emit("div $t0 $v0");
            emitter.emit("mflo $v0");
        }  
    }

    /**
     * Method that applies the operators to the 2 expressions.
     * @param env   The environment in which the operation will be executed.
     * @return  The value of the integer after the operator is applied to the 2 Expressions.
     */
    public int eval(Environment env)
    {
        if (op.equals("*"))
            return (exp1.eval(env)*exp2.eval(env));
        if (op.equals("/"))
            return (exp1.eval(env)/exp2.eval(env));
        if (op.equals("+"))
            return (exp1.eval(env)+exp2.eval(env));
        if (op.equals("-"))
            return (exp1.eval(env)-exp2.eval(env));
        return 0;
    }
}
