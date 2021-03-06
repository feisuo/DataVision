package jimm.datavision.testdata.oracle;
import jimm.datavision.testdata.SchemaGen;

public class OracleSchemaGen extends SchemaGen {

protected void makeTable(String tableName) {
    String name = printableName(tableName);
    System.out.println("drop table " + name + ";");
    System.out.print("create table " + name + " (");
}

protected void endTable() {
    System.out.println();
    System.out.println(");");
}

protected void printType(String type, int size) {
    System.out.print(" ");
    if ("integer".equals(type))
      System.out.print("int");
    else if ("date".equals(type))
	System.out.print("date");
    else if ("boolean".equals(type))
	System.out.print("char(1)");
    else if ("string".equals(type))
	System.out.print("varchar2(" + size + ")");
}

protected void printNotNull() {
    System.out.print(" not null");
}

protected void printPrimaryKey() {
    System.out.print(" primary key");
}

public static void main(String[] args) {
    new OracleSchemaGen().run("../schema.xml");
}
}
