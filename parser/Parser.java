package parser;

import java.util.*;
import java.io.*;
import scanner.Scanner;
import scanner.ScanErrorException;
import ast.Number;
import ast.Variable;
import ast.BinOp;
import ast.Writeln;
import ast.Assignment;
import ast.Block;
import ast.Statement;
import ast.Expression;
import environment.Environment;
import ast.If;
import ast.Condition;
import ast.While;
import ast.Program;
import ast.ProcedureDeclaration;
import ast.ProcedureCall;

/**
 * A simple parser, which will execute Pascal-like statements as it parses them.
 * 
 * @author Sparsh Chauhan 
 * @version 9/23/17
 */
public class Parser
{
    private Map<String, Integer> map;
    private Scanner scan;
    private String str;
    /**
     * Parser constructor for construction of a parser that 
     * uses a Scanner object for input.
     * @param in the input scanner to use
     */
    public Parser(Scanner in) throws ScanErrorException
    {
        map = new HashMap<String, Integer>();
        scan = in;
        str = in.nextToken(); 
    }

    /**
     * This method returns whether or not the Parser has come across the end of the file
     * @return true if it has not, false if it has
     */
    public boolean hasNext()
    {
        if (str.equals("END") || str.equals("."))
            return false;
        return true;
    }

    /**
     * This method compares a given String to the String str. If these Strings match, then
     * eat calls nextToken.
     * @param ext is the String that will be compared to str
     * @throws ScanErrorException 
     */
    private void eat(String ext) throws ScanErrorException
    {
        if (!(ext.equals(str)))
            throw new ScanErrorException("Illegal Character- Expected "+ext+
                "and found "+str);
        str=scan.nextToken();
    }

    /**
     * Parses the current token, which is a number.
     * precondition:  current token is an integer
     * postcondition: number token has been eaten
     * @return the value of the parsed integer
     * @throws ScanErrorException if token isn't a number
     */
    private Expression parseNumber() throws ScanErrorException
    {
        int temp=0;
        temp=(Integer.parseInt(str));
        eat(str); 
        return (new Number(temp));
    }

