package com.dukankhata.invoice;

import com.dukankhata.customer.Customer;
import com.dukankhata.pos.Sale;
import com.dukankhata.pos.SaleItem;
import com.dukankhata.shop.Shop;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class InvoicePdfService {
 public byte[] generate(Sale sale) throws IOException {
  try(PDDocument d=new PDDocument(); ByteArrayOutputStream out=new ByteArrayOutputStream()) {
   PDPage page=new PDPage(PDRectangle.A4); d.addPage(page);
   try(PDPageContentStream c=new PDPageContentStream(d,page)) {
    float y=790; Shop shop=sale.getShop(); Customer customer=sale.getCustomer();
    c.setFont(font(true),18); text(c,50,y,"DukanKhata - "+(shop.getGstin()!=null?"TAX INVOICE":"INVOICE")); y-=25;
    c.setFont(font(true),12); text(c,50,y,safe(shop.getLegalName(),shop.getName())); y-=16;
    c.setFont(font(false),9);
    if(shop.getGstin()!=null) { text(c,50,y,"GSTIN: "+shop.getGstin()); y-=13; }
    String address=join(shop.getAddressLine(),shop.getCity(),shop.getState(),shop.getPincode()); if(!address.isBlank()){text(c,50,y,address); y-=13;}
    text(c,380,790,"Invoice No: "+sale.getInvoiceNumber()); text(c,380,777,"Date: "+sale.getCreatedAt());
    if(customer!=null){ y-=8; c.setFont(font(true),10); text(c,50,y,"Bill To"); y-=13; c.setFont(font(false),9); text(c,50,y,customer.getName()); y-=12; if(customer.getGstin()!=null){text(c,50,y,"GSTIN: "+customer.getGstin());y-=12;} String ca=join(customer.getAddress(),null,null,null); if(!ca.isBlank()) text(c,50,y,ca); }
    y-=30; c.setFont(font(true),8); text(c,50,y,"Item"); text(c,275,y,"HSN"); text(c,325,y,"Qty"); text(c,365,y,"Rate"); text(c,420,y,"GST%"); text(c,470,y,"Tax"); text(c,525,y,"Amount"); y-=12;
    c.setFont(font(false),8);
    for(SaleItem i:sale.getItems()) { if(y<170) break; text(c,50,y,trim(i.getItemName(),32)); text(c,275,y,safe(i.getHsnCode(),"-")); text(c,325,y,i.getQuantity().toPlainString()); text(c,365,y,i.getUnitPrice().toPlainString()); text(c,420,y,i.getGstRate().toPlainString()); text(c,470,y,i.getCgstAmount().add(i.getSgstAmount()).add(i.getIgstAmount()).toPlainString()); text(c,525,y,i.getLineTotal().toPlainString()); y-=14; }
    y-=8; line(c,50,y,545); y-=18; c.setFont(font(false),9);
    text(c,365,y,"Taxable Amount"); text(c,525,y,sale.getTaxableAmount().toPlainString()); y-=14;
    if(sale.getIgstAmount().signum()>0){text(c,365,y,"IGST");text(c,525,y,sale.getIgstAmount().toPlainString());y-=14;} else {text(c,365,y,"CGST");text(c,525,y,sale.getCgstAmount().toPlainString());y-=14;text(c,365,y,"SGST");text(c,525,y,sale.getSgstAmount().toPlainString());y-=14;}
    c.setFont(font(true),11); text(c,365,y,"Grand Total"); text(c,525,y,"INR "+sale.getTotal().toPlainString()); y-=25;
    c.setFont(font(true),9); text(c,50,y,"Payment Summary"); y-=13; c.setFont(font(false),9); for(var p:sale.getPayments()){text(c,50,y,p.getMethod()+": INR "+p.getAmount().toPlainString());y-=12;}
    y-=8; c.setFont(font(false),8); text(c,50,y,"This is a computer-generated invoice. Please retain it for your records.");
   }
   d.save(out); return out.toByteArray();
  }
 }
 private static void text(PDPageContentStream c,float x,float y,String s)throws IOException{c.beginText();c.newLineAtOffset(x,y);c.showText(safe(s,""));c.endText();}
 private static PDFont font(boolean bold){return new PDType1Font(bold?Standard14Fonts.FontName.HELVETICA_BOLD:Standard14Fonts.FontName.HELVETICA);}
 private static void line(PDPageContentStream c,float x,float y,float x2)throws IOException{c.moveTo(x,y);c.lineTo(x2,y);c.stroke();}
 private static String safe(String a,String b){return a==null||a.isBlank()?b:a;}
 private static String trim(String s,int n){return s==null?"":s.length()<=n?s:s.substring(0,n);}
 private static String join(String... parts){StringBuilder b=new StringBuilder();for(String p:parts)if(p!=null&&!p.isBlank()){if(b.length()>0)b.append(", ");b.append(p);}return b.toString();}
}
