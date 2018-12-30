package ast;
import java.util.*;
import environment.Environment;

/**
 * This is the class for procedures, which consist of statements and procedures.
 * 
 * @author Sparsh
 * @version 10/26/17
 */
public class Program
{
    List<Statement> statements;
    List<ProcedureDeclaration> procedures;
    List<Variable> variables;

    /**
     * Creates a Program object and initializes its procedures and statements.
     */
    public Program()
    {
        variables = new ArrayList<Variable>();
        statements = new ArrayList<Statement>();
        procedures = new ArrayList<ProcedureDeclaration>();
    }

    /**
     * Adds a variable to this Program.
     * @param variable The variable to be added to this program
     */
    public void addVariable(Variable variable)
    {
        variables.add(variable);
    }

    /**
     * Adds a statement to this Program.
     * @param statement The statement to be added to this program.
     */
    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    /**
     * Adds a procedure to this Program.
     * @param procedure The ProcedureDeclaration to be added to this program.
     */
    public void addProcedure(ProcedureDeclaration procedure)
    {
        procedures.add(procedure);
    }

    /**
     * Evaluates this Program by getting the procedures in "env" and then executing the
     * statements in this Program.
     * @param env The environment to run this Program in.
     */
    public void eval(Environment env)
    {
        for (int i = 0; i < procedures.size(); i++)
            env.setProcedure(procedures.get(i));
        for (int i = 0; i < statements.size(); i++)
            statements.get(i).exec(env);
    }
    
    /**
     * Generates the assembly code for the given outputfile and compiles the statements
     * and variables contained in this program.
     * @param outputfile the compile file
     */
    public void compile(String outputfile)
    {
        Emitter emitter = new Emitter(outputfile);
        emitter.emit(".data");
        for (int i=0; i<variables.size(); i++)
            emitter.emit("var"+variables.get(i).getName()+": .word 0");
        emitter.emit("nl: .asciiz \"\\n\"");
        emitter.emit(".text");
        emitter.emit(".globl main");
        emitter.emit("main:");
        for (int i=0; i<statements.size(); i++)
            statements.get(i).compile(emitter);
        emitter.emit("li $v0 10");
        emitter.emit("syscall   # end");
        for (int i=0; i<statements.size(); i++)
            procedures.get(i).compile(emitter);
    }
}