    /**
     * Parses a statement, which could be a series of tokens. Parses from BEGIN to END.
     * Also parses IF THEN statements.
     * @return  A object of one of Statement's subclasses.
     */
    public Statement parseStatement() throws ScanErrorException
    {
        //System.out.println(str);
        if (str.equals("IF"))
        {
            eat(str);
            Expression exp1 = parseExpression();
            String relop = str;
            eat(str);
            Expression exp2 = parseExpression();
            Condition cond = new Condition(relop,exp1,exp2);
            eat("THEN");
            return new If(cond,parseStatement());
        }
        else if (str.equals("WHILE"))
        {
            eat(str);
            Expression exp1 = parseExpression();
            String relop = str;
            eat(str);
            Expression exp2 = parseExpression();
            Condition cond = new Condition(relop,exp1,exp2);
            eat("DO");
            return new While(cond,parseStatement());
        }
        else if (str.equals("BEGIN"))
        {
            eat(str);
            List list = new ArrayList<Statement>();
            while (!(str.equals("END")))
            {
                list.add(parseStatement());
            }
            eat("END");
            eat(";");
            //eat(";");
            return new Block(list);
        }
        else if (str.equals("WRITELN"))
        {
            eat(str);
            eat("(");
            Expression exp=parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (str.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String prodName = str;
            eat(str);
            eat("(");
            List<String> params = new ArrayList<String>();
            while (!str.equals(")"))
            {
                if (!str.equals(","))
                    params.add(str);
                else
                    eat(str);
            }
            eat(")");
            eat(";");
            Statement stmt = parseStatement();
            return new ProcedureDeclaration(prodName, params, stmt);
        }
        //else if (!str.equals("END") && str.matches("[a-z A-Z]([a-z A-Z]|(0-9))*"))
        else
        {
            //System.out.println(str);

            String temp2=str;
            eat(str);
            eat(":=");
            Expression exp=parseExpression();
            //System.out.println(exp);
            eat(";");
            return new Assignment(temp2,exp);
        }
        //eat("END");
        //eat(";");
        //return null;
        //         if (str.equals("BEGIN"))
        //         {
        //             eat(str);
        //             List list = new ArrayList<Statement>();
        //             while (!(str.equals("END")))
        //             {
        //                 if (str.equals("BEGIN"))
        //                     parseStatement();
        //                 else if (str.equals("WRITELN"))
        //                 {
        //                     eat(str);
        //                     eat("(");
        //                     Expression exp=parseExpression();
        //                     eat(")");
        //                     eat(";");
        //                     return new Writeln(exp);
        //                 }    
        //                 else if (!str.equals("END") && str.matches
        //                          ("[a-z A-Z]([a-z A-Z]|(0-9))*"))
        //                 {            
        //                     String temp2=str;
        //                     eat(str);
        //                     eat(":=");
        //                     Expression exp=parseExpression();
        //                     map.put(temp2,exp);
        //                     eat(";");
        //                 }
        //             }
        //             eat("END");
        //             eat(";");
        //             return new Block(list);
        //         }
        //         return;

    }

    /**
     * Parses a statement, which could be a series of tokens.
     * @throws ScanErrorException 
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if (str.matches("[a-z A-Z]([a-z A-Z]|(0-9))*"))
        {
            String tempstr = str;
            eat(tempstr);
            if (str.equals("("))
            {
                eat("(");
                List<Expression> tempList = new ArrayList<Expression>();
                if (!str.equals(")"))  
                    while (!str.equals(")"))
                    {
                        tempList.add(parseExpression());
                        if (str.equals(","))
                            eat(",");
                    }
                eat(")");
                if (tempList==null)
                    return new ProcedureCall(tempstr);
                else
                    return new ProcedureCall(tempstr,tempList);
            }
            return new Variable(tempstr);
        }
        if (str.equals("-"))
        {
            eat(str);
            return new BinOp("*",parseFactor(),new Number(-1));
        }
        else if (str.equals("("))
        {
            eat("(");
            Expression exp=parseExpression();
            //if (!str.equals(")"))
            eat(")");
            //while (!str.equals(")"))
            return exp;
        }
        else
            return parseNumber();
    }

    /**
     * Parses "/" and "*".
     * @throws ScanErrorException 
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression exp=parseFactor();
        while (str.equals("/") || str.equals("*"))
        {
            if (str.equals("/"))
            {
                eat(str);
                exp=new BinOp("/",exp,parseFactor());
                //temp=temp/(parseFactor());
            }
            else if (str.equals("*"))
            {
                eat(str);
                exp=new BinOp("*",exp,parseFactor());
                //temp=temp*(parseFactor());
            }
        }
        return exp;
    }

    /**
     * Parses "+" and "-".
     * @throws ScanErrorException 
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression exp=parseTerm();
        while (str.equals("+") || str.equals("-"))
        {
            if (str.equals("-"))
            {
                eat(str);
                exp=new BinOp("-",exp,parseTerm());
            }
            if (str.equals("+"))
            {
                eat(str);
                exp=new BinOp("+",exp,parseTerm());
            }
        }
        return exp;
    }

    /**
     * Parses program with procedure support.
     * @throws  ScanErrorException
     * @return  The program parsed.
     */
    public Program parseProgram() throws ScanErrorException
    {
        Program program = new Program();
        if (str.equals("VAR"))
        {
            eat("VAR");
            while (!(str.equals(";")))
            {
                program.addVariable(new Variable(str));
                eat(str);
                if (str.equals(","))
                    eat(",");
            }
            eat(";");
        }
        while (str.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String tempstr = str;
            eat(str);
            eat("(");
            List<String> tempList = new ArrayList<String>();
            if (!str.equals(")"))
                while (!str.equals(")"))
                {
                    tempList.add(str);
                    eat(str);
                    if (str.equals(","))
                        eat(",");
                }                
            eat(")");
            eat(";");
            if (tempList==null)
                program.addProcedure(new ProcedureDeclaration(tempstr, parseStatement()));
            else
                program.addProcedure(new ProcedureDeclaration(tempstr, tempList, parseStatement()));
        }
        program.addStatement(parseStatement());
        return program;
    }

    /**
     * Main method that tests the Parser class.
     * @param args  Args
     */
    public static void main(String[] args) throws ScanErrorException
    {
        FileInputStream inStream=null;
        try
        {
            inStream = new FileInputStream(new File("/Users/SparshChauhan/Downloads/parserTest3.txt"));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(inStream);
        Parser parse = new Parser(scan);
        Environment env = new Environment();
        //parse.parseStatement().exec(env);
        //parse.parseStatement().exec(env);
        parse.parseStatement().exec(env);
        //Program program = parse.parseProgram();
        //program.compile("outputtest.asm");
        //program.eval(env);
    }
}