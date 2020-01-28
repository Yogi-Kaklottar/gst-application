package com.app.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import java.util.ArrayList;

public interface ServiceData {
  public Invoice setInvoiceDataService(Invoice invoice, ArrayList<Invoiceline> il);

  public Invoiceline setInvoicelineData(Invoice invoice, Invoiceline invoiceline);

  public Invoice setPartyData(Invoice invoice);

  public Invoiceline setPartyCompanyChangeData(Invoice invoice, Invoiceline invoiceline);
}
