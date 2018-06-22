package at.fh.swenga.exportpdf;

import java.awt.Color;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import at.fh.swenga.model.Todo;

public class PdfTodoReportView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// change the file name
		response.setHeader("Content-Disposition", "attachment; filename=\"todos.pdf\"");

		List<Todo> todos = (List<Todo>) model.get("todos");
		
		document.add(new Paragraph("Todo list"));
		

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 1.0f, 1.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Category", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Deadline", font));
		table.addCell(cell);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		// write table row data
		for (Todo todo : todos) {	
			table.addCell(todo.getName());
			table.addCell(todo.getCategory().getName());
			table.addCell(sdf.format(todo.getDate().getTime()));
			//table.addCell(todo.getDate().toString());
		}

		document.add(table);
	}


}