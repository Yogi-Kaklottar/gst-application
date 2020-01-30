package com.axelor.app.gst.service.InvoiceLine;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;

public interface GstInvoiceLineService {

  public InvoiceLine setInvoicelineDataComputation(Invoice invoice, InvoiceLine invoiceLine);
}
