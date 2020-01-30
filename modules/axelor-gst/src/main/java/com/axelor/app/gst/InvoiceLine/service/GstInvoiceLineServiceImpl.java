package com.axelor.app.gst.InvoiceLine.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.math.BigDecimal;

public class GstInvoiceLineServiceImpl implements GstInvoiceLineService {

  @Override
  public InvoiceLine setInvoicelineDataComputation(Invoice invoice, InvoiceLine invoiceLine) {
    try {
      BigDecimal n;
      Address companyAddress = invoice.getCompany().getAddress();
      Address invoiceAddress = invoice.getInvoiceAddress();

      BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
      BigDecimal b = new BigDecimal(invoiceLine.getQty());
      netamount = invoiceLine.getPrice().multiply(b);

      if (companyAddress.getState().equals(invoiceAddress.getState())) {
        netigst = BigDecimal.ZERO;
        netcsgst =
            netamount
                .multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
        netsgst =
            netamount
                .multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)))
                .divide(new BigDecimal(2));
      } else {
        netcsgst = BigDecimal.ZERO;
        netsgst =BigDecimal.ZERO;
        netigst =
            netamount
                .multiply(invoiceLine.getGstRate().divide(new BigDecimal(100)));
                
      }

      grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);

      invoiceLine.setNetAmount(netamount);
      invoiceLine.setIgst(netigst);
      invoiceLine.setCgst(netcsgst);
      invoiceLine.setSgst(netsgst);
      invoiceLine.setGrossAmount(grossamount);
    } catch (Exception e) {
      // System.err.println("invoice line calculation");
    }

    return invoiceLine;
  }
}
