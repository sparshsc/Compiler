package ast;
import java.util.*;
import environment.Environment;

/**
 * This class makes objects that calls procedures and it can also pass parameters.
 * 
 * @author Sparsh Chauhan
 * @version 10/26/17
 */
public class ProcedureCall extends Expression
{
    List<Expression> expressions;
    String procedure;

    /**
     * Creates a ProcedureCall object with name proc.
     * @param proc The name of the procedure.
     */
    public ProcedureCall(String proc)
    {
        procedure=proc;
    }

    /**
     * Creates a ProcedureCall object with name proc and list expressions.
     * @param   name   The name of the procedure
     * @param   exps        The list of parameters to be passed to the procedure.
     */
    public ProcedureCall(String name, List<Expression> exps)
    {
        expressions = exps;
        procedure = name;
    }

    /**
     * Evaluates the procedure call.
     * @param   env The environment to evaluate the procedure in.
     * @return  The value of the procedure or if the procedure does not return a value 0.
     */
    public int eval(Environment env)
    {
        ProcedureDeclaration p = env.getProcedure(procedure);
        List<Integer> tempList = new ArrayList<Integer>();
        Environment tempenv = new Environment(env);
        if (expressions!=null)
        {
            for (int i = 0; i < expressions.size(); i++)
                tempList.add(expressions.get(i).eval(tempenv));
            for (int i = 0; i < expressions.size(); i++)
                tempenv.setVariable(p.getParameters().get(i), tempList.get(i));
        }
        p.exec(tempenv);
        p.getStatement().exec(tempenv);
        return tempenv.getVariable(procedure);
    }

    /**
     * Gets the procedure's name.
     * @return the name of the procedure
     */
    public String getProcedureName()
    {
        return procedure;
    }

    /**
     * Emits a sequence of MIPS instructions for calling a procedure.
     * @param e the emitter that emits the instructions
     */
    public void compile(Emitter e)
    {
        e.emit("subu $sp, $sp, 4");    
        e.emit("sw $ra, ($sp)");
        for (int i = 0; i < expressions.size(); i++)
        {
            e.emit("subu $sp, $sp, 4");    
            e.emit("sw $a" + i + ", ($sp)");
            expressions.get(i).compile(e);
        }
        e.emit("jal proc" + procedure);
        for (int i = 0; i < expressions.size(); i++)
        {
            e.emit("lw $a" + i + ", ($sp)");
            e.emit("addu $sp, $sp, 4");
        }
        e.emit("lw $ra, ($sp)");
        e.emit("addu $sp, $sp, 4");
    }
}
