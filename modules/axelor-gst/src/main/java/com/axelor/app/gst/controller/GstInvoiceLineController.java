package com.axelor.app.gst.controller;

import com.axelor.app.gst.InvoiceLine.service.GstInvoiceLineService;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class GstInvoiceLineController {
  @Inject GstInvoiceLineService gstInvoiceLineService;

  public void setInvoiceLineComputation(ActionRequest request, ActionResponse response) {
    try {
      InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
      Invoice invoice = request.getContext().getParent().asType(Invoice.class);
      invoiceLine = gstInvoiceLineService.setInvoicelineDataComputation(invoice, invoiceLine);
      response.setValues(invoiceLine);
    } catch (Exception e) {
      //  System.out.println("Method:setInvoiceLineData");
    }
  }
}
