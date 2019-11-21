# Toy_Language
-project created during the third semester 
What I learned:
-how to use Git
-java basics
-java collections
-inheritance, interfaces in Java
-working with files in Java
-how to create an interpreter for my own language


----------------------------------------------Toy Language Description-----------------------------------------------------

A program (Program) in this language consists of a statement (Statement) as follows:
Programg := Statement where the symbol "::=" means "a Progam is defined as a Statement".
A statement can be either a compound statement (CompoundStatement), or an assignment statement
(AssignmentStatement), or a print statement (PrintStatement), or a conditional statement (IfStatement) as follows:
Statement ::= Statement1 ; Statement2 /* (CompoundStatement)*/
 | Type Id /* (VariableDecalaration) */
 | Id = Expression /* (AssignmentStatement)*/
 | Print(Expression) /* (PrintStatement)*/
 | If Expression Then Statement1 Else Statement2 /* (IfStatement)*/
 | nop /* No operation */
 |openReadFile(expression) /*opeanReadFile/*
 |readFile(expression,variableName) /*readFile*/
 |closeReadFile(expression) /*closeReadFile*/
where the symbol "|" denotes the possible definition alternatives.

A type Type can be :
Type ::= int
 | bool
 |string
 
A value Value in our language can be either an integer number (ConstNumber),boolean
values like (ConstTrue) or (ConstFalse), or a string value(ConstString):
Value ::= Number /*(ConstNumber)*/
 | True /* (ConstTrue)*/
 | False /* (ConstFalse)*/
 |a string /*(ConstString)
 
An expression (Expression) can be either a value (Value), or a variable name (Variable), or an arithmetic
expression (ArithmeticExpression), a logical expression (LogicalExpression), or a relational expression(RelationExpression) as follows:
Exp ::= Value /*Value*/
 | Id /*(Variable)*/
 | Exp1 + Exp2 /*(ArithmeticExpression)*/
 | Exp1 - Exp2
 | Exp1 * Exp2
 | Exp1 / Exp2
 | Exp1 and Exp2 /*(LogicalExpression)*/
 | Exp1 or Exp2
 |exp1 < exp2 /*(RelationalExpression) - can be only between 2 Integer Values
 |exp1<=exp2
 |exp1==exp2
 |exp1!=exp2
 |exp1> exp2
 |exp1>=exp2 
 
Example1:
int v;
v=2;
Print(v)

Example2:
int a;
a=2+3*5;
int b;
b=a-4/2 + 7;
Print(b)

Example3:
bool a;
a=false;
int v;
If a Then v=2 Else v=3;
Print(v)

Example4:
string varf;
varf="test.in";
openRFile(varf);
int varc;
readFile(varf,varc);print(varc);
readFile(varf,varc);print(varc)
closeRFile(varf)
-->and the "test.in" file contains the followings:
15<NL>
50<NL>
<EOF>

Example5:
example 5
bool a;
a= 2<3;
bool b;
b= 5<1;
if(2!=3) then Print(5) else Print(6)

-------------------------------------Toy Language Evaluation (Execution):-----------------------------------------------

Our mini interpreter uses 4 main structures:
– Execution Stack (ExecutionStack): a stack of statements to execute the currrent program
– Table of Symbols (SymbolTable): a table which keeps the variables values
– Output (Output): that keeps all the mesages printed by the toy program
-The table of files (FileTable) : that manages the files opened in our Toy Language.

All these four main structures denote the program state (ProgramState). Our interpreter can
execute multiple programs but for each of them use a different ProgramState structures (that
means different ExecutionStack, SymbolTable, Output and FileTable structures).

At the beginning, ExecutionStack contains the original program, and SymbolTable ,Outout and FileTable are empty.
After the evaluation has started:
->ExecutionStack contains the remaining part of the program that
must be evaluated
->SymbolTable contains the variables (from the variable declarations
statements evaluated so far) with their assigned values
>Output contains the values printed so far
->FileTable contains a dictionary mapping a StringValue (containing the filename (a string)) to the file descriptor from
Java language. The file descriptor from Java language is an instance of the BufferedReader class.
(We assume that a file can only be a text file that contains only non-zero positive integers, one
integer per line.)

In order to explain the program evaluation rules, we represent ExecutionStack as a collection of
statements separated by the symbol "|", SymbolTable and FileTable as a collection of mappings and Output as a
collection of messages.
For example, the ExecutionStack {s1 | s2 | s3} denotes a stack that has the statement s1 on top of it,
followed by the statement s2 and at the bottom of the stack is the statement s3.
For example, SymbolTable {v->2,a->false} denotes the table containing only two variables v and
a, v has assigned the integer value 2, while a has assigned the boolean value flase.
For example Output {1,2} denotes the printed values, the order of printing is 1 followed by 2.

At the end of a program evaluation:
->ExecutionStack is empty
->SymbolTable contains all the program variables
->Output contains all the program print outputs
->FileTable contains all the remaining opened files

Statement Evaluation Rules: are described by presenting the program state
(ExecutionStack1,SymbolTable1,Output1) before applying the evaluation rule, the symbol "==>" for one
step evaluation and the program state (ExecutionStack2,SymbolTable2,Outout2) after applying the 
evaluation rule. Each evaluation rules shows how the program state is changed. One step
evaluation means that only one statement evaluation rule is applied. Complete evaluation
means that all possible evaluation rules are applied until the program evaluation terminates.
Termination means that there is no any evaluation rule that can be further applied. In the following examples,
FileTable will be omitted for simplicity purposes.

S1. CompoundStatement execution: when a compound statement is the top of the ExecutionStack
ExecutionStack1={Statement1;Statement2 | Statement3|....}
SymbolTable1
Output1
 ==>
ExecutionStack2={Statement1| Statement2 | Statement3|.....}
SymbolTable2=SymbolTable1
Output2 = Output1
As you can see, the top of the ExecutionStack is changed while SymbolTable and Output remain
unchanged.

S2. AssignmentStatement execution: an assignment statement is on top of the stack
ExecutionStack1={Id=Expression| Statement1|....}
SymbolTable1
Output1
 ==>
ExecutionStack2={Statementt1|...}
SymbolTable2=if IsVariableDefined(SymbolTable1,Id) then
 Val1=Evaluate(Expression)
 If Type(Val1) == GetType(SymbolTable1,Id) then
 Update(SymbolTable1,Id,Val1)
 else Error: "Type of expression and type of variable do not match"
 else Erorr: "Variable Id is not declared"
Output2 = Output1
where:
->Evaluate(Expression) denotes the expression evaluation and the rules are explained a bit later;
->IsVaiablerDefined(SymbolTable,Id) looks for the entry of Id in the table, returns False when it cannot be
  found and True otherwise;
->Update(SymbolTable1,Id,Val1) update the value to which Id is mapped in SymbolTable1, for example:
    Update({Id->8},Id,10) --> {Id->10)};
    GetType(Evaluate(expression)) -> get the type of an expression;
    GetType(SymbolTable1,Id) -> get the type of ID from the table
    
S3. VariableDeclaration execution: a variable declaration statement is on top of the stack
ExecutionStack1={Type Id | Statement1|....}
SymbolTable1
Output1
 ==>
ExecutionStack1={Statement1|....}
if IsVariableDefined(SymbolTable1,Id) == True) then Error: "variable is already declared"
else SymbolTable2 = SymbolTable1 U {Id-> default value}
Output1 
where default value of the variable is determined based on the variable declared type, as follows:
->defaultValue(int) =0
->defaultValue(bool) = false

S4. PrintStatement execution:
ExecutionStack1={Print(Expression)| Statement1|....}
SymbolTable1
Output1
 ==>
ExecutionStack2={Statement1|...}
SymbolTable2=SymbolTable1
Output2 = Output1 U {Evaluate(Expression)}

S5. IfStatement execution:
ExecutionStack1={If Expression Then Statement1 Else Statement2 | Statement3|....}
SymbolTable1
Output1
 ==>
Cond= Evaluate(Expression)
if getType(Cond)!=bool) error "conditional expr is not a boolean"
else
 if Cond ==True ExecutionStack2={Statement1|Statement3|...} 
 else ExecutionStack2={Statement2|Statement3|...}
SymbolTable2=SymbolTable1
Output2 = Output1

S6. NOP execution:
ExecutionStack1={Nop | Statement1|....}
SymbolTable1
Output1
 ==>
ExeStack2={Statement1|...}
SymbolTable2=SymbolTable1
Output2 = Output1

S7. Program termination:
ExecutionStack1 ={}
SymbolTable1
Output1
==>
end of the program execution
