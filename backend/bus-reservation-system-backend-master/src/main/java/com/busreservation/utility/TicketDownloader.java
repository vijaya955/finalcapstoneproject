package com.busreservation.utility;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.busreservation.entity.JourneyBooking;
import com.busreservation.utility.Constants.JourneyClassType;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.VerticalPositionMark;

import jakarta.servlet.http.HttpServletResponse;

public class TicketDownloader {
	
	private List<JourneyBooking> bookings;
	
	public TicketDownloader() {
		
	}

	public TicketDownloader(List<JourneyBooking> bookings) {
		super();
		this.bookings = bookings;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new Color(237, 242, 243));
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(new Color(31, 53, 65));

		cell.setPhrase(new Phrase("Journey Seat", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Booing Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Status", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {
		for (JourneyBooking booking : bookings) {
			table.addCell(booking.getBusSeatNo().getSeatNo());
			
			if(booking.getJourneyClass().equals(JourneyClassType.BACK.value())) {
				table.addCell(String.valueOf(booking.getJourney().getBackSeatFare()));
			} else if(booking.getJourneyClass().equals(JourneyClassType.MIDDLE.value())) {
				table.addCell(String.valueOf(booking.getJourney().getMiddleSeatFare()));
			} else if(booking.getJourneyClass().equals(JourneyClassType.FRONT.value())) {
				table.addCell(String.valueOf(booking.getJourney().getFrontSeatFare()));
			}
		
			table.addCell(DateTimeUtils.getProperDateTimeFormatFromEpochTime(booking.getBookingTime()));
			table.addCell(booking.getStatus());
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Image image = Image.getInstance("classpath:images/logo.png");
		image.scalePercent(8.0f, 8.0f);
		document.add(image);

		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		headerFont.setSize(25);
		headerFont.setColor(new Color(31, 53, 65));
		Paragraph pHeader = new Paragraph("Journey Ticket Details\n", headerFont);
		pHeader.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(pHeader);

		Font fontD = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontD.setSize(13);
		fontD.setColor(Color.BLACK);
		Paragraph pD = new Paragraph("Journey Number: " + bookings.get(0).getJourney().getJourneyNumber(), fontD);
		pD.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(pD);

		Font fontAI = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontAI.setSize(13);
		fontAI.setColor(Color.BLACK);
		Paragraph pAI = new Paragraph("Customer Booking Id: " + bookings.get(0).getBookingId(), fontAI);
		pAI.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(pAI);

		Font fontP = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontP.setSize(18);
		fontP.setColor(new Color(31, 53, 65));
		Chunk glue = new Chunk(new VerticalPositionMark());
		Paragraph pp = new Paragraph("\nJourney Details", fontP);
		pp.add(new Chunk(glue));
		pp.add("Customer Details:");
		document.add(pp);

		Font fontN = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontN.setSize(12);
		fontN.setColor(Color.BLACK);
		Chunk glueN = new Chunk(new VerticalPositionMark());
		Paragraph pN = new Paragraph(
				"Bus: " + bookings.get(0).getJourney().getBus().getName(), fontN);
		pN.add(new Chunk(glueN));
		pN.add("Customer Name: " + bookings.get(0).getPassenger().getName());
		document.add(pN);

		Font fontA = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontA.setSize(12);
		fontA.setColor(Color.BLACK);
		Chunk glueA = new Chunk(new VerticalPositionMark());
		Paragraph pA = new Paragraph("Bus Registration: " + bookings.get(0).getJourney().getBus().getRegistrationNumber(),
				fontA);
		pA.add(new Chunk(glueA));
		pA.add("Customer Mobile No: " + bookings.get(0).getPassenger().getContact());
		document.add(pA);

		Font fontBG = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontBG.setSize(12);
		fontBG.setColor(Color.BLACK);
		Paragraph pBG = new Paragraph(
				"Depature BusStop: " + bookings.get(0).getJourney().getDepartureBusStop().getName(), fontBG);
		pBG.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(pBG);

		Font fontE = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontE.setSize(12);
		fontE.setColor(Color.BLACK);
		Paragraph pE = new Paragraph(
				"Arrival BusStop: " + bookings.get(0).getJourney().getArrivalBusStop().getName(), fontE);
		pE.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(pE);
		
		Font fontDTime = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontDTime.setSize(12);
		fontDTime.setColor(Color.BLACK);
		Paragraph pDTime = new Paragraph(
				"Departure Time: " + DateTimeUtils.getProperDateTimeFormatFromEpochTime(bookings.get(0).getJourney().getDepartureTime()), fontDTime);
		pDTime.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(pDTime);

		Font fontATime = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontATime.setSize(12);
		fontATime.setColor(Color.BLACK);
		Paragraph pATime = new Paragraph(
				"Arrival Time: " + DateTimeUtils.getProperDateTimeFormatFromEpochTime(bookings.get(0).getJourney().getArrivalTime()), fontATime);
		pATime.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(pATime);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(new Color(31, 53, 65));
		Paragraph p = new Paragraph("\nBooked Journey Seat Details\n", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.0f, 2.5f, 2.5f, 2.7f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);
		
		document.close();

	}

}
