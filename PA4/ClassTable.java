/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;

    private Hashtable<String, ArrayList<String>> classGraph;
    private Hashtable<String, class_c> nameToNodeMap; // needed for error reporting
    private HashSet<String> primitiveClasses;
    private Vector<class_c> basicClasses;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
    AbstractSymbol filename 
        = AbstractTable.stringtable.addString("<basic class>");
    
    // The following demonstrates how to create dummy parse trees to
    // refer to basic Cool classes.  There's no need for method
    // bodies -- these are already built into the runtime system.

    // IMPORTANT: The results of the following expressions are
    // stored in local variables.  You will want to do something
    // with those variables at the end of this method to make this
    // code meaningful.

    // The Object class has no parent class. Its methods are
    //        cool_abort() : Object    aborts the program
    //        type_name() : Str        returns a string representation 
    //                                 of class name
    //        copy() : SELF_TYPE       returns a copy of the object

    class_c Object_class = 
        new class_c(0, 
               TreeConstants.Object_, 
               TreeConstants.No_class,
               new Features(0)
               .appendElement(new method(0, 
                          TreeConstants.cool_abort, 
                          new Formals(0), 
                          TreeConstants.Object_, 
                          new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.type_name,
                          new Formals(0),
                          TreeConstants.Str,
                          new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.copy,
                          new Formals(0),
                          TreeConstants.SELF_TYPE,
                          new no_expr(0))),
               filename);
    
    // The IO class inherits from Object. Its methods are
    //        out_string(Str) : SELF_TYPE  writes a string to the output
    //        out_int(Int) : SELF_TYPE      "    an int    "  "     "
    //        in_string() : Str            reads a string from the input
    //        in_int() : Int                "   an int     "  "     "

    class_c IO_class = 
        new class_c(0,
               TreeConstants.IO,
               TreeConstants.Object_,
               new Features(0)
               .appendElement(new method(0,
                          TreeConstants.out_string,
                          new Formals(0)
                          .appendElement(new formalc(0,
                                     TreeConstants.arg,
                                     TreeConstants.Str)),
                          TreeConstants.SELF_TYPE,
                          new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.out_int,
                          new Formals(0)
                          .appendElement(new formalc(0,
                                     TreeConstants.arg,
                                     TreeConstants.Int)),
                          TreeConstants.SELF_TYPE,
                          new no_expr(0)))
               .appendElement(new method(0,
                       TreeConstants.in_string,
                       new Formals(0),
                       TreeConstants.Str,
                       new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.in_int,
                          new Formals(0),
                          TreeConstants.Int,
                          new no_expr(0))),
               filename);

    // The Int class has no methods and only a single attribute, the
    // "val" for the integer.

    class_c Int_class = 
        new class_c(0,
               TreeConstants.Int,
               TreeConstants.Object_,
               new Features(0)
               .appendElement(new attr(0,
                        TreeConstants.val,
                        TreeConstants.prim_slot,
                        new no_expr(0))),
               filename);

    // Bool also has only the "val" slot.
    class_c Bool_class = 
        new class_c(0,
               TreeConstants.Bool,
               TreeConstants.Object_,
               new Features(0)
               .appendElement(new attr(0,
                        TreeConstants.val,
                        TreeConstants.prim_slot,
                        new no_expr(0))),
               filename);

    // The class Str has a number of slots and operations:
    //       val                              the length of the string
    //       str_field                        the string itself
    //       length() : Int                   returns length of the string
    //       concat(arg: Str) : Str           performs string concatenation
    //       substr(arg: Int, arg2: Int): Str substring selection

    class_c Str_class =
        new class_c(0,
               TreeConstants.Str,
               TreeConstants.Object_,
               new Features(0)
               .appendElement(new attr(0,
                        TreeConstants.val,
                        TreeConstants.Int,
                        new no_expr(0)))
               .appendElement(new attr(0,
                        TreeConstants.str_field,
                        TreeConstants.prim_slot,
                        new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.length,
                          new Formals(0),
                          TreeConstants.Int,
                          new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.concat,
                          new Formals(0)
                          .appendElement(new formalc(0,
                                     TreeConstants.arg, 
                                     TreeConstants.Str)),
                          TreeConstants.Str,
                          new no_expr(0)))
               .appendElement(new method(0,
                          TreeConstants.substr,
                          new Formals(0)
                          .appendElement(new formalc(0,
                                     TreeConstants.arg,
                                     TreeConstants.Int))
                          .appendElement(new formalc(0,
                                     TreeConstants.arg2,
                                     TreeConstants.Int)),
                          TreeConstants.Str,
                          new no_expr(0))),
               filename);

    /* Do something with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */
    String objectName = Object_class.getName().getString();
    classGraph.put(objectName, new ArrayList());
    // object has no parent
    nameToNodeMap.put(objectName, Object_class);

    String ioName = IO_class.getName().getString();
    classGraph.put(ioName, new ArrayList());
    classGraph.get(objectName).add(ioName);
    nameToNodeMap.put(ioName, Object_class);

    String intName = Int_class.getName().getString();
    classGraph.put(intName, new ArrayList());
    classGraph.get(objectName).add(intName);
    nameToNodeMap.put(intName, Object_class);

    String boolName = Bool_class.getName().getString();
    classGraph.put(boolName, new ArrayList());
    classGraph.get(objectName).add(boolName);
    nameToNodeMap.put(boolName, Object_class);

    String strName = Str_class.getName().getString();
    classGraph.put(strName, new ArrayList());
    classGraph.get(objectName).add(strName);
    nameToNodeMap.put(strName, Object_class);

    //primitiveClasses.add(objectName);
    primitiveClasses.add(ioName);
    primitiveClasses.add(intName);
    primitiveClasses.add(boolName);
    primitiveClasses.add(strName);

    basicClasses.add(Object_class);
    basicClasses.add(IO_class);
    basicClasses.add(Int_class);
    basicClasses.add(Bool_class);
    basicClasses.add(Str_class);



    // NOT TO BE INCLUDED IN SKELETON
    
