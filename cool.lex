/*
 * CS164: Spring 2004
 * Programming Assignment 2
 *
 * The scanner definition for Cool.
 *
 */

import java_cup.runtime.Symbol;

%%

/* Code enclosed in %{ %} is copied verbatim to the lexer class definition.
 * All extra variables/methods you want to use in the lexer actions go
 * here.  Don't remove anything that was here initially.  */
%{
    // Max size of string constants
    static int MAX_STR_CONST = 1024;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    // Keep track of number of nestedly opened comment. Should be 0 or greater.
    private int openCommentCounter = 0;

    /*
        stringError and the next four methods is for string errors.
    */
    private Boolean stringError = false;

    Boolean getStringError() {
        return stringError;
    }

    void nullExists() {
        stringError =true;
    }

    void stringTooLong() {
        stringError = true;
    }

    void stringErrorReset() {
        stringError = false;
    }

    // For line numbers
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }

    /* When processing multiple white spaces, lexer takes it as \s+.
     * 
     */
    int countNewLine (String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char cit = input.charAt(i);
            if (cit == '\n') {
                count++;
            }
        }
        return count;
    }
    
    /* This method deals with preserving "\n" "\b" "\t" "f" shape.
     * Without this function, "\n" is returned as "\\n".
     */
    void deleteEscape(StringBuffer toDelete) {
        for (int i = 0; i < toDelete.length(); i++) {
            char potential_backslash = toDelete.charAt(i);
            if (potential_backslash == '\\') {
                char nextone = toDelete.charAt(i + 1);
                if (nextone == 'n' || nextone == 'b' || nextone == 't' || nextone == 'f')
                   {
                       if (nextone == 'n') {
                     toDelete.setCharAt(i, '\n');
                     toDelete.deleteCharAt(i + 1);
                       }
                       else if (nextone == 'b') {
                     toDelete.setCharAt(i, '\b');
                     toDelete.deleteCharAt(i + 1);
                       }
                       else if(nextone == 't') {
                     toDelete.setCharAt(i, '\t');
                     toDelete.deleteCharAt(i + 1);
                       }
                       else if(nextone == 'f') {
                     toDelete.setCharAt(i, '\f');
                     toDelete.deleteCharAt(i + 1);
                       }
                    }
                else {
                	toDelete.deleteCharAt(i);
                }
            }
            
        }
    }

    /* See line number 260. This method takes two characters input starting with backslash. 
     * It returns a single character after the backslash.  */
    char withoutBackslash(String withBackslash) {
        return withBackslash.charAt(1);
    }

    /* commentOpen is called when lexer sees '(*', it increments the
     * open comment counter.
     */
    void commentOpen () {
        openCommentCounter++;
    }
    
    /* commentClose is called when lexer sees '*)'.
     * It decrements the open comment counter and returns it.
     */
    int commentClose () {
        openCommentCounter--;
        return openCommentCounter;
    }

    /* getCommentCounter simply returns openCommentCounter
     * @return open comment counter
     */
    int getCommentCounter () {
        return openCommentCounter;
    }

    /*
     * Add extra field and methods here.
     */
%}


/*  Code enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here. */
%init{
    // empty for now and then
%init}

/*  Code enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work. */
%eofval{
    switch(yystate()) {
    case YYINITIAL:

	break;

    case LINE_COMMENT:
        break;
    case COMMENT:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "EOF in comment");
    case STRING:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

/* Do not modify the following two jlex directives */
%class CoolLexer
%cup


/* This defines a new start condition for line comments.
 * .
 * Hint: You might need additional start conditions. */
%state LINE_COMMENT
%state STRING
%state COMMENT


%%

<YYINITIAL>\n	 { curr_lineno++; }
<YYINITIAL>\s+   { curr_lineno += countNewLine(yytext()); }

<YYINITIAL>"(*"  { commentOpen(); yybegin(COMMENT); }

<COMMENT>"(*" { commentOpen(); }
<COMMENT>"*)" { if (commentClose() == 0) yybegin(YYINITIAL); }
<COMMENT>[^\n\*\)\(]*   { }
<COMMENT>[\*\)\(] { }
<COMMENT>\n   { curr_lineno++; }


<YYINITIAL>"--"         { yybegin(LINE_COMMENT); }

<YYINITIAL>"(*"         { yybegin(NESTED_COMMENT); comment_depth++; }
<YYINITIAL>"*)"         { return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }

/* Do nothing in line comment */
<LINE_COMMENT>.*        { }
<LINE_COMMENT>\n        { curr_lineno++; yybegin(YYINITIAL); }



<YYINITIAL>"=>"		{ return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>"<="     { return new Symbol(TokenConstants.DARROW); }




<YYINITIAL>[0-9][0-9]*  { /* Integers */
                          return new Symbol(TokenConstants.INT_CONST,
					    AbstractTable.inttable.addString(yytext())); }

<YYINITIAL>\"  { string_buf.setLength(0); yybegin(STRING); }

<STRING>\0 { if (stringError) { } else { nullExists(); return new Symbol(TokenConstants.ERROR, "String contains null character"); } }
<STRING>[^\n\"\\]* { string_buf.append(yytext()); if (string_buf.length() > MAX_STR_CONST) {
            if (stringError) { }
            else {
                stringTooLong();
                return new Symbol(TokenConstants.ERROR, "String constant too long"); 
            }
        }
    }
<STRING>\" { yybegin(YYINITIAL); deleteEscape(string_buf); 
            if (getStringError() == true) {
                stringErrorReset();
                yybegin(YYINITIAL);
            } else { 
             return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));
            } 
        }
