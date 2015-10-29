// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



// import 

import java.util.*;
import java.io.PrintStream;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
 <p>
 See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
 <p>
 See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
}


/** Defines list phylum Formals
 <p>
 See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;
    public AbstractSymbol get_type() { return type; }
    public Expression set_type(AbstractSymbol s) { type = s; return this; }
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
        { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
        { out.println(Utilities.pad(n) + ": _no_type"); }
    }

}


/** Defines list phylum Expressions
 <p>
 See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
 <p>
 See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for classes
     */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }

    /** This method is the entry point to the semantic checker.  You will
     need to complete it in programming assignment 4.
     <p>
     Your checker should do the following two things:
     <ol>
     <li>Check that the program is semantically correct
     <li>Decorate the abstract syntax tree with type information
     by setting the type field in each Expression node.
     (see tree.h)
     </ol>
     <p>
     You are free to first do (1) and make sure you catch all semantic
     errors. Part (2) can be done in a second stage when you want
     to test the complete compiler.
     */
    public void semant() {
    /* ClassTable constructor may do some semantic analysis */
        ClassTable classTable = new ClassTable(classes);
    
    /* some semantic analysis code may go here */
        SymbolTable st = new SymbolTable();
        st.setClassTable(classTable);

        firstPass(st, st.classTable().getBasicElements());
        firstPass(st, classes.getElements());

        secondPass(st, classes);

        /* TESTING LUB -- DO NOT KEEP FOR SUBMISSION*/
//        int count = 0;
//        class_c cls1 = null;
//        class_c cls2 = null;
//        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
//            count++;
//            class_c curClass = (class_c)e.nextElement();
//            //System.out.println(curClass.getName().getString());
//            if(count == 1){
//                cls1 = curClass;
//            }else if(count == 5){
//                cls2 = curClass;
//            }
//        }
//        System.out.println(cls1.getName());
//        System.out.println(cls2.getName());
//        System.out.println("lub is " + classTable.lub(cls1, cls2).getName());

        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
    }

    /** Add class attributes and methods to the symbol table.
     *  Don't do any type checking yet.
     *
     * @param st symbol table
     * @param cls classes to add to the symbol table
     */
    public void firstPass(SymbolTable st, Enumeration cls){
        ClassTable ct = st.classTable();
        for (Enumeration e = cls; e.hasMoreElements(); ) {
            class_c curClass = (class_c)e.nextElement();
            st.methodLookup().put(curClass.getName(), new HashMap<AbstractSymbol, method>());
            st.variableLookup().put(curClass.getName(), new HashMap<AbstractSymbol, AbstractSymbol>());

            Features feats = curClass.getFeatures();
            for (Enumeration<Feature> f = feats.getElements(); f.hasMoreElements();){
                Feature curFeat = f.nextElement();
                if(curFeat instanceof attr){
                    addAttr(st, curClass, (attr)curFeat);
                }
                //else must be of type 'method', or the parser would have thrown an error
                else{
                    addMethod(st, curClass, (method)curFeat);
                }
            }
        }
    }

    /** Add the method to the symbol table
     *
     * @param st symbol table
     * @param curClass
     * @param curFeat
     */
    public void addMethod(SymbolTable st, class_c curClass, method curFeat){
        HashMap mt = (HashMap)st.methodLookup();
        AbstractSymbol className = curClass.getName();
        HashMap curClassMethods = (HashMap)mt.get(className);
        curClassMethods.put(curFeat.name, curFeat);
    }

    /** Add the attribute to the symbol table.
     *
     * @param st symbol table
     * @param curClass
     * @param curFeat
     */
    public void addAttr(SymbolTable st, class_c curClass, attr curFeat){
        HashMap vt = st.variableLookup();
        AbstractSymbol className = curClass.getName();
        HashMap curClassAttrs = (HashMap)vt.get(className);
        curClassAttrs.put(curFeat.name, curFeat.type_decl);
    }

    /** Type check the tree.
     *
     * @param st the symboltable to use
     */
    public void secondPass(SymbolTable st, Classes cls){
        ClassTable ct = st.classTable();
        for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
            class_c curClass = (class_c)e.nextElement();
            enterClassScope(st, curClass);

            Features feats = curClass.getFeatures();
            for (Enumeration<Feature> f = feats.getElements(); f.hasMoreElements();){
                Feature curFeat = f.nextElement();
                if(curFeat instanceof attr){
                    checkAttr(st, curClass, (attr)curFeat);
                }
                //else must be of type 'method', or the parser would have thrown an error
                else{
                    checkMethod(st, curClass, (method) curFeat);
                }
            }
            st.exitScope();
        }
    }

    /** Add a new scope to the symbol table and fill it with the current class's attributes.
     *
     * @param st
     * @param curClass
     */
    public void enterClassScope(SymbolTable st, class_c curClass){
        st.enterScope();
        HashMap classAttrs = (HashMap)st.variableLookup().get(curClass.getName());
        Iterator it = classAttrs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry curEntry = (Map.Entry)it.next();
            AbstractSymbol curName = (AbstractSymbol)curEntry.getKey();
            AbstractSymbol curType = (AbstractSymbol)curEntry.getValue();
            st.addId(curName, curType);
        }
        st.addId(TreeConstants.self, TreeConstants.SELF_TYPE); // every class has self implicitly
    }

    /** Type check attributes.
     *
     * @param st SymbolTable
     * @param curClass
     * @param curFeat
     */
    public void checkAttr(SymbolTable st, class_c curClass, attr curFeat){
        if(!(curFeat.init instanceof no_expr)){
            AbstractSymbol T = checkExpression(st, curClass, curFeat.init);
            if(T != curFeat.type_decl){
                String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                        "attribute \"" + curFeat.name.getString() + "\":" +
                        "\n\tInferred type \"" + T.getString() +
                        "\" does not match declared type \"" + curFeat.type_decl.getString() + "\"\n";
                st.classTable.semantError(curClass).append(errorStr).flush();
            }
        }
    }

    /** Type check methods.
     *
     * @param st SymbolTable
     * @param curClass
     * @param curFeat
     */
    public void checkMethod(SymbolTable st, class_c curClass, method curFeat){
        enterMethodScope(st, curClass, curFeat);
        AbstractSymbol T0 = checkExpression(st, curClass, curFeat.expr);
        st.exitScope();
        AbstractSymbol declRetType = curFeat.return_type;

        if(!st.classTable().isSubtype(T0.getString(), declRetType.getString())){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "method \"" + curFeat.name.getString() + "\":" +
                    "\n\tInferred type \"" + T0.getString() +
                    "\" does not conform to return type \"" + declRetType.getString() + "\"\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
        }
    }

    /** Enter a new scope and add the method formals to that scope.
     *
     * @param st
     * @param curClass
     * @param curFeat
     */
    public void enterMethodScope(SymbolTable st, class_c curClass, method curFeat){
        st.enterScope();
        for (Enumeration<TreeNode> e = curFeat.formals.getElements(); e.hasMoreElements();){
            formalc curFormal = ((formalc)e.nextElement());
            AbstractSymbol curName = curFormal.name;
            AbstractSymbol curType = curFormal.type_decl;
            st.addId(curName, curType);
        }
    }

    /** Check if the type exists in the class table (i.e. if type is a class name).
     *
     * Reports an error if "type" it is not a type.
     *
     * @param st Symbol Table
     * @param curClass
     * @param type the type to check
     * @return true if the type is in the class table, else false
     */
    public boolean isType(SymbolTable st, class_c curClass, AbstractSymbol type){
        boolean isAType = st.classTable().isType(type);
        if(!isAType){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"new\":" +
                    "\n\ttype \"" + type.getString() + "\" not found.\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
        }
        return isAType;
    }

    /** Type check expressions.
     *
     *  prints out an error message if an expression fails to type check, but does not stop
     *  execution.
     *
     * @param st symbol table
     * @param curClass
     * @param exp
     * @return the type the expressions evaluates to.
     */
    public AbstractSymbol checkExpression(SymbolTable st, class_c curClass, Expression exp){
        System.out.println(exp.getClass());
        if(exp instanceof int_const){
            exp.set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }else if(exp instanceof string_const){
            exp.set_type(TreeConstants.Str);
            return TreeConstants.Str;
        }else if(exp instanceof bool_const){
            exp.set_type(TreeConstants.Bool);
            return TreeConstants.Bool;
        }else if(exp instanceof object){
            return typeCheckObject(st, curClass, (object)exp);
        }else if(exp instanceof cond){
            return typeCheckCond(st, curClass, (cond)exp);
        }else if(exp instanceof plus){
            return typeCheckPlus(st, curClass, (plus)exp);
        }else if(exp instanceof let){
            return typeCheckLet(st, curClass, (let)exp);
        }else if(exp instanceof dispatch){
            return typeCheckDispatch(st, curClass, (dispatch) exp);
        }else if(exp instanceof static_dispatch){
            return typeCheckStaticDispatch(st, curClass, (static_dispatch) exp);
        }else if(exp instanceof sub){
            return typeCheckSub(st, curClass, (sub)exp);
        }else if(exp instanceof mul){
            return typeCheckMul(st, curClass, (mul)exp);
        }else if(exp instanceof divide){
            return typeCheckDiv(st, curClass, (divide)exp);
        }else if(exp instanceof neg){
            return typeCheckComp(st, curClass, (neg)exp);
        }else if(exp instanceof lt){
            return typeCheckLt(st, curClass, (lt)exp);
        }else if(exp instanceof eq){
            return typeCheckEq(st, curClass, (eq)exp);
        }else if(exp instanceof leq){
            return typeCheckLeq(st, curClass, (leq)exp);
        }else if(exp instanceof comp){
            return typeCheckNeg(st, curClass, (comp)exp);
        }else if(exp instanceof new_){
            return typeCheckNew(st, curClass, (new_)exp);
        }else if(exp instanceof isvoid){
            return typeCheckIsVoid(st, curClass, (isvoid)exp);
        }else if(exp instanceof block){
            return typeCheckBlock(st, curClass, (block)exp);
        }else if(exp instanceof loop){
             return typeCheckLoop(st, curClass, (loop)exp);
        }else if(exp instanceof typcase){
             return typeCheckTypcase(st, curClass, (typcase) exp);
        }else if(exp instanceof assign){
            return typeCheckAssign(st, curClass, (assign) exp);
        }

        return null;
    }

    public AbstractSymbol typeCheckObject(SymbolTable st, class_c curClass, object exp){
        Object objType = st.lookup(exp.name);
        System.out.println(objType);
        if(objType == null){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "object \"" + exp.name.getString() + ":\":" +
                    "\n\tObject with name\"" + exp.name + "\" was not found in scope.\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            objType = TreeConstants.Object_;
        }else{
            exp.set_type((AbstractSymbol) objType);
        }
        return (AbstractSymbol)objType;
    }

    public AbstractSymbol typeCheckCond(SymbolTable st, class_c curClass, cond exp){
        AbstractSymbol e2 = checkExpression(st, curClass, exp.then_exp);
        AbstractSymbol e3 = checkExpression(st, curClass, exp.else_exp);
        AbstractSymbol retType = st.classTable.lub(e2.getString(), e3.getString());

        AbstractSymbol e1 = checkExpression(st, curClass, exp.pred);
        if(e1 != TreeConstants.Bool){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "conditional expression:" +
                    "\n\tConditional predicate had inferred type \"" + e1.getString() +
                    "\" should have type \"" + TreeConstants.Bool + ".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }else{
            exp.set_type(retType);
        }
        return retType;
    }

    public AbstractSymbol typeCheckPlus(SymbolTable st, class_c curClass, plus exp){
        AbstractSymbol retType = TreeConstants.Int;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"plus\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        else if(T2 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"plus\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }else{
            exp.set_type(TreeConstants.Int);  // jk: why we set_type here
        }
        return retType;
    }

    public AbstractSymbol typeCheckAssign(SymbolTable st, class_c curClass, assign exp){
        AbstractSymbol T0 = exp.name;
        Expression e1 = exp.expr;
        AbstractSymbol retType;
        AbstractSymbol T1 = checkExpression(st, curClass, e1);
        String t0s = T0.getString();
        String t1s = T1.getString();
        if (st.classTable().isSubtype(t1s, t0s)) {
            retType = T1;
        } else retType = TreeConstants.Object_;
        exp.set_type(T1);
        return retType;
    }  

    public AbstractSymbol typeCheckBlock(SymbolTable st, class_c curClass, block exp){
        AbstractSymbol retType = null;
        Expression e1 = null;
        for (Enumeration<Expression> e = exp.body.getElements(); e.hasMoreElements();){
            e1 = e.nextElement();
            retType = checkExpression(st, curClass, e1);
        }

        // make sure there was at least one expression
        if(retType == null){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"block\": \n\tNo expressions found.\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckLoop(SymbolTable st, class_c curClass, loop exp){
        AbstractSymbol e1 = checkExpression(st, curClass, exp.pred);
        AbstractSymbol e2 = checkExpression(st, curClass, exp.body);
        if(e1 != TreeConstants.Bool){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"loop\":" +
                    "\n\tpredicate is type \"" + e1.getString() +
                    "\" should be type \"" + TreeConstants.Bool.getString() + "\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
        }
        exp.set_type(TreeConstants.Object_);
        return TreeConstants.Object_;
    }

    public AbstractSymbol typeCheckTypcase(SymbolTable st, class_c curClass, typcase exp){
        Vector<AbstractSymbol> casesTypes = new Vector<AbstractSymbol>();
        branch b1 = null;
        //collect all case branch expression types
        for (Enumeration<branch> e = exp.cases.getElements(); e.hasMoreElements();){
            b1 = e.nextElement();
            st.enterScope();
            st.addId(b1.name, b1.type_decl);
            casesTypes.add(checkExpression(st, curClass, b1.expr));
            st.exitScope();
        }

        if(casesTypes.size() == 0){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"case\": case must have at least one branch.\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
        }

        // find the lub of all the case branch expression types
        AbstractSymbol retType = null;
        ClassTable ct = st.classTable();
        Enumeration<AbstractSymbol> lube = casesTypes.elements();
        AbstractSymbol type1 = lube.nextElement();
        AbstractSymbol type2 = null;
        while(lube.hasMoreElements()){
            type2 = lube.nextElement();
            type1 = ct.lub(type1.getString(), type2.getString());
        }
        exp.set_type(type1);
        return type1;
    }

    public AbstractSymbol typeCheckSub(SymbolTable st, class_c curClass, sub exp){
        AbstractSymbol retType = TreeConstants.Int;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"sub\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        if(T2 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"sub\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }else {
            exp.set_type(TreeConstants.Int);
        }
        return retType;
    }

    public AbstractSymbol typeCheckMul(SymbolTable st, class_c curClass, mul exp){
        AbstractSymbol retType = TreeConstants.Int;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"mul\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        if(T2 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"mul\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }else {
            exp.set_type(TreeConstants.Int);
        }
        return retType;
    }

    public AbstractSymbol typeCheckNeg(SymbolTable st, class_c curClass, comp exp){
        AbstractSymbol retType = TreeConstants.Bool;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        if(T1 != TreeConstants.Bool){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"Neg\":" +
                    "\n\tInferred type of expr is \"" + T1.getString() +
                    "\". Should be \"Bool\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckLt(SymbolTable st, class_c curClass, lt exp){
        AbstractSymbol retType = TreeConstants.Bool;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"lt\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        if(T2 != TreeConstants.Int){
            exp.set_type(TreeConstants.Int);
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"lt\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckEq(SymbolTable st, class_c curClass, eq exp){
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if ((T1 == TreeConstants.Int || T1 == TreeConstants.Bool || T1 == TreeConstants.Str) ||
            (T2 == TreeConstants.Int || T2 == TreeConstants.Bool || T2 == TreeConstants.Str)) {
            if(T1 != T2){
                String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                        "expression \"eq\":" +
                        "\n\tInferred type of LHS is \"" + T1.getString() +
                        "\n\tInferred type of EHS is \"" + T2.getString() +
                        "\". Not comparable.\n";
                st.classTable.semantError(curClass).append(errorStr).flush();
                return TreeConstants.Object_;
            }
        }
        exp.set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }

    public AbstractSymbol typeCheckLeq(SymbolTable st, class_c curClass, leq exp){
        AbstractSymbol retType = TreeConstants.Bool;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"leq\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        if(T2 != TreeConstants.Int){
            exp.set_type(TreeConstants.Int);
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"leq\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckComp(SymbolTable st, class_c curClass, neg exp){
        AbstractSymbol retType = TreeConstants.Int;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        if(T1 != TreeConstants.Bool){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"Complement\":" +
                    "\n\tInferred type of expr is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckDiv(SymbolTable st, class_c curClass, divide exp){
        AbstractSymbol retType = TreeConstants.Int;
        AbstractSymbol T1 = checkExpression(st, curClass, exp.e1);
        AbstractSymbol T2 = checkExpression(st, curClass, exp.e2);
        if(T1 != TreeConstants.Int){
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"divide\":" +
                    "\n\tInferred type of LHS is \"" + T1.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        if(T2 != TreeConstants.Int){
            exp.set_type(TreeConstants.Int);
            String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                    "expression \"divide\":" +
                    "\n\tInferred type of RHS is \"" + T2.getString() +
                    "\". Should be \"Int\".\n";
            st.classTable.semantError(curClass).append(errorStr).flush();
            retType = TreeConstants.Object_;
        }
        exp.set_type(retType);
        return retType;
    }

    public AbstractSymbol typeCheckNew(SymbolTable st, class_c curClass, new_ exp){
        AbstractSymbol typeName = exp.type_name;

        // check if the type exists in the class table
        if(!isType(st, curClass, typeName)){
            return TreeConstants.Object_;
        }

        if(typeName == TreeConstants.SELF_TYPE){
            exp.set_type(TreeConstants.Int);
            return curClass.name;
        }
        exp.set_type(typeName);
        return typeName;
    }

    public AbstractSymbol typeCheckIsVoid(SymbolTable st, class_c curClass, isvoid exp){
        AbstractSymbol e1 = checkExpression(st, curClass, exp.e1);
        exp.set_type(TreeConstants.Object_);
        return TreeConstants.Object_;
    }

    public AbstractSymbol typeCheckLet(SymbolTable st, class_c curClass, let exp){

        AbstractSymbol e1 = exp.identifier;

        AbstractSymbol T0 = exp.type_decl;
        if(exp.type_decl == TreeConstants.SELF_TYPE){
            T0 = curClass.name;
        }

        if(!(exp.init instanceof no_expr)){
            AbstractSymbol T1 = checkExpression(st, curClass, exp.init);
            if(!st.classTable().isSubtype(T1.getString(), T0.getString())){
                String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                        "expression \"let_init\":" +
                        "\n\tInferred type of \"" + e1.getString() + "\" is \"" + T1.getString() +
                        "\" and does not conform to \"" + T0.getString() + "\".\n";
                st.classTable.semantError(curClass).append(errorStr).flush();
                return TreeConstants.Object_;
            }
        }

        st.enterScope();
        st.addId(e1, T0);
        AbstractSymbol bodyType = checkExpression(st, curClass, exp.body);
        st.exitScope();

        exp.set_type(bodyType);
        return bodyType;
    }

    public AbstractSymbol typeCheckDispatch(SymbolTable st, class_c curClass, dispatch exp){
        AbstractSymbol retType = null;

        AbstractSymbol T0 = checkExpression(st, curClass, exp.expr);
        if(T0 == TreeConstants.SELF_TYPE){
            T0 = curClass.name;
        }

        Enumeration<AbstractSymbol> actualsTypes = getActualsTypes(st, curClass, exp.actual);
        Enumeration<AbstractSymbol> retAndFormalTypes = methodRetAndFormalTypes(st, T0, exp.name);

        AbstractSymbol declRetType =  retAndFormalTypes.nextElement();
        if(declRetType == TreeConstants.SELF_TYPE){
            declRetType = T0;
        }
        retType = declRetType;

        Boolean badType = false;
        while (retAndFormalTypes.hasMoreElements()){
            String curActualType = actualsTypes.nextElement().getString();
            String curFormalType = retAndFormalTypes.nextElement().getString();
            if(!st.classTable().isSubtype(curActualType, curFormalType)){
                String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                        "expression \"dispatch\":" +
                        "\n\tInferred actual type \"" + curActualType +
                        "\" does no conform to type \"" + curFormalType + "\".\n";
                st.classTable.semantError(curClass).append(errorStr).flush();
                retType = TreeConstants.Object_;
                badType = true;
            }
        }

        if(!badType){
            exp.set_type(retType);
        }
        return retType;
    }

    public AbstractSymbol typeCheckStaticDispatch(SymbolTable st, class_c curClass, static_dispatch exp){
        AbstractSymbol retType = null;

        AbstractSymbol T0 = checkExpression(st, curClass, exp.expr);
        if(T0 == TreeConstants.SELF_TYPE){
            T0 = curClass.name;
        }

        Enumeration<AbstractSymbol> actualsTypes = getActualsTypes(st, curClass, exp.actual);
        Enumeration<AbstractSymbol> retAndFormalTypes = methodRetAndFormalTypes(st, exp.type_name, exp.name);

        AbstractSymbol declRetType =  retAndFormalTypes.nextElement();
        if(declRetType == TreeConstants.SELF_TYPE){
            declRetType = T0;
        }
        retType = declRetType;

        Boolean badType = false;
        while (retAndFormalTypes.hasMoreElements()){
            String curActualType = actualsTypes.nextElement().getString();
            String curFormalType = retAndFormalTypes.nextElement().getString();
            if(!st.classTable().isSubtype(curActualType, curFormalType)){
                String errorStr = "\n\tClass \"" + curClass.getName().getString() + "\", " +
                        "expression \"static_dispatch\":" +
                        "\n\tInferred actual type \"" + curActualType +
                        "\" does no conform to type \"" + curFormalType + "\".\n";
                st.classTable.semantError(curClass).append(errorStr).flush();
                retType = TreeConstants.Object_;
                badType = true;
            }
        }

        if(!badType){
            exp.set_type(retType);
        }
        return retType;
    }

    /** get the types of the method actuals (i.e. the method arguments).
     * also typeChecks the acutals and sets the types of the actuals
     *
     * @param st
     * @param curClass
     * @param actuals
     * @return an enumeration of the actuals' types
     */
    public Enumeration<AbstractSymbol> getActualsTypes(SymbolTable st, class_c curClass, Expressions actuals){
        Vector actualsTypes = new Vector<AbstractSymbol>();
        for (Enumeration<Expression> e = actuals.getElements(); e.hasMoreElements();){
            Expression e1 = e.nextElement();
            actualsTypes.add(checkExpression(st, curClass, e1));
        }
        return actualsTypes.elements();
    }

    /** return an enumeration of the return type and formal types of the specified method
     *
     * @param st Symbol Table
     * @param className the class of the method
     * @param methodName the name of the method
     * @return enumeration of return and formal types of the method (in that order).
     */
    public Enumeration<AbstractSymbol> methodRetAndFormalTypes(SymbolTable st,
                                                               AbstractSymbol className, AbstractSymbol methodName){
        Vector formalsAndRetType = new Vector<AbstractSymbol>();
        method m = (method)((HashMap)st.methodLookup().get(className)).get(methodName);
        System.out.println("className: " + className + " name: " + methodName + " methodOBject: " + m);
        formalsAndRetType.add(m.return_type);
        for (Enumeration<TreeNode> e = m.formals.getElements(); e.hasMoreElements();){
            formalsAndRetType.add(((formalc)e.nextElement()).type_decl);
        }
        return formalsAndRetType.elements();
    }

}

/** Defines AST constructor 'class_c'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for parent
     * @param a2 initial value for features
     * @param a3 initial value for filename
     */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

}


/** Defines AST constructor 'method'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for formals
     * @param a2 initial value for return_type
     * @param a3 initial value for expr
     */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
        expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'attr'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for type_decl
     * @param a2 initial value for init
     */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'formalc'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for type_decl
     */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for type_decl
     * @param a2 initial value for expr
     */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'assign'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     * @param a1 initial value for expr
     */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
        expr.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'static_dispatch'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for expr
     * @param a1 initial value for type_name
     * @param a2 initial value for name
     * @param a3 initial value for actual
     */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'dispatch'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for expr
     * @param a1 initial value for name
     * @param a2 initial value for actual
     */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'cond'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for pred
     * @param a1 initial value for then_exp
     * @param a2 initial value for else_exp
     */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
        pred.dump_with_types(out, n + 2);
        then_exp.dump_with_types(out, n + 2);
        else_exp.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'loop'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for pred
     * @param a1 initial value for body
     */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
        pred.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'typcase'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for expr
     * @param a1 initial value for cases
     */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
        expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

}


/** Defines AST constructor 'block'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for body
     */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

}


/** Defines AST constructor 'let'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for identifier
     * @param a1 initial value for type_decl
     * @param a2 initial value for init
     * @param a3 initial value for body
     */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
        dump_AbstractSymbol(out, n + 2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'plus'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'sub'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'mul'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'divide'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'neg'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'lt'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'eq'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'leq'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     * @param a1 initial value for e2
     */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'comp'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'int_const'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for token
     */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
        dump_AbstractSymbol(out, n + 2, token);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'bool_const'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for val
     */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
        dump_Boolean(out, n + 2, val);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'string_const'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for token
     */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, token.getString());
        out.println("\"");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'new_'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for type_name
     */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'isvoid'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for e1
     */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

}


/** Defines AST constructor 'no_expr'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'object'.
 <p>
 See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for name
     */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
        dump_AbstractSymbol(out, n + 2, name);
        dump_type(out, n);
    }
}