//  Object_class.dump_with_types(System.err, 0);
//  IO_class.dump_with_types(System.err, 0);
//  Int_class.dump_with_types(System.err, 0);
//  Bool_class.dump_with_types(System.err, 0);
//  Str_class.dump_with_types(System.err, 0);
    }
    

    /** Add the provided classes to the class graph
     *
     * Expects the passes in classes to be an enumeration
     *
     * */
    public ClassTable(Classes cls) {
    semantErrors = 0;
    errorStream = System.err;
    
    /* fill this in */
    classGraph = new Hashtable<String, ArrayList<String>>();
    nameToNodeMap = new Hashtable<String, class_c>();
    primitiveClasses = new HashSet<String>();
    basicClasses = new Vector<class_c>();

    installBasicClasses(); // add the basic class to the class table

    /* required because classes do not need to be declared in order */
    LinkedList<class_c> noParent = new LinkedList();

    for (Enumeration<TreeNode> e = cls.getElements(); e.hasMoreElements();){
        class_c curClass = (class_c)e.nextElement();
        String curName = curClass.getName().getString();
        String parName = curClass.getParent().getString();

        if(primitiveClasses.contains(parName)){
            String errorStr = "\n\tCannot inherit from primitive class \"" + parName + "\".\n";
            semantError(curClass).append(errorStr).flush();
            System.exit(0);
        }

        if (!classGraph.containsKey(curName)){
            if (classGraph.containsKey(parName)){
                classGraph.put(curName, new ArrayList<String>());
                classGraph.get(parName).add(curName);
                nameToNodeMap.put(curName, curClass);
            }else{
                noParent.offer(curClass);
            }
        }else{
            String errorStr = "\n\tDuplicate class name found. \"" + curName + "\"\n";
            semantError(curClass).append(errorStr).flush();
            System.exit(0);
        }
    }

    /* clean up classes declared out of order.
     *
     *  Loop through a queue looking for classes with its parent already in the table.
     *  if such a class is found, then remove it from the queue and add to table
     *  else add back to the queue
     *
     *  if the entire queue is traversed without removing anything then there is a class
     *  without an invalid parent
     */
    boolean didChange = true;
    while(noParent.size ()!= 0 && didChange){
        didChange = false;
        int oldSize = noParent.size();
        for (int i = 0; i < oldSize; i++){
            class_c curClass = noParent.remove();
            String curName = curClass.getName().getString();
            String parName = curClass.getParent().getString();

            if (!classGraph.containsKey(curName)){
                if (classGraph.containsKey(parName)){
                    classGraph.put(curName, new ArrayList<String>());
                    classGraph.get(parName).add(curName);
                    nameToNodeMap.put(curName, curClass);
                    didChange = true;
                }else{
                    noParent.offer(curClass);
            }
        }else{
            String errorStr = "\n\tDuplicate class name found. \"" + curName + "\"\n";
            semantError(curClass).append(errorStr).flush();
            System.exit(0);
        }
    }
    }

    /* there are classes with no parent
        all classes in noParent don't have valid parents,
        but we only report the first ('cause we're lazy) */
    if(noParent.size() != 0){
        // report some error, class with no parent
        class_c curClass = noParent.remove();
        String parName = curClass.getParent().getString();
        String errorStr = "\n\tNo parent class \"" + parName + "\" was found.\n";
        semantError(curClass).append(errorStr).flush();
        System.exit(0);
    }



    checkClassHierarchy();
    }

    /** Check or valid class hierarchy
     *
     * prints some error messages and ends execution if there is a cycle in the graph.
     *
     * @param ct the class table
     */
    public void checkClassHierarchy(){
        HashSet<String> marked = new HashSet();

        Set<String> keys = classGraph.keySet();
        for(String key: keys){
            if(marked.contains(key)){
                class_c curClass = nameToNodeMap.get(key);
                String errorStr = "\n\tCircular class hierarchy found with \"" + key + "\"\n";
                semantError(curClass).append(errorStr).flush();
                System.exit(0);
            }else{
                marked.add(key);
            }
        }
    }

    public Enumeration getBasicElements() {
        return basicClasses.elements();
    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
    return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
    errorStream.print(filename + ":" + t.getLineNumber() + ": ");
    return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
    semantErrors++;
    return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
    return semantErrors != 0;
    }

    // NOT TO BE INCLUDED IN SKELETON
    public static void main(String[] args) {
    new ClassTable(null).installBasicClasses();
    }

    public String toString(){
        String s = "classGraph size: " + classGraph.size() + "\n";
        int indent = 0;
        Set<String> keys = classGraph.keySet();
        for(String key: keys){
            s += "\t" + key + ": " + classGraph.get(key).toString() + "\n";
        }
        return s;
    }

}
              
    