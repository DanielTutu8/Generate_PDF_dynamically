package com.sara.com.dynamic;

import java.io.FileOutputStream;
import java.sql.*;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.xdevapi.Table;

import javafx.scene.text.TextAlignment;

import com.itextpdf.text.*;

public class Generate_PDF_Dynamic {
	
	public static PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
	
	public static PdfPCell getCell2(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    return cell;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			String file_name = "D:\\Eclipse\\factura.pdf";
			Document document = new Document();
			
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
	
			document.open();
			
			DBConnection obJDBConnection = new DBConnection();
			Connection connection = obJDBConnection.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			//The ID must be read from somewhere//
			int IdCursa = 1; 

			//Add query from your database//
			String querycumparator = "select IdCursa, companii.Sediu, companii.Cont, companii.Banca, companii.Nume, companii.Cif, "
					+ "companii.NrORC, curse.datacomanda, curse.adresadestinatie, curse.adresaplecare, curse.km, curse.tarif, "
					+ "users.Nume, users.Prenume, users.Buletin, masini.NrInmatriculare "
					+ "from companii, masini, curse, users "
					+ "where companii.IdCompanie = curse.IdCompanie and masini.IdMasina = curse.IdMasina "
					+ "and users.IdUser = curse.IdSofer and curse.IdCursa=" + IdCursa +";";
			
			ps = connection.prepareStatement(querycumparator);
			rs = ps.executeQuery();
			
	        if(rs.next()){

	            byte[] newTripUUID = rs.getBytes(1);

	        }

			
			Font bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD);
			
			
			Paragraph preface = new Paragraph("FACTURA", bold); 
			preface.setAlignment(Element.ALIGN_CENTER);
			document.add(preface);			

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.addCell(getCell("Furnizor:......SCM Mutam.......", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("Cumparator: " + rs.getString("companii.nume"), PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell(".............................................", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell(".............................................", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("C.I.F:........RO 334123..........", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("Seria............nr................", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("C.I.F:........."+rs.getString("Cif") + ".........", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("Nr. O.R.C:...j56/222/2000....", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("Data...............................", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("Nr. O.R.C:...."+ rs.getString("NrORC")+"...", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("Domiciliu/Sediul: str. Cucuiului", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("Domiciliu/Sediul:..................", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell(".......nr. 252, Vaslui................", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell(rs.getString("Sediu"), PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("Judetul:.........Vaslui................", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("Judetul..................................", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("IBAN: RO56 BNCB XXXX XXXX XXXX XXXX XXXX", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("IBAN: "+ rs.getString("Cont"), PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("Banca:........BCR Vaslui.........", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("Banca:............."+rs.getString("Banca") + ".............", PdfPCell.ALIGN_RIGHT));
			table.addCell(getCell("Capital social:........................", PdfPCell.ALIGN_LEFT));
			table.addCell(getCell("", PdfPCell.ALIGN_CENTER));
			table.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
			
			document.add(table);

			
			PdfPTable tableproducts = new PdfPTable(6);
			float[] width = {0.5f, 3f, 0.5f, 1f, 2f, 2f};
			tableproducts.setSpacingBefore(11f);
			tableproducts.setSpacingAfter(11f);
			tableproducts.setWidths(width);
			tableproducts.setWidthPercentage(100);
			tableproducts.addCell(getCell2("Nr. crt.\n\n",PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("Denumirea produselor sau a serviciilor", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("U.M.", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("Cantitatea\n-km-", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("Pretul unitar\n-lei-",PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("Valoare\n-lei-", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("0", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("1", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("2\n\n",PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("3", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("4", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("5(3x4)", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("1\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("Transport intern " + rs.getString("curse.adresaplecare") + " - " + rs.getString("curse.adresadestinatie"), PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("1",PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2(""+ rs.getInt("curse.km"), PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("" + rs.getInt("curse.tarif"), PdfPCell.ALIGN_CENTER));
			tableproducts.addCell(getCell2("" + rs.getInt("curse.km")*rs.getInt("curse.tarif"), PdfPCell.ALIGN_CENTER));
			document.add(tableproducts);
			
			PdfPTable tablelast = new PdfPTable(4);
			float[] width2 = {1.5f, 3.5f, 2f, 2f};
			tablelast.setSpacingBefore(11f);
			tablelast.setSpacingAfter(11f);
			tablelast.setWidths(width2);
			tablelast.setWidthPercentage(100);
			tablelast.addCell(getCell2("\nSemnatura si stampila furnizorului", PdfPCell.ALIGN_CENTER));
			tablelast.addCell(getCell2("           Date privind expeditia:\n Numele delegatului: " + rs.getString("users.nume") + " " + rs.getString("users.prenume") + "\n"
					+ " B.I./C.I. seria, nr: ......." + rs.getString("users.buletin") + ".......\n eliberat(a).....................................\n"
					+ " Mijlocul de transport:....................\n nr:..........." +rs.getString("masini.nrinmatriculare") + "....................\n",0));

			tablelast.addCell(getCell2("\nTotal\ndin care:\naccize", PdfPCell.ALIGN_CENTER));
			tablelast.addCell(getCell2("\n\n" + rs.getInt("curse.km")*rs.getInt("curse.tarif")+ "\n\n-----------------------------\n", PdfPCell.ALIGN_CENTER));
			tablelast.addCell(getCell2("", PdfPCell.ALIGN_CENTER));
			tablelast.addCell(getCell2(" Expediarea s-a facut in prezenta\n noastra la data de:..........................\n"
					+ " ora:..................................................\n"
					+ " Semnaturile:....................................\n\n", 0));
			tablelast.addCell(getCell2("\nSemnatura\nde primire\n\n", PdfPCell.ALIGN_CENTER));
			tablelast.addCell(getCell2("", PdfPCell.ALIGN_CENTER));
			document.add(tablelast);

			
			document.close();
			
			System.out.println("If you cand read this, then something is working...");
		}catch(Exception e){
		System.err.println(e);
	}
	}

}
