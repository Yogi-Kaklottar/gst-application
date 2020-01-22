package com.app.gst;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Company;
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
    Invoiceline invoiceline = request.getContext().asType(Invoiceline.class);
    Invoice invoice = request.getContext().asType(Invoice.class);
    Company company = invoice.getCompany();
    Address companyAddress = company.getAddress();
    Address invoiceAddress = invoice.getInvoiceaddress();

    double netamount = 0, netigst = 0, netcsgst = 0, netsgst = 0, grossamount = 0;

    netamount = invoiceline.getQty().doubleValue() * invoiceline.getPrice().doubleValue();

    if (companyAddress.getState() != invoiceAddress.getState()) {
      netigst = netamount * (invoiceline.getGstrate().doubleValue() / 100);
    } else {
      netigst = 0;
    }
    if (companyAddress.getState() == invoiceAddress.getState()) {
      netcsgst = netamount * (invoiceline.getGstrate().doubleValue() / 100) / 2;
    } else {
      netcsgst = 0;
    }
    if (companyAddress.getState() != invoiceAddress.getState()) {
      netsgst = netamount * (invoiceline.getGstrate().doubleValue() / 100) / 2;
    } else {
      netsgst = 0;
    }

    grossamount = grossamount + netamount + netcsgst + netigst + netsgst;

    invoiceline.setNetamount(new BigDecimal(netamount));
    invoiceline.setIgst(new BigDecimal(netigst));
    invoiceline.setCgst(new BigDecimal(netcsgst));
    invoiceline.setSgst(new BigDecimal(netsgst));
    invoiceline.setGrossamount(new BigDecimal(grossamount));

    response.setValues(invoiceline);
  }
}
