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
import java.util.Vector;
import java.util.Enumeration;

class MethodNode {
    CgenNode currNode;
    method currMt;

    public MethodNode(CgenNode node, method method) {
    currNode = node;
    currMt = method;
    }
    //  overrive equals method
    public boolean equals(Object o){
    return o instanceof MethodNode && currMt.name.equals(((MethodNode) o).currMt.name);
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

    private int classTag;  // jk: the class tag of every cgenNode
    // using "private" attributes to implement encapsulation, attrs are 
    // only accessible using methods

    private Vector<attr> inheritedAttrs;  // jk: inherited attrs
    private Vector<attr> localAttrs;  // jk: local/new-defined attrs
    private Vector<MethodNode> methods;  // jk: all methods of current node

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
    this.inheritedAttrs = null;
    this.localAttrs = null;
    this.methods = null;
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

    void setTag(int currTag) {
    this.classTag = currTag;
    }

    int getTag() {
    return this.classTag;
    }

    void setMethods(Vector<MethodNode> methods) {
    if(this.methods != null){
        Utilities.fatalError("methods already set in CgenNode.setMethods");
    }
    this.methods = methods;
    }

    Vector<MethodNode> getMethods() {
    if(this.methods == null){
        Utilities.fatalError("methods not yet set in CgenNode.getMethods");
    }
    return this.methods;
    }

    //get locally-defined methods of this CgenNode, including the locally-overriden methods inherited from parent
    Vector<MethodNode> getLocalDefinedMethods() {
    if(this.methods == null){
        Utilities.fatalError("methods not yet set in CgenNode.getLocalDefinedMethods");
    }

    Vector<MethodNode> localMethods = new Vector<MethodNode>();
    for (MethodNode met : this.methods) {
        if (met.currNode.name.equals(this.name)) localMethods.add(met);
    }
    return localMethods;
    }

    void setInheritedAttrs(Vector<attr> inheritedAttrs) {
    if(this.inheritedAttrs != null){
        Utilities.fatalError("inheritedAttrs already set in CgenNode.setMethods");
    }
    this.inheritedAttrs = inheritedAttrs;
    }

    void setLocalAttrs(Vector<attr> localAttrs) {
    if(this.inheritedAttrs != null){
        Utilities.fatalError("localAttrs already set in CgenNode.setMethods");
    }
    this.localAttrs = localAttrs;
    }
    
    Vector<attr> getLocalAttrs() {
    if(this.localAttrs == null){
        Utilities.fatalError("local attrs not yet set in CgenNode.getLocalAttrs");
    }
    return this.localAttrs;
    }
    // jk: get both inherited attributes and local attributes
    Vector<attr> getAllAttrs(){
    if(this.inheritedAttrs == null || this.localAttrs == null){
        Utilities.fatalError("either local attrs or inherited attrs not yet set in CgenNode.getAllAttrs");
    }
    Vector<attr> allAttrs = new Vector<attr>(this.inheritedAttrs);
    allAttrs.addAll(this.localAttrs);
    return allAttrs;
    }    
    // Vector<MethodNodePair> getMethods() {
    // if(this.methods == null){
    //     Utilities.fatalError("methods not yet set in CgenNode.getMethods");
    // }
    // return this.methods;
    // }

    // //get locally-defined methods of this CgenNode, including the locally-overriden methods inherited from parent
    // Vector<MethodNodePair> getLocalDefinedMethods() {
    // if(this.methods == null){
    //     Utilities.fatalError("methods not yet set in CgenNode.getLocalDefinedMethods");
    // }

    // Vector<MethodNodePair> localMethods = new Vector<MethodNodePair>();
    // for (MethodNodePair met : this.methods) {
    //     if (met.node.name.equals(this.name)) localMethods.add(met);
    // }
    // return localMethods;
    // }    
}
    

    
