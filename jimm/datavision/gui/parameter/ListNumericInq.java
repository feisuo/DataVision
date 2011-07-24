package jimm.datavision.gui.parameter;
import jimm.datavision.Parameter;

/**
 * A multiple-choice  numeric list inquisitor knows how to display and
 * control the widgets needed to ask a user for multiple numeric parameter
 * values from a list.
 *
 * @author Jim Menard, <a href="mailto:jim@jimmenard.com">jim@jimmenard.com</a>
 */
class ListNumericInq extends ListStringInq {

ListNumericInq(Parameter param, boolean allowMultipleSelection) {
    super(param, allowMultipleSelection);
}

}
