package com.axelor.app.gst.service.invoice;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import java.util.List;

public interface GstInvoiceService {
  public Invoice setInvoicePartySelectDefault(Invoice invoice);

  public Invoice setInvoiceComputation(Invoice invoice, List<InvoiceLine> invoiceLineList);

  public InvoiceLine setComputationPartyCompanyChange(Invoice invoice, InvoiceLine invoiceLine);

  public Invoice setAsBooleanValueShippingAddress(Invoice invoice);
}
