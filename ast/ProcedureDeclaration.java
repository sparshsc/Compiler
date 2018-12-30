package ast;
import java.util.*;
import environment.Environment;

/**
 * This class makes objects that make or declare procedures of two types.
 * One type of procedure is with a list, and the other is without a list.
 * 
 * @author Sparsh Chauhan
 * @version 10/26/17
 */
public class ProcedureDeclaration extends Statement
{
    String procedure;
    Statement statement;
    List<String> parameters;

    /**
     * Creates a ProcedureDeclaration object with name "procedure", with no parameters,
     * and statement "statement" (of the procedure).
     * @param   procedure   The name of the procedure.
     * @param   statement   The statement of the procedure.
     */
    public ProcedureDeclaration(String procedure, Statement statement)
    {
        this.procedure = procedure;
        this.statement = statement;
    }

//     /**
//      * Creates a ProcedureDeclaration object with name "procedure", with parameters "parameters",
//      * and statement "statement" (of the procedure).
//      * @param   procedure   The name of the procedure.
//      * @param   statement   The statement of the procedure.
//      * @param   parameters  The parameters of the procedure.
//      */
//     public ProcedureDeclaration(String procedure, Statement statement, List<String> parameters)
//     {
//         this.procedure = procedure;
//         this.statement = statement;
//         this.parameters = parameters;
//     }
    
    /**
     * Creates a ProcedureDeclaration object with name "procedure", with parameters "parameters",
     * and statement "statement" (of the procedure).
     * @param   procedure   The name of the procedure.
     * @param   statement   The statement of the procedure.
     * @param   parameters  The parameters of the procedure.
     */
    public ProcedureDeclaration(String procedure, List<String> parameters, Statement statement)
    {
        this.procedure = procedure;
        this.statement = statement;
        this.parameters = parameters;
    }

    /**
     * Retrieves the String name of the procedure.
     * @return  The name of the procedure.
     */
    public String getName()
    {
        return procedure;
    }

    /**
     * Retrieves the Statement of the procedure (the code of the procedure).
     * @return  The Statement of the procedure.
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Retrieves the Parameters of the procedure (what is passed into the procedure).
     * @return  The parameters (they are all stored in a list).
     */
    public List<String> getParameters()
    {
        return parameters;
    }

    /**
     * Executes the procedure in the environment "env".
     * @param   env The environment to execute the procedure in.
     */
    public void exec(Environment env)
    {
        env.setProcedure(this);
        env.setVariable(procedure,0);
    }
    
    /**
     * Emits a sequence of MIPS instructions for declaring a procedure.
     * @param e the emitter that emits the instructions
     */
    public void compile(Emitter e)
    {
        e.setProcedureContext(this);
        e.emit("proc" + procedure + ":");
        statement.compile(e);
        e.emit("jr $ra");
        e.clearProcedureContext();
    }
}
