package environment;

import java.util.*;
import java.io.*;
import ast.ProcedureDeclaration;
/**
 * The Environment class creates Environment objects which have a map
 * with multiple pieces of data in it of value Integer and label String.
 * 
 * @author Sparsh
 * @version 10/11/17
 */
public class Environment
{
    private Environment environment;
    private Map<String, ProcedureDeclaration> procedure;
    private Map<String, Integer> map = new HashMap<String, Integer>();

    /**
     * Creates a new Environment object. Initializes the HashMap containing all of the variables
     */
    public Environment()
    {
        environment = null;
        procedure = new HashMap<String, ProcedureDeclaration>();
        map = new HashMap<String, Integer>();
    }

    /**
     * Creates a new Environment object. Initializes the HashMap containing all of the variables.
     * @param   env The environment to set the instance variable "environment" to.
     */
    public Environment(Environment env)
    {
        environment = env;
        procedure = new HashMap<String, ProcedureDeclaration>();
        map = new HashMap<String, Integer>();
    }

    /**
     * This method associates the given variable name with the given value.
     * @param   variable    the cover name of the key with the value.
     * @param   value   the integer value, the key.
     */
    public void setVariable(String variable, int value)
    {
        if (getGlobal().isVariablePresent(variable))
            getGlobal().declareVariable(variable,value);
        else
        {
            this.declareVariable(variable,value);
            getGlobal().declareVariable(variable,value);
        }
    }

    /**
     * Checks if the variable is present.
     * @return true if the variable is present in the map; otherwise,
     * false.
     * @param   variable    Variable to check if it's in the map.
     */
    public boolean isVariablePresent(String variable)
    {
        return (map.containsKey(variable));
    }

    /**
     * This method returns the value associated with the given variable name.
     * @param   variable    The cover name of the key in the map.
     * @return  The value, the key in the map.
     */
    public int getVariable(String variable)
    {
        if (!map.containsKey(variable))
            return environment.getVariable(variable);
        else
            return map.get(variable);
    }

    /**
     * In environment, associates the given variable name with the given value
     * @param   variable    The name with which the value is associated with in the map.
     * @param   value       The value of "variable" in map that is assigned.
     */
    public void checkGlobal(String variable, int value)
    {
        if (environment==null)
        {
            if (!map.containsKey(variable))
                declareVariable(variable, value);
        }
        else
            environment.checkGlobal(variable, value);
    }

    /**
     * Returns the global environment.
     * @return  The global environment.
     */
    public Environment getGlobal()
    {
        if (environment==null)
            return this;
        else
            return environment.getGlobal();
    } 

    /**
     * Associates the given variable name with the given value
     * @param variable The name of the variable to be declared
     * @param value The value of the variable to be set
     */
    public void declareVariable(String variable, int value)
    {
        map.put(variable, value);
    }

    /**
     * Sets the value in the map procedure to proc.
     * @param   proc    The ProcedureDeclaration object to put in the procedure map.
     */
    public void setProcedure(ProcedureDeclaration proc)
    {
        if (environment==null)
            procedure.put(proc.getName(), proc);
        else
            environment.setProcedure(proc);
    }

    /**
     * Gets the value associated to "nam" in the procedure map.
     * @param   nam The name in the map that has the value to be returned.
     * @return  the value associated with "nam".
     */
    public ProcedureDeclaration getProcedure(String nam)
    {
        if (environment==null)
            return procedure.get(nam);
        else
            return environment.getProcedure(nam);
    }
}
