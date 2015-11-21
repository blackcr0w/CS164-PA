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
// CAN MODIFY
import java.io.PrintStream;
import java.util.*;
// import java.util.Vector;
// import java.util.Enumeration;

//MethodNodePair class stores the CgenNode where the method is textually defined and the method definition
class MethodNodePair {
    CgenNode node;
    method mt;

    public MethodNodePair(CgenNode node, method mt) {
        this.node = node;
        this.mt = mt;
    }

    //to use by Vector.indexOf method to test for equality in CgenClassTable.installAllClassFeaturesHelper
    public boolean equals(Object o){
        return o instanceof MethodNodePair && mt.name.equals(((MethodNodePair) o).mt.name);
    }

}

class CgenNode extends class_c {
    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;
    
    /** Does this node correspond to a basic class? */
    private int basic_status;
    
    /** The class tag **/
    private int tag;   

    //all methods, including both the inherited and locally-defined methods
    private Vector<MethodNodePair> methods;
    //all inherited attributes
    private Vector<attr> inheritedAttrs;
    //all locally-defined(new) attributes
    private Vector<attr> localAttrs;    

    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
	super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
	this.parent = null;
	this.children = new Vector();
	this.basic_status = basic_status;
	AbstractTable.stringtable.addString(name.getString());

    //set the default values
    this.tag = -1;
    this.methods = null;
    this.inheritedAttrs = null;
    this.localAttrs = null;
    AbstractTable.stringtable.addString(name.getString());
    }

    void addChild(CgenNode child) {
	children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
	return children.elements(); 
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
	if (this.parent != null) {
	    Utilities.fatalError("parent already set in CgenNode.setParent()");
	}
	if (parent == null) {
	    Utilities.fatalError("null parent in CgenNode.setParent()");
	}
	this.parent = parent;
    }    
	

    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
	return parent; 
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() { 
	return basic_status == Basic; 
    }

    int getClassTag() {
        if(this.tag == -1){
            // System.out.println("this.tag = " + Integer.toString(this.tag));
            // Utilities.fatalError("class tag not yet set in CgenNode.getClassTag");
        }
        return this.tag;
    }

    // jk: set the classTag of current class, changeName
    void setClassTag(int classTag) {
    // the initial tag should be -1
    if (this.tag != -1) {
    System.out.println("this.tag = " + Integer.toString(this.tag));
    // Utilities.fatalError("class tag already set to " + this.tag + " in CgenNode.setClassTag");
    }
    this.tag = classTag;
    }
    
    void setMethods(Vector<MethodNodePair> methods) {
    if(this.methods != null){
    Utilities.fatalError("methods already set in CgenNode.setMethods");
    }

    this.methods = methods;
    }

    Vector<MethodNodePair> getMethods() {
        if(this.methods == null){
            Utilities.fatalError("methods not yet set in CgenNode.getMethods");
        }
        return this.methods;
    }

    //get locally-defined methods of this CgenNode, including the locally-overriden methods inherited from parent
    Vector<MethodNodePair> getLocalDefinedMethods() {
        if(this.methods == null){
            Utilities.fatalError("methods not yet set in CgenNode.getLocalDefinedMethods");
        }

        Vector<MethodNodePair> localMethods = new Vector<MethodNodePair>();
        for (MethodNodePair met : this.methods) {
            if (met.node.name.equals(this.name)) localMethods.add(met);
        }

        return localMethods;
    }

    //get method offset in dispatch table
    int getMethodOffset(AbstractSymbol methodName){
        for(int i = 0; i < methods.size(); i++){
            if(methods.get(i).mt.name.equals(methodName)) return i; 
        }
        Utilities.fatalError("can't find method in CgenNode.getMethodOffset");
        return -1;
    }

    void setInheritedAttrs(Vector<attr> inheritedAttrs) {
        if(this.inheritedAttrs != null){
            Utilities.fatalError("inherited attrs already set in CgenNode.setInheritedAttrs");
        }
        this.inheritedAttrs = inheritedAttrs;
    }

    void setLocalAttrs(Vector<attr> localAttrs) {
        if(this.localAttrs != null) {
            Utilities.fatalError("local attrs already set in CgenNode.setLocalAttrs");
        }
        this.localAttrs = localAttrs;
    }

    Vector<attr> getLocalAttrs() {
        if(this.localAttrs == null){
            Utilities.fatalError("local attrs not yet set in CgenNode.getLocalAttrs");
        }
        return this.localAttrs;
    } 
    
    //get both inherited attributes and locally-defined attributes
    Vector<attr> getAllAttrs(){
        if(this.inheritedAttrs == null || this.localAttrs == null){
            Utilities.fatalError("either local attrs or inherited attrs not yet set in CgenNode.getAllAttrs");
        }
        Vector<attr> allAttrs = new Vector<attr>(this.inheritedAttrs);
        allAttrs.addAll(this.localAttrs);
        return allAttrs;
    }

    //get attribute offset
    int getAttrOffset(AbstractSymbol attrName){
        Vector<attr> attrs = getAllAttrs();
        for(int i = 0; i < attrs.size(); i++){
            if(attrs.get(i).name.equals(attrName)) return (3 + i);
        }
        Utilities.fatalError("can't find attr in CgenNode.getAttrOffset");
        return -1;
    }        

    //get all descendants(children) of this CgenNode
    Set<CgenNode> getAllDescendants(){
        Set<CgenNode> descendants = new HashSet<CgenNode>();
        addDescendants(descendants, this);
        return descendants;
    }

    //helper method for getAllDescendants; add descendants through a depth-first search
    private void addDescendants(Set<CgenNode> descendants, CgenNode curNode){
        descendants.add(curNode);

        for (Enumeration<CgenNode> e = curNode.getChildren(); e.hasMoreElements();) {
            CgenNode child = (CgenNode)e.nextElement();
            addDescendants(descendants, child);
        }

    }    


}
    

    
