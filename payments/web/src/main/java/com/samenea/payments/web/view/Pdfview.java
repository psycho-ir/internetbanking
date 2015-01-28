package com.samenea.payments.web.view;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.*;
import com.samenea.commons.component.utils.persian.NumberUtil;
import com.samenea.payments.web.model.ResultViewModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 * Date: 3/14/13
 * Time: 11:38 AM
 *
 * @Author:payam
 */
public class Pdfview extends AbstractIText5PdfView {
    private static final String INSTALLMENT_PAY_PRODUCT_NAME = "InstallmentPay";
    private static final String DEPOSIT_CHARGE_PRODUCT_NAME = "DepositCharge";
    private String FONT_PATH = "tahoma.ttf";

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=UserAccessReport.pdf");
        PdfPTable table = new PdfPTable(2);

        ResultViewModel resultViewModel = (ResultViewModel) model.get("resultViewModel");
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        if (resultViewModel.getTypeOfResult().equals(DEPOSIT_CHARGE_PRODUCT_NAME)) {
            table.addCell(addRow(resultViewModel.getPdfTemplateView().getDepositTitles()));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getDepositDescription(), getPersianFont(10)));
            table.addCell(new Paragraph(NumberUtil.convertDigits(resultViewModel.getDepositNumber()), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getOrderState(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getOrderState(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getAmountText(), getPersianFont(10)));
            table.addCell(new Paragraph(NumberUtil.convertDigits(spreate(resultViewModel.getAmount())) + resultViewModel.getPdfTemplateView().getRial(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getDateText(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getDate(), getPersianFont(10)));
            if (resultViewModel.getReferenceId() != null && resultViewModel.getReferenceId().trim() != "") {
                table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getReferenceId(), getPersianFont(10)));
                table.addCell(new Paragraph(resultViewModel.getReferenceId(), getPersianFont(10)));
            }
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getTrackingTitles(), getPersianFont(10)));
            table.addCell(resultViewModel.getTransactionId());
            table.addCell(addRow(resultViewModel.getPdfTemplateView().getSamenTitles()));
            table.addCell(addLink(resultViewModel.getPdfTemplateView().getTrackingLink()));
        } else if (resultViewModel.getTypeOfResult().equals(INSTALLMENT_PAY_PRODUCT_NAME)) {
            table.addCell(addRow(resultViewModel.getPdfTemplateView().getLoanTitles()));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getLoanDescription(), getPersianFont(10)));
            table.addCell(new Paragraph(NumberUtil.convertDigits(resultViewModel.getLoanNumber()), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getInstallment_number(), getPersianFont(10)));
            table.addCell(new Paragraph(NumberUtil.convertDigits(resultViewModel.getInstallmentNumber()), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getOrderState(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getOrderState(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getAmountText(), getPersianFont(10)));
            table.addCell(new Paragraph(NumberUtil.convertDigits(spreate(resultViewModel.getAmount())) + resultViewModel.getPdfTemplateView().getRial(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getDateText(), getPersianFont(10)));
            table.addCell(new Paragraph(resultViewModel.getDate(), getPersianFont(10)));
            if (resultViewModel.getReferenceId() != null && resultViewModel.getReferenceId().trim() != "") {
                table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getReferenceId(), getPersianFont(10)));
                table.addCell(new Paragraph(resultViewModel.getReferenceId(), getPersianFont(10)));
            }
            table.addCell(new Paragraph(resultViewModel.getPdfTemplateView().getTrackingTitles(), getPersianFont(10)));
            table.addCell(resultViewModel.getTransactionId());
            table.addCell(addRow(resultViewModel.getPdfTemplateView().getSamenTitles()));
            table.addCell(addLink(resultViewModel.getPdfTemplateView().getTrackingLink()));
        }


        table.addCell(addSpamEmailAlert(resultViewModel.getPdfTemplateView().getSpam_email_text()));
        document.add(addLogo());
        document.add(table);
    }

    private Font getPersianFont(float size) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontPersian = new Font(baseFont, size, Font.NORMAL, BaseColor.DARK_GRAY);
        return fontPersian;

    }

    private Font getPersianFont(float size, BaseColor baseColor) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontPersian = new Font(baseFont, size, Font.NORMAL, baseColor);
        return fontPersian;

    }

    private String spreate(String amountNumber) {
        char[] myNumber = amountNumber.toCharArray();
        String myResult = "";
        for (int i = amountNumber.length() - 1; i >= 0; i--) {
            myResult = myNumber[i] + myResult;
            if ((myNumber.length - i) % 3 == 0 & i > 0)
                myResult = "," + myResult;
        }

        return myResult;

    }

    private PdfPCell addRow(String title) throws IOException, DocumentException {
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Paragraph(title, getPersianFont(10, BaseColor.WHITE)));
        PdfPCell cell = new PdfPCell(header);
        BaseColor color = WebColors.getRGBColor("#459357");
        cell.setBackgroundColor(color);
        cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell addSpamEmailAlert(String title) throws IOException, DocumentException {
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Paragraph(title, getPersianFont(10, BaseColor.BLACK)));
        PdfPCell cell = new PdfPCell(header);
        cell.setBackgroundColor(WebColors.getRGBColor("#FEEFB3"));
        cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell addLink(String link) throws IOException, DocumentException {
        Chunk imdb = new Chunk(link, getPersianFont(10, BaseColor.WHITE));
        imdb.setAction(new PdfAction(new URL(link)));
        Paragraph paragraph = new Paragraph(imdb);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(paragraph);
        BaseColor color = WebColors.getRGBColor("#459357");
        cell.setBackgroundColor(color);
        cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private Image addLogo() throws BadElementException, IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        URL vImgURL = cl.getResource("Logo.png");
        Image logo = Image.getInstance(vImgURL);
        logo.setAlignment(Element.ALIGN_MIDDLE);
        logo.scaleAbsoluteWidth(161f);
        logo.scaleAbsoluteHeight(78f);
        return logo;
    }


}