<STRING>\n { curr_lineno++; yybegin(YYINITIAL); return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); }

/* If it sees a backslash followed by a normal character, it should returns the character without the backslash. */
<STRING>\\[^bnft\n] { string_buf.append(withoutBackslash(yytext())); }

/* If it sees a backslash followed by a linebreak, it should returns the only linebreak as above.
 * The only difference is this increments line number. */
<STRING>\\\n { string_buf.append(withoutBackslash(yytext())); curr_lineno++; }

/* If it sees a backslash followed by n, b, f, t. It should keep the backslash.
 * In some reason this actually adds one backslash. This added backslash is removed in deleteEscape() method.
 */
<STRING>\\[nbft] { string_buf.append(yytext()); }



<YYINITIAL>[Cc][Aa][Ss][Ee]	{ return new Symbol(TokenConstants.CASE); }
<YYINITIAL>[Cc][Ll][Aa][Ss][Ss] { return new Symbol(TokenConstants.CLASS); }
<YYINITIAL>[Ee][Ll][Ss][Ee]  	{ return new Symbol(TokenConstants.ELSE); }
<YYINITIAL>[Ee][Ss][Aa][Cc]	{ return new Symbol(TokenConstants.ESAC); }
<YYINITIAL>f[Aa][Ll][Ss][Ee]	{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.FALSE); }
<YYINITIAL>[Ff][Ii]             { return new Symbol(TokenConstants.FI); }
<YYINITIAL>[Ii][Ff]  		{ return new Symbol(TokenConstants.IF); }
<YYINITIAL>[Ii][Nn]             { return new Symbol(TokenConstants.IN); }
<YYINITIAL>[Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss] { return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL>[Ii][Ss][Vv][Oo][Ii][Dd] { return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL>[Ll][Ee][Tt]         { return new Symbol(TokenConstants.LET); }
<YYINITIAL>[Ll][Oo][Oo][Pp]  	{ return new Symbol(TokenConstants.LOOP); }
<YYINITIAL>[Nn][Ee][Ww]		{ return new Symbol(TokenConstants.NEW); }
<YYINITIAL>[Nn][Oo][Tt] 	{ return new Symbol(TokenConstants.NOT); }
<YYINITIAL>[Oo][Ff]		{ return new Symbol(TokenConstants.OF); }
<YYINITIAL>[Pp][Oo][Oo][Ll]  	{ return new Symbol(TokenConstants.POOL); }
<YYINITIAL>[Tt][Hh][Ee][Nn]   	{ return new Symbol(TokenConstants.THEN); }
<YYINITIAL>t[Rr][Uu][Ee]	{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.TRUE); }
<YYINITIAL>[Ww][Hh][Ii][Ll][Ee] { return new Symbol(TokenConstants.WHILE); }


<YYINITIAL> "self"    { return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString("self")); }

<YYINITIAL> "SLEF_TYPE"    { return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString("self")); }


<YYINITIAL>[a-z][A-Za-z0-9_]* { return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext())); }
<YYINITIAL> [A-Z][a-zA-Z0-9_]* { return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext()));}


<YYINITIAL>"+"			{ return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"/"			{ return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"-"			{ return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"*"			{ return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"="			{ return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"<"			{ return new Symbol(TokenConstants.LT); }
<YYINITIAL>"."			{ return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"~"			{ return new Symbol(TokenConstants.NEG); }
<YYINITIAL>","			{ return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>";"			{ return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>":"			{ return new Symbol(TokenConstants.COLON); }
<YYINITIAL>"("			{ return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>")"			{ return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"@"			{ return new Symbol(TokenConstants.AT); }
<YYINITIAL>"}"			{ return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>"{"			{ return new Symbol(TokenConstants.LBRACE); }

<YYINITIAL>"->"			{ return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>"<-"         { return new Symbol(TokenConstants.ASSIGN); }

<YYINITIAL>. {return new Symbol(TokenConstants.ERROR, yytext()); }

.                { /*
                    *  This should be the very last rule and will match
                    *  everything not matched by other lexical rules.
                    */
                   System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
