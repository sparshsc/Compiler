package ast;

import java.util.*;
import java.io.*;
import environment.Environment;
/**
 * Condition class creates an Condition object in an If Then statement. This is the actual
 * condition in the If.
 * 
 * @author Sparsh
 * @version 10/10/17
 */
public class Condition
{
    String relop="";
    Expression exp1;
    Expression exp2;
    /**
     * Constructor for Condition class. Creates Condition projects using a Regular Operation
     * and 2 Expressions.
     * @param   relop   The mathematical operator to be applied to exp1 and exp2.
     * @param   exp1    one of the expressions in the mathematical operation.
     * @param   exp2    the other expression in the mathematical operation.
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop=relop;
        this.exp1=exp1;
        this.exp2=exp2;
    }

    /**
     * Method that evaluates conditions.
     * @param env   The environment in which the If will be executed.
     * @return  true if the condition is true; otherwise, false.
     */
    public boolean eval(Environment env)
    {
        if (relop.equals("="))
            return ((exp1.eval(env))==(exp2.eval(env)));
        if (relop.equals("<>"))
            return ((exp1.eval(env))!=(exp2.eval(env))); 
        if (relop.equals("<"))
            return ((exp1.eval(env))<(exp2.eval(env)));
        if (relop.equals(">"))
            return ((exp1.eval(env))>(exp2.eval(env)));
        if (relop.equals("<="))
            return ((exp1.eval(env))<=(exp2.eval(env)));
        if (relop.equals(">="))
            return ((exp1.eval(env))>=(exp2.eval(env)));
        return false;
    }

    /**
     * Compiles the given condition object by outputting its assembly code
     * based on the condition and if its requirements are met.
     * @param emitter the emitter used to generate the output file
     * @param targetlabel the label used in the if statement
     */
    public void compile(Emitter emitter, String targetlabel)
    {
        exp1.compile(emitter);
        emitter.emitPush("$v0");
        exp2.compile(emitter);
        emitter.emitPop("$t0");

        if (relop.equals("="))
            emitter.emit("bne $t0 $v0 "+targetlabel+" #checking equals");
        if (relop.equals("<"))
            emitter.emit("bge $t0 $v0 "+targetlabel+" #checking less than");
        if (relop.equals(">"))
            emitter.emit("ble $t0 $v0 "+targetlabel+" #checking greater than");
        if (relop.equals("<="))
            emitter.emit("bgt $t0 $v0 "+targetlabel+" #checking less than or equals");
        if (relop.equals(">="))
            emitter.emit("blt $t0 $v0 "+targetlabel+" #checking greater than or equals");
        if (relop.equals("<>"))
            emitter.emit("beq $t0 $v0 "+targetlabel+" #checking not equals");
    }
}
