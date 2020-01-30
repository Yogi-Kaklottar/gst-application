package com.axelor.app.gst.InvoiceLine.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;

public interface GstInvoiceLineService {

  public InvoiceLine setInvoicelineDataComputation(Invoice invoice, InvoiceLine invoiceLine);
}
