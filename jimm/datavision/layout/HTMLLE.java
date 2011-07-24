package jimm.datavision.layout;
import jimm.datavision.*;
import jimm.datavision.field.*;
import jimm.util.StringUtils;
import java.io.PrintWriter;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * An HTML layout engine.
 *
 * @author Jim Menard, <a href="mailto:jim@jimmenard.com">jim@jimmenard.com</a>
 */
public class HTMLLE extends SortedLayoutEngine {

protected int rowX;

/**
 * Constructor.
 *
 * @param out a print writer
 */
public HTMLLE(PrintWriter out) {
    super(out);
}

protected void doStart() {
    out.println("<html>");
    out.println("<head>");
    out.println("<!-- Generated by DataVision version " + info.Version
		+ " -->");
    out.println("<!-- " + info.URL + " -->");
    String title = report.getTitle();
    if (title != null && title.length() > 0)
	out.println("<title>" + report.getTitle() + "</title>");
    out.println("</head>");
    out.println("<body bgcolor=\"white\">");
}

protected void doEnd() {
    out.println("</body>");
    out.println("</html>");
    out.flush();
}

protected void doOutputSection(Section s) {
    out.println("<table width=\"" + pageWidth() + "pt\">");
    out.println("<tr height=\"" + s.getOutputHeight() + "pt\">");
    rowX = 0;

    super.doOutputSection(s);

    // Pad remaining page width
    if (rowX < pageWidth())
	out.println("<td width=\"" + (pageWidth() - rowX)
		    + "pt\">&nbsp;</td>");

    out.println("</tr>");
    out.println("</table>");
}

protected void doOutputField(Field field) {
    Format format = outputCellStart(field);

    // Style attribute courtesy of Brendon Price <Brendon.Price@sytec.co.nz>
    out.print("<font style=\"");
    if (format.getFontFamilyName() != null)
	out.print("font-family: " + format.getFontFamilyName() + "; ");
    out.print("font-size: " + format.getSize() + "pt; ");
    outputColor(format.getColor());
    
    // Border code courtesy of Khadiyd Idris <khad@linuxindo.com>
    Border b = field.getBorderOrDefault();
    String bcolor = "black";
    if (b.getColor() != null) {
	bcolor = "#" + Integer.toHexString(b.getColor().getRed())
	    + Integer.toHexString(b.getColor().getGreen())
	    + Integer.toHexString(b.getColor().getBlue());
    }

    if (b.getTop() != null)
	out.print("border-top: solid " + bcolor + " "
		  + b.getTop().getThickness() + "pt; ");
    if (b.getLeft() != null)
	out.print("border-left: solid " + bcolor + " "
		  + b.getLeft().getThickness() + "pt; ");
    if (b.getBottom() != null)
	out.print("border-bottom: solid " + bcolor + " "
		  + b.getBottom().getThickness() + "pt; ");
    if (b.getRight() != null)
	out.print("border-right: solid " + bcolor + " "
		  + b.getRight().getThickness() + "pt; ");


    out.print("\">");

    if (format.isBold()) out.print("<b>");
    if (format.isItalic()) out.print("<i>");
    if (format.isUnderline()) out.print("<u>");

    String str = field.toString();
    if (str == null || str.length() == 0)
	str = "&nbsp;";

    // Fix courtesy of Brendon Price <Brendon.Price@sytec.co.nz>
    if ("&nbsp;".equals(str))
	out.print(str);
    else
	out.print(StringUtils.newlinesToXHTMLBreaks(StringUtils.escapeHTML(str)));

    if (format.isUnderline()) out.print("</u>");
    if (format.isItalic()) out.print("</i>");
    if (format.isBold()) out.print("</b>");
    out.print("</font>");

    outputCellEnd();
}

protected void doOutputImage(ImageField image) {
    if (image == null || image.getImageURL() == null)
	return;

    outputCellStart(image);

    ImageIcon icon = image.getImageIcon(); // For width and height
    String url = image.getImageURL().toString();
    if (url.startsWith("file:"))
	url = url.substring(5);

    // Make an alt attribute from the URL
    String alt = url;
    int pos = alt.lastIndexOf("/");
    if (pos != -1)
	alt = alt.substring(pos + 1);

    out.print("<img src=\"" + StringUtils.escapeHTML(url) + "\" alt=\""
	      + StringUtils.escapeHTML(alt)
	      + "\" width=\"" + icon.getIconWidth()
	      + "\" height=\"" + icon.getIconHeight()
	      + "\">");
    outputCellEnd();
}

protected Format outputCellStart(Field field) {
    Format format = field.getFormat();
    Rectangle bounds = field.getBounds();

    if (bounds.x > rowX)
	out.println("<td width=\"" + (bounds.x - rowX) + "pt\">&nbsp;</td>");
    rowX = (int)(bounds.x + bounds.width);

    String align = null;
    switch (format.getAlign()) {
    case Format.ALIGN_LEFT: align = "left"; break;
    case Format.ALIGN_CENTER: align = "center"; break;
    case Format.ALIGN_RIGHT: align = "right"; break;
    }

    out.print("<td align=\"" + align + "\" width=\"" + (int)bounds.width
	      + "pt\" height=\"" + (int)field.getOutputHeight() + "pt\"");
    if (!format.isWrap())
	out.print(" nowrap");
    out.print(">");

    return format;
}

protected void outputCellEnd() {
    out.println("</td>");
}

protected void outputColor(Color c) {
    if (c.equals(Color.black))
	return;

    int[] rgb = new int[3];
    rgb[0] = c.getRed();
    rgb[1] = c.getGreen();
    rgb[2] = c.getBlue();
    out.print(" color: #");
    for (int i = 0; i < 3; ++i) {
	if (rgb[i] < 16) out.print('0');
	out.print(Integer.toHexString(rgb[i]));
    }
}


protected void doOutputLine(Line line) {}

}

