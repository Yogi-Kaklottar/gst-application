package com.app.gst;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;

public class InvoicelineData {

  public void setInvoiceData(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    double netamount = 0, netigst = 0, netcsgst = 0, netsgst = 0, grossamount = 0;
    ArrayList<Invoiceline> il = (ArrayList<Invoiceline>) invoice.getInvoiceitem();

    for (Invoiceline invoiceline : il) {
      netamount = netamount + invoiceline.getNetamount().doubleValue();
      netigst = netigst + invoiceline.getIgst().doubleValue();
      netcsgst = netcsgst + invoiceline.getCgst().doubleValue();
      netsgst = netsgst + invoiceline.getSgst().doubleValue();
      grossamount = grossamount + netamount + netcsgst + netigst + netsgst;
    }

    invoice.setNetamount(new BigDecimal(netamount));
    invoice.setNetigst(new BigDecimal(netigst));
    invoice.setNetcsgst(new BigDecimal(netcsgst));
    invoice.setNetsgst(new BigDecimal(netsgst));
    invoice.setGrossamount(new BigDecimal(grossamount));
    response.setValues(invoice);
  }

  public void setInvoiceLineData(ActionRequest request, ActionResponse response) {
    System.err.println("hello calll");
    Invoiceline invoiceline = request.getContext().asType(Invoiceline.class);
    Invoice invoice = request.getContext().getParent().asType(Invoice.class);
    BigDecimal n;
    Address companyAddress = invoice.getCompany().getAddress();
    Address invoiceAddress = invoice.getInvoiceaddress();

    BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
    BigDecimal b = new BigDecimal(invoiceline.getQty());
    netamount = invoiceline.getPrice().multiply(b);

    System.out.println(netamount.toString());
    if (companyAddress.getState().equals(invoiceAddress.getState())) {
      netigst = new BigDecimal(0);
      netcsgst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
      netsgst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
    } else {
      netcsgst = new BigDecimal(0);
      netsgst = new BigDecimal(0);
      netigst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
    }

    grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);

    invoiceline.setNetamount(netamount);
    invoiceline.setIgst(netigst);
    invoiceline.setCgst(netcsgst);
    invoiceline.setSgst(netsgst);
    invoiceline.setGrossamount(grossamount);

    response.setValues(invoiceline);
  }
}
