package jimm.datavision.gui.cmd;
import jimm.datavision.source.Query;
import jimm.datavision.source.Join;
import jimm.util.I18N;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A command for changing a field's table join.
 *
 * @author Jim Menard, <a href="mailto:jim@jimmenard.com">jim@jimmenard.com</a>
 */
public class TableJoinCommand extends CommandAdapter {

protected Query query;
protected Collection newJoins;
protected Collection origJoins;

public TableJoinCommand(Query query, Collection joins) {
    super(I18N.get("TableJoinCommand.name"));
    this.query = query;

    origJoins = new ArrayList();
    for (Join j : query.joins())
	origJoins.add(j);

    newJoins = joins;
}

public void perform() {
    query.clearJoins();
    query.addAllJoins(newJoins);
}

public void undo() {
    query.clearJoins();
    query.addAllJoins(origJoins);
}

}
